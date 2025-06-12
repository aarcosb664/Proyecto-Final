package aarcosb.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import java.util.Date;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import aarcosb.model.entity.*;
import aarcosb.model.dto.*;
import aarcosb.model.repository.*;
import aarcosb.service.*;

@Controller
@RequestMapping("/community")
public class CommunityController {

    // Servicios necesarios para la gestión de listings y funcionalidades asociadas
    @Autowired private ListingService listingService;
    @Autowired private UserDetailsServiceImpl userDetailsService;
    @Autowired private CloudinaryService cloudinaryService;
    @Autowired private CommentService commentService;
    @Autowired private RatingService ratingService;
    @Autowired private UserRepository userRepository;

    // Método auxiliar para obtener el usuario actual a partir de la autenticación
    private User getCurrentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName());
    }

    // Página principal de la comunidad con paginación de listings
    // Muestra los listings más recientes primero, 12 por página por defecto
    @GetMapping("")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "9") Integer size,
                        @RequestParam(defaultValue = "updatedAt") String sort,
                        @RequestParam(defaultValue = "desc") String order,
                        @RequestParam(defaultValue = "") String query,
                        @RequestParam(required = false) Double minRating,
                        @RequestParam(required = false) Double maxRating,
                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo,
                        Model model, Authentication authentication) 
    {
        User user = getCurrentUser(authentication);
        model.addAttribute("user", user);

        Sort sortBy = order.equals("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sortBy);

        model.addAttribute("listings",
        listingService.searchAndFilter(query, minRating, maxRating, dateFrom, dateTo, pageable));
        return "community/main";
    }

    // Muestra un listing específico con todos sus detalles
    // Incluye comentarios, valoraciones, y estado de favorito para el usuario actual
    @GetMapping("/listing/{listingId}")
    public String viewListing(@PathVariable Long listingId, Model model, Authentication authentication) {
        User user = getCurrentUser(authentication);
        model.addAttribute("user", user);
        
        Listing listing = listingService.getListingById(listingId);
        if (listing == null) {
            return "redirect:/community";
        }
        model.addAttribute("listing", listing);

        User listingUser = userRepository.findById(listing.getUserId()).orElse(null);
        model.addAttribute("listingUserProfilePic", listingUser.getProfilePic());
        model.addAttribute("userRating", ratingService.getUserRating(listingId, user.getId()));
        model.addAttribute("isFavorite", user.getFavListings().contains(listingId));
        model.addAttribute("totalFavorites", listingService.countTotalFavorites(listingId));
        model.addAttribute("totalListings", userDetailsService.countTotalListings(listing.getUserId()));

        model.addAttribute("comments", commentService.getListingComments(listingId));

        if (!model.containsAttribute("commentForm")) {
            model.addAttribute("commentForm", new CommentForm());
        }

        return "community/show";
    }

    // Formulario de creación de nuevo listing
    // Inicializa un formulario vacío y lo añade al modelo
    @GetMapping("/listing/create")
    public String createListing(Model model, Authentication authentication) {
        User user = getCurrentUser(authentication);
        model.addAttribute("user", user);

        model.addAttribute("listingForm", new ListingForm());
        return "community/create";
    }

    // Procesa la creación de un nuevo listing
    // Maneja la subida de imágenes (3-10) y video (opcional)
    @PostMapping("/listing/create")
    public String createListing(@Valid @ModelAttribute("listingForm") ListingForm form,
                          BindingResult result,
                          Model model, 
                          Authentication authentication) 
    {
        User user = getCurrentUser(authentication);
        model.addAttribute("user", user);

        // Validación del formulario mediante anotaciones en ListingForm
        if (result.hasErrors()) {
            model.addAttribute("listingForm", form);
            return "community/create";
        }

        try {
            // Creación del listing con datos básicos
            // El ID se genera automáticamente al guardar
            Listing listing = new Listing();
            listing.setTitle(form.getTitle());
            listing.setDescription(form.getDescription());
            listing.setOfficialUrl(form.getOfficialUrl());
            listing.setTags(listingService.convertTags(form.getTags()));
            listing.setUserId(user.getId());
            listing.setUserName(user.getUserName());
            
            // Guardamos primero para obtener el ID
            // Necesario para asociar imágenes y video
            listing = listingService.createListing(listing);

            // Subida de archivos multimedia
            // Las validaciones de formato y cantidad se realizan en CloudinaryService
            cloudinaryService.uploadImages(listing.getId(), form.getImages());
            if (form.getVideo() != null && !form.getVideo().isEmpty()) {
                cloudinaryService.uploadVideo(listing.getId(), form.getVideo());
            }

            return "redirect:/community/listing/" + listing.getId();

        } catch (Exception e) {
            result.reject("global", "Error creating listing: " + e.getMessage());
            model.addAttribute("listingForm", form);
            return "community/create";
        }
    }

    // Formulario de edición de listing existente
    // Carga los datos actuales del listing en el formulario
    @GetMapping("/listing/{listingId}/edit")
    public String editListing(@PathVariable Long listingId, Model model, Authentication authentication) {
        User user = getCurrentUser(authentication);
        model.addAttribute("user", user);
        
        // Obtener listing existente o redirigir si no existe
        Listing listing = listingService.getListingById(listingId);
        if (listing == null) {
            return "redirect:/community";
        }

        // Poblar el formulario con los datos actuales
        // Las imágenes y video actuales se muestran en la vista
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
    // Permite actualizar datos básicos y opcionalmente multimedia
    @PostMapping("/listing/{listingId}/update")
    public String updateListing(@PathVariable Long listingId,
                                @Valid @ModelAttribute("listingForm") ListingForm form,
                                BindingResult result,
                                Model model,
                                Authentication authentication) 
    {
        User user = getCurrentUser(authentication);
        model.addAttribute("user", user);

        // Obtener listing existente o redirigir si no existe
        Listing listing = listingService.getListingById(listingId);
        if (listing == null) {
            return "redirect:/community";
        }
        model.addAttribute("listing", listing);

        // Validación del formulario mediante anotaciones en ListingForm
        if (result.hasErrors()) {
            model.addAttribute("listingForm", form);
            return "community/edit";
        }

        try {
            // Actualizar datos básicos del listing
            listing.setTitle(form.getTitle());
            listing.setDescription(form.getDescription());
            listing.setOfficialUrl(form.getOfficialUrl());
            listing.setTags(listingService.convertTags(form.getTags()));

            // Actualizar multimedia solo si se proporcionan nuevos archivos
            // Las validaciones de formato y cantidad se realizan en CloudinaryService
            if (!form.getImages().isEmpty() && !form.getImages().get(0).isEmpty()) {
                cloudinaryService.uploadImages(listingId, form.getImages());
            }
            if (form.getVideo() != null && !form.getVideo().isEmpty()) {
                cloudinaryService.uploadVideo(listingId, form.getVideo());
            }

            listingService.updateListing(listing);
            return "redirect:/community/listing/" + listingId;

        } catch (Exception e) {
            result.reject("global", "Error updating listing: " + e.getMessage());
            model.addAttribute("listingForm", form);
            model.addAttribute("listing", listing);
            return "community/edit";
        }
    }

    // Elimina un listing y todos sus datos asociados
    // Incluye imágenes, video, comentarios y valoraciones
    @PostMapping("/listing/{listingId}/remove")
    public String removeListing(@PathVariable Long listingId, Model model, Authentication authentication) {
        User user = getCurrentUser(authentication);
        model.addAttribute("user", user);

        Listing listing = listingService.getListingById(listingId);

        if (listing == null) {
            return "redirect:/community";
        }

        listingService.deleteListing(listingId);
        return "redirect:/community";
    }

    // Añade un nuevo comentario a un listing
    // Requiere título y texto, asocia automáticamente el usuario actual
    @PostMapping("/listing/{listingId}/comment/post")
    public String addComment(@PathVariable Long listingId, 
                             @Valid @ModelAttribute("commentForm") CommentForm form,
                             BindingResult result,
                             RedirectAttributes redirect,
                             Authentication authentication,
                             Model model) 
    {
        User user = getCurrentUser(authentication);
        model.addAttribute("user", user);

        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.commentForm", result);
            redirect.addFlashAttribute("commentForm", form);
            return "redirect:/community/listing/" + listingId;
        }

        Comment comment = new Comment();
        comment.setTitle(form.getTitle());
        comment.setText(form.getText());
        comment.setListingId(listingId);
        comment.setUserId(user.getId());
        comment.setUserName(user.getUserName());
        commentService.createComment(comment);

        return "redirect:/community/listing/" + listingId;
    }

    // Elimina un comentario específico de un listing
    // Solo el propietario del comentario puede eliminarlo
    @PostMapping("/listing/{listingId}/comment/{commentId}/remove")
    public String removeComment(@PathVariable Long listingId, @PathVariable Long commentId, Model model, Authentication authentication) {
        User user = getCurrentUser(authentication);
        model.addAttribute("user", user);

        Comment comment = commentService.getCommentById(commentId);

        if (comment == null) {
            return "redirect:/community/listing/" + listingId;
        }

        commentService.deleteComment(commentId);
        return "redirect:/community/listing/" + listingId;
    }

    // Añade o actualiza la valoración de un usuario para un listing
    // La valoración debe estar entre 1 y 5 estrellas
    @PostMapping("/listing/{listingId}/rate")
    public String rateListing(@PathVariable Long listingId, @RequestParam Double rating, Model model, Authentication authentication) {
        User user = getCurrentUser(authentication);
        model.addAttribute("user", user);

        ratingService.rateListing(listingId, user.getId(), rating);
        return "redirect:/community/listing/" + listingId;
    }

    // Alterna el estado de favorito de un listing para el usuario actual
    // Si ya es favorito lo quita, si no lo es lo añade
    @PostMapping("/listing/{listingId}/favorite")
    public String addToFavorites(@PathVariable Long listingId, Model model, Authentication authentication) {
        User user = getCurrentUser(authentication);
        model.addAttribute("user", user);

        if (!user.getFavListings().contains(listingId)) {
            user.getFavListings().add(listingId);
        } else {
            user.getFavListings().remove(listingId);
        }

        userRepository.save(user);
        return "redirect:/community/listing/" + listingId;
    }
}
