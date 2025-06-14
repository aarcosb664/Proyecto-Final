package aarcosb.model.entity;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.PrePersist;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "position")
    private int position;
    
    @Column(name = "score")
    private int score;
    
    @Column(name = "destroyed_blocks")
    private int destroyedBlocks;
    
    @Column(name = "date_game")
    private LocalDate dateGame;

    @PrePersist
    protected void onCreate() {
        position = 0;
        dateGame = LocalDate.now();
    }
} 