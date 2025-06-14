package aarcosb.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import aarcosb.model.entity.Listing;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByUserId(Long userId);
    
    List<Listing> findByIdIn(List<Long> ids);

    @Query(value = "SELECT image_url FROM listing_images WHERE listing_id = :listingId", nativeQuery = true)
    List<String> findImagesByListingId(@Param("listingId") Long listingId);

    @Query("SELECT DISTINCT l FROM Listing l LEFT JOIN FETCH l.images WHERE l.id = :id")
    Listing findByIdWithImages(@Param("id") Long id);

    @Query("SELECT l FROM Listing l JOIN l.tags t WHERE t = LOWER(:tag)")
    List<Listing> retrieveByTag(String tag);

    @Query("SELECT l FROM Listing l JOIN l.tags t WHERE l.title = LOWER(:title) AND t = LOWER(:tag)")
    List<Listing> retrieveByNameFilterByTag(String title, String tag);

    @Query(value = "SELECT COUNT(*) FROM user_fav_listings WHERE fav_listings = :listingId", nativeQuery = true)
    int countTotalFavorites(@Param("listingId") Long listingId);

    @Query("SELECT DISTINCT l FROM Listing l " +
           "LEFT JOIN l.tags t " +
           "WHERE " +
           "(:query IS NULL OR " +
           "   LOWER(l.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "   LOWER(l.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "   LOWER(l.userName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "   LOWER(t) LIKE LOWER(CONCAT('%', :query, '%'))" +
           ") AND " +
           "(:minRating IS NULL OR l.rating >= :minRating) AND " +
           "(:maxRating IS NULL OR l.rating <= :maxRating) AND " +
           "(:dateFrom IS NULL OR l.createdAt >= :dateFrom) AND " +
           "(:dateTo IS NULL OR l.createdAt <= :dateTo)")
    Page<Listing> searchAndFilter(
        @Param("query") String query,
        @Param("minRating") Double minRating,
        @Param("maxRating") Double maxRating,
        @Param("dateFrom") LocalDate dateFrom,
        @Param("dateTo") LocalDate dateTo,
        Pageable pageable
    );

    @Modifying
    @Query("UPDATE Listing l SET l.rating = :rating WHERE l.id = :listingId")
    void updateRatingOnly(Long listingId, Double rating);
} 