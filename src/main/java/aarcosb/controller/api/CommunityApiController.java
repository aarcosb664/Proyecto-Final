// package aarcosb.controller.api;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import jakarta.validation.Valid;

// import aarcosb.model.dto.ListingForm;
// import aarcosb.service.CloudinaryService;
// import aarcosb.model.entity.Listing;
// import aarcosb.model.entity.Comment;
// import aarcosb.model.entity.Rating;
// import aarcosb.service.ListingService;
// import aarcosb.service.CommentService;
// import aarcosb.service.RatingService;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Page;

// import java.util.List;


// @RestController
// @RequestMapping("/api/community")
// @CrossOrigin(origins = "*")
// public class CommunityApiController {

//     @Autowired
//     private ListingService listingService;
//     @Autowired
//     private CommentService commentService;
//     @Autowired
//     private RatingService ratingService;
//     @Autowired
//     private CloudinaryService cloudinaryService;

//     // --- LISTINGS ---
//     @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//     public ResponseEntity<Page<Listing>> getAllListings(Pageable pageable) {
//         return ResponseEntity.ok(listingService.getAllListings(pageable));
//     }

//     @GetMapping(value = "/listing/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//     public ResponseEntity<Listing> getListing(@PathVariable Long id) {
//         Listing listing = listingService.getListingById(id);
//         return listing != null ? ResponseEntity.ok(listing) : ResponseEntity.notFound().build();
//     }

//     @GetMapping(value = "/user/{userId}/listings", produces = MediaType.APPLICATION_JSON_VALUE)
//     public ResponseEntity<List<Listing>> getListingsByUser(@PathVariable Long userId) {
//         return ResponseEntity.ok(listingService.getListingsByUserId(userId));
//     }

//     @PostMapping(value = "/listing/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//     public ResponseEntity<Listing> createListing(
//             @Valid @ModelAttribute ListingForm form) {
//         // Validación y subida de imágenes y video
//         cloudinaryService.validateImages(form.getImages() != null ? List.of(form.getImages()) : List.of());
//         cloudinaryService.validateVideo(form.getVideo());
//         Listing listing = new Listing();
//         listing.setTitle(form.getTitle());
//         listing.setDescription(form.getDescription());
//         listing.setTags(listingService.convertTags(form.getTags()));
//         // Subir imágenes
//         try {
//             List<String> imageUrls = cloudinaryService.uploadAllImages(List.of(form.getImages()), "listings/images");
//             listing.setImages(imageUrls);
//         } catch (Exception e) {
//             throw new RuntimeException("Error uploading images: " + e.getMessage());
//         }
//         // Subir video
//         if (form.getVideo() != null && !form.getVideo().isEmpty()) {
//             try {
//                 String videoUrl = cloudinaryService.uploadFile(form.getVideo(), "listings/videos");
//                 listing.setVideo(videoUrl);
//             } catch (Exception e) {
//                 throw new RuntimeException("Error uploading video: " + e.getMessage());
//             }
//         }
//         Listing created = listingService.createListing(listing);
//         return ResponseEntity.ok(created);
//     }

//     @PostMapping(value = "/listing/{id}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//     public ResponseEntity<Listing> updateListing(
//             @PathVariable Long id,
//             @Valid @ModelAttribute ListingForm form) {
//         Listing listing = listingService.getListingById(id);
//         if (listing == null) return ResponseEntity.notFound().build();
//         listing.setTitle(form.getTitle());
//         listing.setDescription(form.getDescription());
//         listing.setTags(listingService.convertTags(form.getTags()));
//         // Subir imágenes si se envían
//         if (form.getImages() != null && form.getImages().length > 0) {
//             try {
//                 List<String> imageUrls = cloudinaryService.uploadAllImages(List.of(form.getImages()), "listings/images");
//                 listing.setImages(imageUrls);
//             } catch (Exception e) {
//                 throw new RuntimeException("Error uploading images: " + e.getMessage());
//             }
//         }
//         // Subir video si se envía
//         if (form.getVideo() != null && !form.getVideo().isEmpty()) {
//             try {
//                 String videoUrl = cloudinaryService.uploadFile(form.getVideo(), "listings/videos");
//                 listing.setVideo(videoUrl);
//             } catch (Exception e) {
//                 throw new RuntimeException("Error uploading video: " + e.getMessage());
//             }
//         }
//         Listing updated = listingService.updateListing(listing);
//         return ResponseEntity.ok(updated);
//     }

//     @PostMapping(value = "/listing/{id}/remove")
//     public ResponseEntity<Void> removeListing(@PathVariable Long id) {
//         listingService.deleteListing(id);
//         return ResponseEntity.ok().build();
//     }

//     // --- RATINGS ---
//     @PostMapping(value = "/listing/{listingId}/rate")
//     public ResponseEntity<Rating> rateListing(
//             @PathVariable Long listingId,
//             @RequestParam Long userId,
//             @RequestParam Double ratingValue) {
//         Rating ratingEntity = ratingService.rateListing(listingId, userId, ratingValue);
//         return ratingEntity != null ? ResponseEntity.ok(ratingEntity) : ResponseEntity.ok().build();
//     }

//     @GetMapping(value = "/listing/{listingId}/rating", produces = MediaType.APPLICATION_JSON_VALUE)
//     public ResponseEntity<Double> getListingRating(@PathVariable Long listingId) {
//         double averageRating = ratingService.getAverageRating(listingId);
//         return ResponseEntity.ok(averageRating);
//     }

//     @GetMapping(value = "/listing/{listingId}/user/{userId}/rating", produces = MediaType.APPLICATION_JSON_VALUE)
//     public ResponseEntity<Rating> getUserRating(@PathVariable Long listingId, @PathVariable Long userId) {
//         Rating rating = ratingService.getUserRating(listingId, userId);
//         return rating != null ? ResponseEntity.ok(rating) : ResponseEntity.notFound().build();
//     }

//     // --- COMMENTS ---
//     @GetMapping(value = "/listing/{listingId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
//     public ResponseEntity<List<Comment>> getListingComments(@PathVariable Long listingId) {
//         List<Comment> comments = commentService.getListingComments(listingId);
//         return ResponseEntity.ok(comments);
//     }

//     @PostMapping(value = "/listing/{listingId}/comment", produces = MediaType.APPLICATION_JSON_VALUE)
//     public ResponseEntity<Comment> addComment(
//             @PathVariable Long listingId,
//             @RequestParam Long userId,
//             @RequestBody Comment comment) {
//         Comment saved = commentService.createComment(listingId, userId, comment);
//         return ResponseEntity.ok(saved);
//     }

//     @PostMapping(value = "/listing/{listingId}/comment/{commentId}/remove")
//     public ResponseEntity<Void> removeComment(
//             @PathVariable Long listingId,
//             @PathVariable Long commentId) {
//         commentService.deleteComment(commentId);
//         return ResponseEntity.ok().build();
//     }
// }