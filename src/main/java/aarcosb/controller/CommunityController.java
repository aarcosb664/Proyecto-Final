package aarcosb.controller;

import aarcosb.model.dto.*;
import aarcosb.model.entity.*;
import aarcosb.model.repository.*;
import aarcosb.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/community")
public class CommunityController {

    // Autowired pone aquí automáticamente los objetos que necesita la clase
    @Autowired private ListingService listingService;
    @Autowired private UserDetailsServiceImpl userDetailsService;
    @Autowired private CloudinaryService cloudinaryService;
    @Autowired private CommentService commentService;
    @Autowired private RatingService ratingService;
    @Autowired private UserRepository userRepository;

    // Añade el usuario actual al modelo y lo retorna
    private User addCurrentUserToModel(Model model, Authentication authentication) 
    {
        User currentUser = userRepository.findByEmail(authentication.getName());
        model.addAttribute("currentUser", currentUser);
        return currentUser;
    }

    // Página principal de la comunidad con filtros y paginación de listings
    @GetMapping("")
    public String index(@ModelAttribute("filter") ListingFilterDTO filter, Model model, Authentication authentication) 
    {
        model.addAttribute("currentUser", userRepository.findByEmail(authentication.getName()));

        // Ordena los listings por el campo y la dirección especificados en el filtro
        Sort sortBy = filter.getOrder().equals("desc") ? Sort.by(filter.getSort()).descending() : Sort.by(filter.getSort()).ascending();
        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize(), sortBy);

