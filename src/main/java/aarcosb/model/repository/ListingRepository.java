package aarcosb.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import aarcosb.model.entity.Listing;
import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByUserId(Long userId);

    @Query("SELECT s FROM Listing s JOIN s.tags t WHERE t = LOWER(:tag)")
    List<Listing> retrieveByTag(@Param("tag") String tag);

    @Query("SELECT s FROM Listing s JOIN s.tags t WHERE s.title = LOWER(:title) AND t = LOWER(:tag)")
    List<Listing> retrieveByNameFilterByTag(@Param("title") String title, @Param("tag") String tag);
} 