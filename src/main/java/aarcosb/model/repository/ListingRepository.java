package aarcosb.model.repository;

import aarcosb.model.entity.Listing;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    // Busca los listings de un usuario
    List<Listing> findByUserId(Long userId);

    // Busca los listings por sus IDs
    List<Listing> findByIdIn(List<Long> ids);

    // Busca los listings por una etiqueta
    @Query("SELECT l FROM Listing l JOIN l.tags t WHERE t = LOWER(:tag)")
    List<Listing> retrieveByTag(String tag);

    // Busca los listings por un título y una etiqueta
    @Query("SELECT l FROM Listing l JOIN l.tags t WHERE l.title = LOWER(:title) AND t = LOWER(:tag)")
    List<Listing> retrieveByNameFilterByTag(String title, String tag);

    // Cuenta el número de favoritos de un listing
    @Query(value = "SELECT COUNT(*) FROM user_fav_listings WHERE fav_listings = :listingId", nativeQuery = true)
    int countTotalFavorites(@Param("listingId") Long listingId);

    // Busca los listings por un título, una descripción, un nombre de usuario y una etiqueta con filtros y paginación
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

    // Actualiza la valoración de un listing sin actualizar las imágenes y el vídeo
    @Modifying
    @Query("UPDATE Listing l SET l.rating = :rating WHERE l.id = :listingId")
    void updateRatingOnly(Long listingId, Double rating);
} 