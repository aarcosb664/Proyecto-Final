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
import aarcosb.service.RatingService;
import aarcosb.service.CloudinaryService;
import org.springframework.security.core.context.SecurityContextHolder;
import aarcosb.model.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import aarcosb.model.dto.ListingForm;

@Controller
@RequestMapping("/community")
public class CommunityController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ListingService listingService;
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private RatingService ratingService;

    @Autowired
    private CloudinaryService cloudinaryService;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

    @GetMapping("")
    public String index(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "6") int size,
        @RequestParam(defaultValue = "updatedAt") String sortBy,
        @RequestParam(defaultValue = "true") boolean ascending,
        Model model
    ) {
        model.addAttribute("activePage", "community");
        
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Listing> listings = listingService.getAllListings(pageable);
        model.addAttribute("listings", listings);
        
        if (page < 1) {
            return "redirect:/community?page=1";
        } else if (page > listings.getTotalPages()) {
            return "redirect:/community?page=" + listings.getTotalPages();
        }
        return "community/main";
    }

    // Listing related methods
    @GetMapping("/listing/{id}")
    public String viewListing(@PathVariable Long id, Model model) {
        Listing listing = listingService.getListingById(id);
        if (listing == null) {
            return "redirect:/community";
        }
        model.addAttribute("listing", listing);
        model.addAttribute("comments", commentService.getListingComments(id));
        
        // Obtener el usuario creador del listing
        User listingUser = null;
        if (listing.getUserId() != null) {
            listingUser = userRepository.findById(listing.getUserId()).orElse(null);
        }
        model.addAttribute("listingUser", listingUser);
        return "community/show";
    }

    @GetMapping("/user/{userId}/listings")
    public String viewUserListings(@PathVariable Long userId, Model model) {
        model.addAttribute("listings", listingService.getListingsByUserId(userId));
        return "community/user-listings";
    }

    @GetMapping("/listing/create")
    public String createListing(Model model) {
        model.addAttribute("listingForm", new ListingForm());
        return "community/create";
    }

    @PostMapping("/listing/create")
    public String createListing(
        @Valid @ModelAttribute("listingForm") ListingForm form,
        BindingResult result,
        Model model
    ) {
        // Validación de tags (puede lanzar excepción)
        try {
            listingService.convertTags(form.getTags());
        } catch (IllegalArgumentException e) {
            result.rejectValue("tags", "error.tags", e.getMessage());
        }

        List<String> imageUrls = null;
        String videoUrl = null;
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

        if (result.hasErrors()) {
            model.addAttribute("listingForm", form);
            return "community/create";
        }

        Listing listing = new Listing();
        listing.setTitle(form.getTitle());
        listing.setDescription(form.getDescription());
        listing.setTags(listingService.convertTags(form.getTags()));
        listing.setImages(imageUrls);
        listing.setVideo(videoUrl);

        User user = getCurrentUser();
        listing.setUserId(user.getId());
        listing.setUserName(user.getName() + " " + user.getLastName());
        listingService.createListing(listing);

        return "redirect:/community/listing/" + listing.getId();
    }

    @GetMapping("/listing/{listingId}/edit")
    public String editListing(@PathVariable Long listingId, Model model) {
        User user = getCurrentUser();
        Listing listing = listingService.getListingById(listingId);
        if (listing == null) {
            return "redirect:/community";
        }

        model.addAttribute("userName", user.getName() + " " + user.getLastName());
        
        if (listing.getUserId() != user.getId()) {
            return "redirect:/community/listing/" + listingId;
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
        User user = getCurrentUser();
        if (listing.getUserId() != user.getId()) {
            return "redirect:/community/listing/" + listingId;
        }
        listing.setId(listingId);
        listing.setUserId(user.getId());
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
        User user = getCurrentUser();
        Listing listing = listingService.getListingById(listingId);

        if (listing == null) {
            return "redirect:/community";
        }

        if (listing.getUserId() != user.getId()) {
            return "redirect:/community/listing/" + listingId;
        }

        listingService.deleteListing(listingId);
        return "redirect:/community";
    }

    @PostMapping("/listing/{listingId}/rate")
    public String rateListing(@PathVariable Long listingId, @RequestParam Double rating) {
        User user = getCurrentUser();
        ratingService.rateListing(listingId, user.getId(), rating);
        return "redirect:/community/listing/" + listingId;
    }

    // Comment related methods
    @PostMapping("/listing/{listingId}/comment/post")
    public String addComment(@PathVariable Long listingId, @ModelAttribute Comment comment) {
        User user = getCurrentUser();
        Listing listing = listingService.getListingById(listingId);
        commentService.createComment(listingId, user.getId(), comment);
        listing.setCommentsCount(listingService.countCommentsByListingId(listingId));
        listingService.updateListing(listing);
        comment.setUserName(user.getName() + " " + user.getLastName());
        return "redirect:/community/listing/" + listingId;
    }

    @PostMapping("/listing/{listingId}/comment/{commentId}/remove")
    public String removeComment(@PathVariable Long listingId, @PathVariable Long commentId) {
        User user = getCurrentUser();
        Comment comment = commentService.getCommentById(commentId);
        Listing listing = listingService.getListingById(listingId);
        
        if (comment == null) {
            return "redirect:/community/listing/" + listingId;
        }

        if (comment.getUserId() != user.getId()) {
            return "redirect:/community/listing/" + listingId;
        }
        
        commentService.deleteComment(commentId);
        listing.setCommentsCount(listingService.countCommentsByListingId(listingId));
        listingService.updateListing(listing);
        return "redirect:/community/listing/" + listingId;
    }
}
