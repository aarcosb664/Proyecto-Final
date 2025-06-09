package aarcosb.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import aarcosb.model.entity.Listing;
import java.util.Date;
import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByUserId(Long userId);

    @Query("SELECT s FROM Listing s JOIN s.tags t WHERE t = LOWER(:tag)")
    List<Listing> retrieveByTag(@Param("tag") String tag);

    @Query("SELECT s FROM Listing s JOIN s.tags t WHERE s.title = LOWER(:title) AND t = LOWER(:tag)")
    List<Listing> retrieveByNameFilterByTag(@Param("title") String title, @Param("tag") String tag);

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
        @Param("dateFrom") Date dateFrom,
        @Param("dateTo") Date dateTo,
        Pageable pageable
    );
} 