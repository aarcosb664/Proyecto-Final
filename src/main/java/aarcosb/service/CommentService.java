package aarcosb.service;

import aarcosb.model.entity.Comment;
import aarcosb.model.repository.CommentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    // Autowired pone aquí automáticamente los objetos que necesita la clase
    @Autowired private CommentRepository commentRepository;

    // Obtiene un comentario por su ID
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    // Obtiene los comentarios de un listing
    public List<Comment> getListingComments(Long listingId) {
        return commentRepository.findByListingId(listingId);
    }

    // Crea un comentario
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    // Elimina un comentario
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
