package aarcosb.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import aarcosb.model.entity.Rating;
import aarcosb.model.repository.RatingRepository;
import aarcosb.model.entity.Listing;
import aarcosb.model.repository.ListingRepository;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private ListingRepository listingRepository;

    public double getAverageRating(Long listingId) {
        return ratingRepository.findAverageRatingByListingId(listingId);
    }

    public Rating getUserRating(Long listingId, Long userId) {
        return ratingRepository.findByListingIdAndUserId(listingId, userId);
    }

    public Rating rateListing(Long listingId, Long userId, Double ratingValue) {
        Rating rating = ratingRepository.findByListingIdAndUserId(listingId, userId);

        if (rating != null) {
            if (rating.getRatingValue().equals(ratingValue)) {
                ratingRepository.delete(rating);
                updateListingRating(listingId);
                return null;
            } else if (ratingValue < 0.0 || ratingValue > 5.0) {
                throw new IllegalArgumentException("The rating value must be between 0.0 and 5.0");
            } else {
                rating.setRatingValue(ratingValue);
                Rating savedRating = ratingRepository.save(rating);
                updateListingRating(listingId);
                return savedRating;
            }
        } else {
            Rating newRating = new Rating();
            newRating.setListingId(listingId);
            newRating.setUserId(userId);
            newRating.setRatingValue(ratingValue);
            Rating savedRating = ratingRepository.save(newRating);
            updateListingRating(listingId);
            return savedRating;
        }
    }

    private void updateListingRating(Long listingId) {
        Double avg = ratingRepository.findAverageRatingByListingId(listingId);
        Listing listing = listingRepository.findById(listingId).orElse(null);
        if (listing != null) {
            listing.setRating(avg);
            listingRepository.save(listing);
        }
    }
}