        // Añade los listings ya filtrados al modelo
        model.addAttribute("listings", listingService.searchAndFilter(filter.getQuery(), filter.getMinRating(), filter.getMaxRating(), filter.getDateFrom(), filter.getDateTo(), pageable));
        return "community/main";
    }

    // Muestra los detalles de un listing, comentarios y favoritos
    @GetMapping("/listing/{listingId}")
    public String viewListing(@PathVariable Long listingId, Model model, Authentication authentication) 
    {
        User currentUser = addCurrentUserToModel(model, authentication);
        Listing listing = listingService.getListingById(listingId);

        // Si el listing no existe, lanza una excepción
        if (listing == null) {
            throw new IllegalArgumentException("Listing not found");
        }
        model.addAttribute("listing", listing);

        // Se envían datos varios necesarios para la vista
        User listingUser = userRepository.findById(listing.getUserId()).orElse(null);
        model.addAttribute("listingUserProfilePic", listingUser.getProfilePic());
        model.addAttribute("userRating", ratingService.getUserRating(listingId, currentUser.getId()));
        model.addAttribute("isFavorite", currentUser.getFavListings().contains(listingId));
        model.addAttribute("totalFavorites", listingService.countTotalFavorites(listingId));
        model.addAttribute("totalListings", userDetailsService.countTotalListings(listing.getUserId()));
        model.addAttribute("comments", commentService.getListingComments(listingId));

        // Si no existe el formulario de comentario, lo añade al modelo
        if (!model.containsAttribute("commentForm")) {
            model.addAttribute("commentForm", new CommentForm());
        }

        return "community/show";
    }

    // Formulario para crear un nuevo listing
    @GetMapping("/listing/create")
    public String createListing(Model model, Authentication authentication) 
    {
        User currentUser = addCurrentUserToModel(model, authentication);

        // Si el usuario es admin, lanza una excepción
        if (currentUser.getRole() == Role.ADMIN) {
            throw new IllegalArgumentException("Admins cannot create listings");
        }

        // Añade el formulario de creación de listing al modelo
        model.addAttribute("listingForm", new ListingForm());
        return "community/create";
    }

    // Procesa la creación de un nuevo listing con imágenes y video
    @PostMapping("/listing/create")
    public String createListing(@Valid @ModelAttribute("listingForm") ListingForm form,
                                BindingResult result,
                                Model model, 
                                Authentication authentication) 
    {
        User currentUser = addCurrentUserToModel(model, authentication);

        // Si el usuario es admin, lanza una excepción
        if (currentUser.getRole() == Role.ADMIN) {
            throw new IllegalArgumentException("Admins cannot create listings");
        }

        // Si hay errores, añade el formulario de creación de listing al modelo
        if (result.hasErrors()) {
            model.addAttribute("listingForm", form);
            return "community/create";
        }

        // Crea el listing y lo guarda en la base de datos
        try {
            Listing listing = new Listing();
            listing.setTitle(form.getTitle());
            listing.setDescription(form.getDescription());
            listing.setOfficialUrl(form.getOfficialUrl());
            listing.setTags(listingService.convertTags(form.getTags()));
            listing.setUserId(currentUser.getId());
            listing.setUserName(currentUser.getUserName());
            listing = listingService.updateListing(listing);

            // Sube las imágenes al servicio de Cloudinary
            cloudinaryService.uploadImages(listing.getId(), form.getImages());
            if (form.getVideo() != null && !form.getVideo().isEmpty()) {
                cloudinaryService.uploadVideo(listing.getId(), form.getVideo());
            }

            return "redirect:/community/listing/" + listing.getId();
        } catch (Exception e) {
            // Si hay errores, añade el formulario de creación de listing al modelo
            result.reject("error", "Error creating listing: " + e.getMessage());
            model.addAttribute("listingForm", form);
            return "community/create";
        }
    }

    // Formulario para editar un listing existente
    @GetMapping("/listing/{listingId}/edit")
    public String editListing(@PathVariable Long listingId, Model model, Authentication authentication) 
    {
        User currentUser = addCurrentUserToModel(model, authentication);
        Listing listing = listingService.getListingById(listingId);

        // Si el listing no existe, lanza una excepción
        if (listing == null || currentUser.getRole() == Role.ADMIN || !currentUser.getId().equals(listing.getUserId())) {
            throw new IllegalArgumentException("User not authorized");
        }

        // Añade el formulario de edición de listing al modelo
        ListingForm form = new ListingForm();
        form.setTitle(listing.getTitle());
        form.setDescription(listing.getDescription());
        form.setOfficialUrl(listing.getOfficialUrl());
        form.setTags(String.join(", ", listing.getTags()));

        model.addAttribute("listingForm", form);
        model.addAttribute("listing", listing);
        return "community/edit";
    }

    // Procesa la actualización de un listing existente
    @PostMapping("/listing/{listingId}/update")
    public String updateListing(@PathVariable Long listingId,
                                @Valid @ModelAttribute("listingForm") ListingForm form,
                                BindingResult result,
                                Model model,
                                Authentication authentication) 
    {
        User currentUser = addCurrentUserToModel(model, authentication);
        Listing listing = listingService.getListingById(listingId);

        // Si el listing no existe, lanza una excepción
        if (listing == null) {
            throw new IllegalArgumentException("Listing not found");
        }

        // Si el usuario no es admin y no es el propietario del listing, lanza una excepción
        if (currentUser.getRole() != Role.ADMIN && !currentUser.getId().equals(listing.getUserId())) {
            throw new IllegalArgumentException("User not authorized");
        }

        model.addAttribute("listing", listing);

        // Si hay errores, añade el formulario de edición de listing al modelo
        if (result.hasErrors()) {
            model.addAttribute("listingForm", form);
            return "community/edit";
        }

        // Actualiza el listing y lo guarda en la base de datos
        try {
            listing.setTitle(form.getTitle());
            listing.setDescription(form.getDescription());
            listing.setOfficialUrl(form.getOfficialUrl());
            listing.setTags(listingService.convertTags(form.getTags()));

            // Sube las imágenes al servicio de Cloudinary
            if (!form.getImages().isEmpty() && !form.getImages().get(0).isEmpty()) {
                cloudinaryService.uploadImages(listingId, form.getImages());
            }

            // Sube el video, si existe, al servicio de Cloudinary
            if (form.getVideo() != null && !form.getVideo().isEmpty()) {
                cloudinaryService.uploadVideo(listingId, form.getVideo());
            }

            listingService.updateListing(listing);
            return "redirect:/community/listing/" + listingId;
        } catch (Exception e) {
            // Si hay errores, añade el formulario de edición de listing al modelo
            result.reject("error", "Error updating listing: " + e.getMessage());
            model.addAttribute("listingForm", form);
            model.addAttribute("listing", listing);
            return "community/edit";
        }
    }

    // Elimina un listing y todos sus datos asociados
    @PostMapping("/listing/{listingId}/delete")
    public String deleteListing(@PathVariable Long listingId, Model model, Authentication authentication) 
    {
        User currentUser = addCurrentUserToModel(model, authentication);
        Listing listing = listingService.getListingById(listingId);

        // Si el listing no existe, lanza una excepción
        if (listing == null) {
            throw new IllegalArgumentException("Listing not found");
        }

        // Si el usuario no es admin y no es el propietario del listing, lanza una excepción
        if (currentUser.getRole() != Role.ADMIN && !currentUser.getId().equals(listing.getUserId())) {
            throw new IllegalArgumentException("User not authorized");
        }

        listingService.deleteListing(listingId);
        return "redirect:/community";
    }

    // Añade un nuevo comentario a un listing
    @PostMapping("/listing/{listingId}/comment/post")
    public String addComment(@PathVariable Long listingId, 
                             @Valid @ModelAttribute("commentForm") CommentForm form,
                             BindingResult result,
                             RedirectAttributes redirect,
                             Authentication authentication,
                             Model model) 
    {
        User currentUser = addCurrentUserToModel(model, authentication);

        // Si el usuario es admin, lanza una excepción
        if (currentUser.getRole() == Role.ADMIN) {
            throw new IllegalArgumentException("User not authorized");
        }

        // Si hay errores, añade el formulario de creación de comentario al modelo
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.commentForm", result);
            redirect.addFlashAttribute("commentForm", form);
            return "redirect:/community/listing/" + listingId;
        }

        Comment comment = new Comment();
        comment.setTitle(form.getTitle());
        comment.setText(form.getText());
        comment.setListingId(listingId);
        comment.setUserId(currentUser.getId());
        comment.setUserName(currentUser.getUserName());
        commentService.createComment(comment);

        return "redirect:/community/listing/" + listingId;
    }

    // Elimina un comentario de un listing (solo el propietario puede)
    @PostMapping("/listing/{listingId}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable Long listingId, 
                                @PathVariable Long commentId, 
                                Model model, 
                                Authentication authentication) 
    {
        User currentUser = addCurrentUserToModel(model, authentication);
        Comment comment = commentService.getCommentById(commentId);

        // Si el usuario no es admin y no es el propietario del comentario, lanza una excepción
        if (currentUser.getRole() != Role.ADMIN && !currentUser.getId().equals(comment.getUserId()) || comment == null) {
            throw new IllegalArgumentException("User not authorized");
        }

        commentService.deleteComment(commentId);
        return "redirect:/community/listing/" + listingId;
    }

    // Añade o actualiza la valoración de un usuario para un listing
    @PostMapping("/listing/{listingId}/rate")
    public String rateListing(@PathVariable Long listingId, 
                              @RequestParam Double rating, 
                              Model model, 
                              Authentication authentication) 
    {
        User currentUser = addCurrentUserToModel(model, authentication);
        Listing listing = listingService.getListingById(listingId);

        // Si el listing no existe, lanza una excepción
        if (listing == null || currentUser.getRole() == Role.ADMIN || currentUser.getId().equals(listing.getUserId())) {
            throw new IllegalArgumentException("User not authorized");
        }

        ratingService.rateListing(listingId, currentUser.getId(), rating);
        return "redirect:/community/listing/" + listingId;
    }

    // Alterna el estado de favorito de un listing para el usuario actual
    @PostMapping("/listing/{listingId}/favorite")
    public String addToFavorites(@PathVariable Long listingId, Model model, Authentication authentication) 
    {
        User currentUser = addCurrentUserToModel(model, authentication);
        Listing listing = listingService.getListingById(listingId);

        // Si el listing no existe, lanza una excepción
        if (listing == null) {
            throw new IllegalArgumentException("Listing not found");
        }

        // Si el listing no está en la lista de favoritos, lo añade, si está, lo elimina
        if (!currentUser.getFavListings().contains(listingId)) {
            currentUser.getFavListings().add(listingId);
        } else {
            currentUser.getFavListings().remove(listingId);
        }

        userRepository.save(currentUser);
        return "redirect:/community/listing/" + listingId;
    }
}