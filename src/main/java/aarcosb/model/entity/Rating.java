package aarcosb.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ratings")
public class Rating {
    // ID de la valoración
    @Id
    // Genera el ID de la valoración de forma automática
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Valor de la valoración
    @Column(name = "rating")
    @DecimalMin(value = "0.0", message = "The minimum rating is 0")
    @DecimalMax(value = "5.0", message = "The maximum rating is 5")
    private Double ratingValue;
    
    // ID del usuario que ha creado la valoración
    @Column(name = "user_id")
    private Long userId;

    // ID del listing al que pertenece la valoración
    @Column(name = "listing_id")
    private Long listingId;
} 