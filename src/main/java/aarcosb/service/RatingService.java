package aarcosb.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import aarcosb.model.entity.Rating;
import aarcosb.model.repository.RatingRepository;
import aarcosb.model.repository.ListingRepository;

@Service
@Transactional
public class RatingService {
    @Autowired private RatingRepository ratingRepository;
    @Autowired private ListingRepository listingRepository;

    public double getAverageRating(Long listingId) {
        return ratingRepository.findAverageRatingByListingId(listingId);
    }

    public Double getUserRating(Long listingId, Long userId) {
        Rating rating = ratingRepository.findByListingIdAndUserId(listingId, userId);
        return rating != null ? rating.getRatingValue() : 0.0;
    }

    public Rating rateListing(Long listingId, Long userId, Double ratingValue) {
        if (ratingValue < 0.0 || ratingValue > 5.0) {
            throw new IllegalArgumentException("The rating value must be between 0.0 and 5.0");
        }

        Rating rating = ratingRepository.findByListingIdAndUserId(listingId, userId);
        
        if (rating != null) {
            if (rating.getRatingValue().equals(ratingValue)) {
                ratingRepository.delete(rating);
                updateListingRating(listingId);
                return null;
            }
            rating.setRatingValue(ratingValue);
            Rating savedRating = ratingRepository.save(rating);
            updateListingRating(listingId);
            return savedRating;
        }

        Rating newRating = new Rating();
        newRating.setListingId(listingId);
        newRating.setUserId(userId);
        newRating.setRatingValue(ratingValue);
        Rating savedRating = ratingRepository.save(newRating);
        updateListingRating(listingId);
        return savedRating;
    }

    private void updateListingRating(Long listingId) {
        Double avg = ratingRepository.findAverageRatingByListingId(listingId);
        if (avg == null) {
            avg = 0.0;
        }
        
        listingRepository.updateRatingOnly(listingId, avg);
    }
} 