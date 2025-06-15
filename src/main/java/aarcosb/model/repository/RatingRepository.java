package aarcosb.model.repository;

import aarcosb.model.entity.Rating;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    // Busca las valoraciones de un listing
    List<Rating> findByListingId(Long listingId);

    // Busca las valoraciones de un usuario
    List<Rating> findByUserId(Long userId);

    // Busca una valoración por el ID del listing y el ID del usuario
    Rating findByListingIdAndUserId(Long listingId, Long userId);

    // Busca la valoración media de un listing
    @Query("SELECT AVG(r.ratingValue) FROM Rating r WHERE r.listingId = :listingId")
    Double findAverageRatingByListingId(Long listingId);
}
