package aarcosb.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating")
    @DecimalMin(value = "0.0", message = "The minimum rating is 0")
    @DecimalMax(value = "5.0", message = "The maximum rating is 5")
    private Double ratingValue;
    
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "listing_id")
    private Long listingId;
} 