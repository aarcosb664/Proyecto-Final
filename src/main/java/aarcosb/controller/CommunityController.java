package aarcosb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import aarcosb.model.entity.Listing;
import aarcosb.model.entity.Comment;
import aarcosb.model.entity.User;
import aarcosb.service.ListingService;
import aarcosb.service.CommentService;
import aarcosb.service.CloudinaryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import aarcosb.model.dto.ListingForm;
import aarcosb.model.dto.CommentForm;
import aarcosb.service.RatingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Controller
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private ListingService listingService;
    
    @Autowired
    private CommentService commentService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private RatingService ratingService;

    @GetMapping("")
    public String index(
        @RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue = "9") Integer size,
        @RequestParam(defaultValue = "updatedAt") String sort,
        @RequestParam(defaultValue = "desc") String order,
        @RequestParam(defaultValue = "") String query,
        @RequestParam(required = false) Double minRating,
        @RequestParam(required = false) Double maxRating,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo,
        Model model) 
    {

        Sort sortBy = order.equals("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sortBy);

        model.addAttribute("activePage", "community");
        model.addAttribute("listings",
        listingService.searchAndFilter(query, minRating, maxRating, dateFrom, dateTo, pageable));
        return "community/main";
    }

    // Listing related methods
    @GetMapping("/listing/{id}")
    public String viewListing(@PathVariable Long id, Model model) {
        model.addAttribute("activePage", "community");
        
        Listing listing = listingService.getListingById(id);
        if (listing == null) {
            return "redirect:/community";
        }
        model.addAttribute("listing", listing);
        model.addAttribute("comments", commentService.getListingComments(id));

        // Si ya hay un commentForm (por redirect), no lo sobrescribas
        if (!model.containsAttribute("commentForm")) {
            model.addAttribute("commentForm", new CommentForm());
        }

        return "community/show";
    }

    @GetMapping("/user/{userId}/listings")
    public String viewUserListings(@PathVariable Long userId, Model model) {
        model.addAttribute("listings", listingService.getListingsByUserId(userId));
        return "community/user-listings";
    }

    @GetMapping("/listing/create")
    public String createListing(Model model) {
        model.addAttribute("activePage", "community");
        model.addAttribute("listingForm", new ListingForm());
        return "community/create";
    }

    @PostMapping("/listing/create")
    public String createListing(
        @Valid @ModelAttribute("listingForm") ListingForm form,
        BindingResult result,
        Model model,
        HttpSession session
    ) {
        // Validación de tags (puede lanzar excepción)
        try {
            listingService.convertTags(form.getTags());
        } catch (IllegalArgumentException e) {
            result.rejectValue("tags", "error.tags", e.getMessage());
        }

        // Inicializar variables
        List<String> imageUrls = null;
        String videoUrl = null;

        // Subir imágenes y video
        try {
            imageUrls = cloudinaryService.uploadImages(form.getImages());
            videoUrl = cloudinaryService.uploadVideo(form.getVideo());
        } catch (IllegalArgumentException e) {
            if (e.getMessage().toLowerCase().contains("image")) {
                result.rejectValue("images", "error.images", e.getMessage());
            } else if (e.getMessage().toLowerCase().contains("video")) {
                result.rejectValue("video", "error.video", e.getMessage());
            } else {
                result.reject("global", e.getMessage());
            }
        } catch (Exception e) {
            result.reject("global", "Error uploading files: " + e.getMessage());
        }

        // Si hay errores, volver a la vista de creación
        if (result.hasErrors()) {
            model.addAttribute("listingForm", form);
            return "community/create";
        }

        // Crear listing
        Listing listing = new Listing();
        listing.setTitle(form.getTitle());
        listing.setDescription(form.getDescription());
        listing.setTags(listingService.convertTags(form.getTags()));
        listing.setImages(imageUrls);
        listing.setVideo(videoUrl);

        // Obtener usuario de sesión y asignar campos
        User user = (User) session.getAttribute("user");
        listing.setUserId(user.getId());
        listing.setUserName(user.getUserName());

        listingService.createListing(listing);

        return "redirect:/community/listing/" + listing.getId();
    }

    @GetMapping("/listing/{listingId}/edit")
    public String editListing(@PathVariable Long listingId, Model model) {
        model.addAttribute("activePage", "community");
        
        Listing listing = listingService.getListingById(listingId);
        if (listing == null) {
            return "redirect:/community";
        }

        model.addAttribute("listing", listing);
        return "community/edit";
    }

    @PostMapping("/listing/{listingId}/update")
    public String updateListing(
        @PathVariable Long listingId,
        @Valid @ModelAttribute Listing listing,
        BindingResult result,
        @RequestParam("tags") String tags,
        @RequestParam(value = "images", required = false) List<MultipartFile> images,
        @RequestParam(value = "video", required = false) MultipartFile video,
        Model model
    ) {
        listing.setId(listingId);
        listing.setTags(listingService.convertTags(tags));

        // Si se suben nuevas imágenes, reemplazar
        if (images != null && !images.isEmpty() && !images.get(0).isEmpty()) {
            try {
                List<String> imageUrls = cloudinaryService.uploadImages(images);
                listing.setImages(imageUrls);
            } catch (IllegalArgumentException e) {
                result.rejectValue("images", "error.listing", e.getMessage());
                model.addAttribute("listing", listing);
                return "community/edit";
            } catch (Exception e) {
                result.rejectValue("images", "error.listing", "Error uploading images");
                model.addAttribute("listing", listing);
                return "community/edit";
            }
        }
        // Video: si se sube uno nuevo, reemplazar
        if (video != null && !video.isEmpty()) {
            try {
                String videoUrl = cloudinaryService.uploadVideo(video);
                listing.setVideo(videoUrl);
            } catch (Exception e) {
                result.rejectValue("video", "error.listing", "Error uploading video");
                model.addAttribute("listing", listing);
                return "community/edit";
            }
        }

        // Si hay errores, volver a la vista de edición
        if (result.hasErrors()) {
            model.addAttribute("listing", listing);
            return "community/edit";
        }

        listingService.updateListing(listing);
        return "redirect:/community/listing/" + listingId;
    }

    @PostMapping("/listing/{listingId}/remove")
    public String removeListing(@PathVariable Long listingId) {
        Listing listing = listingService.getListingById(listingId);

        if (listing == null) {
            return "redirect:/community";
        }

        listingService.deleteListing(listingId);
        return "redirect:/community";
    }

    // Comment related methods
    @PostMapping("/listing/{listingId}/comment/post")
    public String addComment(
        @PathVariable Long listingId,
        @Valid @ModelAttribute("commentForm") CommentForm form,
        BindingResult result,
        RedirectAttributes redirect,
        HttpSession session
    ) {
        User user = (User) session.getAttribute("user");

        try {
            commentService.createComment(listingId, user.getId(), form.getTitle(), form.getText());
        } catch (Exception e) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.commentForm", result);
            redirect.addFlashAttribute("commentForm", form);
            redirect.addFlashAttribute("commentError", e.getMessage());
            return "redirect:/community/listing/" + listingId;
        }
        return "redirect:/community/listing/" + listingId;
    }

    @PostMapping("/listing/{listingId}/comment/{commentId}/remove")
    public String removeComment(@PathVariable Long listingId, @PathVariable Long commentId) {
        Comment comment = commentService.getCommentById(commentId);

        if (comment == null) {
            return "redirect:/community/listing/" + listingId;
        }

        commentService.deleteComment(commentId);
        return "redirect:/community/listing/" + listingId;
    }

    @PostMapping("/listing/{listingId}/rate")
    public String rateListing(@PathVariable Long listingId, @RequestParam Double rating, HttpSession session) {
        User user = (User) session.getAttribute("user");
        ratingService.rateListing(listingId, user.getId(), rating);
        return "redirect:/community/listing/" + listingId;
    }
}
