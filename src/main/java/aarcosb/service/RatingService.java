package aarcosb.service;

import aarcosb.model.entity.Rating;
import aarcosb.model.repository.ListingRepository;
import aarcosb.model.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RatingService {

    // Autowired pone aquí automáticamente los objetos que necesita la clase
    @Autowired private RatingRepository ratingRepository;
    @Autowired private ListingRepository listingRepository;

    // Obtiene la valoración media de un listing
    public double getAverageRating(Long listingId) {
        return ratingRepository.findAverageRatingByListingId(listingId);
    }

    // Obtiene la valoración de un usuario en un listing
    public Double getUserRating(Long listingId, Long userId) {
        Rating rating = ratingRepository.findByListingIdAndUserId(listingId, userId);
        // Si la valoración existe, la retorna, si no, retorna 0.0
        return rating != null ? rating.getRatingValue() : 0.0;
    }

    // Valora un listing
    public Rating rateListing(Long listingId, Long userId, Double ratingValue) {
        // Si la valoración no está entre 0.0 y 5.0, lanza una excepción
        if (ratingValue < 0.0 || ratingValue > 5.0) {
            throw new IllegalArgumentException("The rating value must be between 0.0 and 5.0");
        }

        Rating rating = ratingRepository.findByListingIdAndUserId(listingId, userId);

        // Si la valoración existe y es igual a la valoración que se está intentando guardar, la elimina y actualiza la valoración media del listing
        if (rating != null) {
            if (rating.getRatingValue().equals(ratingValue)) {
                ratingRepository.delete(rating);
                updateListingRating(listingId);
                return null;
            }
    
            // Si la valoración existe y es diferente a la valoración que se está intentando guardar, la actualiza y actualiza la valoración media del listing
            rating.setRatingValue(ratingValue);
            Rating savedRating = ratingRepository.save(rating);
            updateListingRating(listingId);
            return savedRating;
        }

        // Si la valoración no existe, la crea y actualiza la valoración media del listing
        Rating newRating = new Rating();
        newRating.setListingId(listingId);
        newRating.setUserId(userId);
        newRating.setRatingValue(ratingValue);
        Rating savedRating = ratingRepository.save(newRating);
        updateListingRating(listingId);
        return savedRating;
    }

    // Actualiza la valoración media de un listing
    private void updateListingRating(Long listingId) {
        Double avg = ratingRepository.findAverageRatingByListingId(listingId);

        // Si la valoración media es null, la establece a 0.0
        if (avg == null) {
            avg = 0.0;
        }

        listingRepository.updateRatingOnly(listingId, avg);
    }
} 