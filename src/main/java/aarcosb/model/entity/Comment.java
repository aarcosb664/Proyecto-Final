package aarcosb.model.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {
    // ID del comentario
    @Id
    // Genera el ID del comentario de forma automática
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del usuario que ha creado el comentario
    @Column(name = "user_name")
    private String userName;

    // Título del comentario
    @Column(name = "title")
    private String title;

    // Texto del comentario
    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    // Fecha de creación del comentario
    @Column(name = "created_at")
    private Date createdAt;

    // ID del usuario que ha creado el comentario
    @Column(name = "user_id")
    private Long userId;

    // ID del listing al que pertenece el comentario
    @Column(name = "listing_id")
    private Long listingId;

    // Método que se ejecuta antes de que se persista el comentario
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
}

