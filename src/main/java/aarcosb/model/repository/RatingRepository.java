package aarcosb.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import aarcosb.model.entity.Rating;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByListingId(Long listingId);
    List<Rating> findByUserId(Long userId);
    Rating findByListingIdAndUserId(Long listingId, Long userId);

    @Query("SELECT AVG(r.ratingValue) FROM Rating r WHERE r.listingId = :listingId")
    Double findAverageRatingByListingId(Long listingId);
}
