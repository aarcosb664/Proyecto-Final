package aarcosb.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int score;
    private int totalDestroyedBlocks;
    private Date finishTime;

    public Player(String name, int score, int totalDestroyedBlocks) {
        this.name = name;
        this.score = score;
        this.totalDestroyedBlocks = totalDestroyedBlocks;
        this.finishTime = new Date();
    }
}