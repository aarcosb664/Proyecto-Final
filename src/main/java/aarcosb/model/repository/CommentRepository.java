package aarcosb.model.repository;

import aarcosb.model.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Busca los comentarios de un listing
    List<Comment> findByListingId(Long listingId);
}
