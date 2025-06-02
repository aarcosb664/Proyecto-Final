package aarcosb.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import aarcosb.model.entity.Rating;
import aarcosb.model.repository.RatingRepository;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public double getAverageRating(Long listingId) {
        return ratingRepository.findAverageRatingByListingId(listingId);
    }

    public Rating rateListing(Long listingId, Long userId, Double ratingValue) {
        Rating rating = ratingRepository.findByListingIdAndUserId(listingId, userId);

        if (rating != null) {
            if (rating.getRatingValue() == ratingValue) {
                throw new IllegalArgumentException("You can't rate the same listing with the same value");
            } else if (ratingValue < 0.0 || ratingValue > 5.0) {
                throw new IllegalArgumentException("The rating value must be between 0.0 and 5.0");
            } else {
                rating.setRatingValue(ratingValue);
                return ratingRepository.save(rating);
            }
        } else {
            Rating newRating = new Rating();
            newRating.setListingId(listingId);
            newRating.setUserId(userId);
            newRating.setRatingValue(ratingValue);
            return ratingRepository.save(newRating);
        }
    }

    public Rating getUserRating(Long listingId, Long userId) {
        return ratingRepository.findByListingIdAndUserId(listingId, userId);
    }
}
