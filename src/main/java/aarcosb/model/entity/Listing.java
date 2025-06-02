package aarcosb.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;
import java.util.List;
import java.util.Set;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;

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
    @Size(min = 1, max = 100, message = "The title must be between 1 and 100 characters")
    private String title;

    @Column(name = "description")
    @NotBlank(message = "Description is required")
    @Size(min = 30, max = 500, message = "The description must be between 30 and 500 characters")
    private String description;
    
    @ElementCollection
    @Column(name = "image", nullable = false)
    @NotEmpty(message = "At least one image is required")
    private List<String> images;

    @Column(name = "video", nullable = true)
    private String video;

    @ElementCollection
    @Column(name = "tags", nullable = true)
    private Set<String> tags;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
} 