package aarcosb.model.entity;

import jakarta.persistence.*;
import java.util.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "listings")
public class Listing {
    // ID del listing
    @Id
    // Genera el ID del listing de forma automática
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Título del listing
    @Column(name = "title")
    private String title;

    // Descripción del listing
    @Column(name = "description")
    private String description;

    // Nombre del usuario que ha creado el listing
    @Column(name = "user_name")
    private String userName;

    // URL oficial del listing
    @Column(name = "official_url", nullable = false)
    private String officialUrl;
    
    // Imágenes del listing
    @ElementCollection
    @CollectionTable(name = "listing_images", joinColumns = @JoinColumn(name = "listing_id"))
    @Column(name = "image_url")
    private List<String> images = new ArrayList<>();

    // Vídeo del listing
    @Column(name = "video", nullable = true)
    private String video;

    // Valoración del listing
    @Column(name = "rating", nullable = true)
    private Double rating;

    // Etiquetas del listing
    @ElementCollection
    @Column(name = "tag", nullable = true)
    private Set<String> tags = new HashSet<>();

    // ID del usuario que ha creado el listing
    @Column(name = "user_id")
    private Long userId;

    // Fecha de creación del listing
    @Column(name = "created_at")
    private Date createdAt;

    // Fecha de actualización del listing
    @Column(name = "updated_at")
    private Date updatedAt;

    // Método que se ejecuta antes de que se persista el listing
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
        // images = new ArrayList<>();
        rating = 0.0;
    }

    // Método que se ejecuta antes de que se actualice el listing
    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
} 