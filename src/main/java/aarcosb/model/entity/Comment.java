package aarcosb.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    @NotBlank(message = "User name is required")
    @Size(min = 1, max = 250, message = "The user name must be between 1 and 250 characters")
    private String userName;

    @Column(name = "title")
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 250, message = "The title must be between 1 and 250 characters")
    private String title;

    @Column(name = "text", columnDefinition = "TEXT")
    @NotBlank(message = "Comment text is required")
    @Size(min = 1, max = 250, message = "The comment must be between 1 and 250 characters")
    private String text;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "listing_id")
    private Long listingId;
}

