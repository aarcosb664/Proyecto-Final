package aarcosb.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

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
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "official_url", nullable = false)
    private String officialUrl;
    
    @ElementCollection
    @CollectionTable(name = "listing_images", joinColumns = @JoinColumn(name = "listing_id"))
    @Column(name = "image_url")
    private List<String> images;

    @Column(name = "video", nullable = true)
    private String video;

    @Column(name = "rating", nullable = true)
    private Double rating;

    @ElementCollection
    @Column(name = "tag", nullable = true)
    private Set<String> tags;

    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
        images = new ArrayList<>();
        rating = 0.0;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
} 