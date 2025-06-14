package aarcosb.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

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
    private String userName;

    @Column(name = "title")
    private String title;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "listing_id")
    private Long listingId;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }
}

