package aarcosb.service;

import aarcosb.model.entity.Comment;
import aarcosb.model.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

class CommentServiceTest {
    @Mock private CommentRepository commentRepository;
    private CommentService commentService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        commentService = new CommentService();
        java.lang.reflect.Field commentRepoField = CommentService.class.getDeclaredField("commentRepository");
        commentRepoField.setAccessible(true);
        commentRepoField.set(commentService, commentRepository);
    }

    @Test
    void getListingComments_callsRepository() {
        commentService.getListingComments(1L);
        verify(commentRepository).findByListingId(1L);
    }

    @Test
    void createComment_callsRepository() {
        Comment comment = new Comment();
        commentService.createComment(comment);
        verify(commentRepository).save(comment);
    }

    @Test
    void deleteComment_callsRepository() {
        commentService.deleteComment(1L);
        verify(commentRepository).deleteById(1L);
    }
} 