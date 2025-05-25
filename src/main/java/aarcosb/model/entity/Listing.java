package aarcosb.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "listings")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 50, message = "The title must be between 1 and 50 characters")
    private String title;

    @Column(name = "description")
    @NotBlank(message = "Description is required")
    @Size(min = 50, max = 1000, message = "The description must be between 50 and 1000 characters")
    private String description;
    
    @ElementCollection
    @Column(name = "image", nullable = true)
    @Size(min = 1, max = 10, message = "There must be between 1 and 10 images")
    private List<String> images;

    @Column(name = "rating")
    @DecimalMin(value = "0.0", message = "The minimum rating is 0")
    @DecimalMax(value = "5.0", message = "The maximum rating is 5")
    private Double rating;

    @OneToMany
    @JoinColumn(name = "comments", nullable = true)
    private List<Comment> comments;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
} 