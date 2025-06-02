package aarcosb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import aarcosb.model.entity.Listing;
import aarcosb.model.entity.Comment;
import aarcosb.model.entity.User;
import aarcosb.service.ListingService;
import aarcosb.service.CommentService;
import aarcosb.service.RatingService;
import org.springframework.security.core.context.SecurityContextHolder;
import aarcosb.model.repository.UserRepository;
import org.springframework.security.core.Authentication;

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

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("listings", listingService.getAllListings());
        model.addAttribute("activePage", "community");
        return "community/index";
    }

    // Listing related methods
    @GetMapping("/listing/{id}")
    public String viewListing(@PathVariable Long id, Model model) {
        Listing listing = listingService.getListingById(id);
        if (listing == null) {
            return "community";
        }
        model.addAttribute("listing", listing);
        model.addAttribute("comments", commentService.getListingComments(id));
        return "community/show";
    }

    @GetMapping("/user/{userId}/listings")
    public String viewUserListings(@PathVariable Long userId, Model model) {
        model.addAttribute("listings", listingService.getListingsByUserId(userId));
        return "community/user-listings";
    }

    @GetMapping("/listing/create")
    public String createListing(Model model) {
        model.addAttribute("listing", new Listing());
        return "community/create";
    }

    @PostMapping("/listing/create")
    public String createListing(@ModelAttribute Listing listing, @RequestParam("tags") String tags) {
        User user = getCurrentUser();
        listing.setUserId(user.getId());
        listing.setTags(listingService.convertTags(tags));
        listingService.createListing(listing);
        return "redirect:/community/listing/" + listing.getId();
    }

    @GetMapping("/listing/{listingId}/edit")
    public String editListing(@PathVariable Long listingId, Model model) {
        Listing listing = listingService.getListingById(listingId);
        if (listing == null) {
            return "redirect:/community";
        }

        User user = getCurrentUser();
        if (listing.getUserId() != user.getId()) {
            return "redirect:/community/listing/" + listingId;
        }

        model.addAttribute("listing", listing);
        return "community/edit";
    }

    @PostMapping("/listing/{listingId}/update")
    public String updateListing(@PathVariable Long listingId, @ModelAttribute Listing listing, @RequestParam("tags") String tags) {
        User user = getCurrentUser();

        if (listing.getUserId() != user.getId()) {
            return "redirect:/community/listing/" + listingId;
        }

        listing.setId(listingId);
        listing.setUserId(user.getId());
        listing.setTags(listingService.convertTags(tags));
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
        commentService.createComment(listingId, user.getId(), comment);
        return "redirect:/community/listing/" + listingId;
    }

    @PostMapping("/listing/{listingId}/comment/{commentId}/remove")
    public String removeComment(@PathVariable Long listingId, @PathVariable Long commentId) {
        User user = getCurrentUser();
        Comment comment = commentService.getCommentById(commentId);
        if (comment == null) {
            return "redirect:/community/listing/" + listingId;
        }

        if (comment.getUserId() != user.getId()) {
            return "redirect:/community/listing/" + listingId;
        }
        
        commentService.deleteComment(commentId);
        return "redirect:/community/listing/" + listingId;
    }
}
