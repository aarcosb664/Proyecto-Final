package aarcosb.service;

import aarcosb.model.entity.Comment;
import aarcosb.model.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public List<Comment> getListingComments(Long listingId) {
        return commentRepository.findByListingId(listingId);
    }

    public Comment createComment(Long listingId, Long userId, Comment comment) {
        Date now = new Date();
        comment.setListingId(listingId);
        comment.setUserId(userId);
        comment.setCreatedAt(now);
        comment.setUpdatedAt(now);
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
