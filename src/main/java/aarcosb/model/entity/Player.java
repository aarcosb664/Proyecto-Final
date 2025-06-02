package aarcosb.model.entity;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_id")
    private int userId;
    
    @Column(name = "score")
    private int score;
    
    @Column(name = "destroyed_blocks")
    private int destroyedBlocks;
    
    @Column(name = "date_game")
    private Date dateGame;

    public Player(String userName, int score, int destroyedBlocks) {
        this.userName = userName;
        this.score = score;
        this.destroyedBlocks = destroyedBlocks;
        this.dateGame = new Date();
    }
} 