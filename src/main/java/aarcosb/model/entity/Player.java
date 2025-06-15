package aarcosb.model.entity;

import java.util.Date;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "player")
public class Player {
    // ID del jugador
    @Id
    // Genera el ID del jugador de forma automática
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Nombre del usuario que ha creado el jugador
    @Column(name = "user_name")
    private String userName;

    // ID del usuario que ha creado el jugador
    @Column(name = "user_id")
    private Long userId;

    // Posición del jugador
    @Column(name = "position")
    private Integer position;
    
    // Puntuación del jugador
    @Column(name = "score")
    private int score;
    
    // Bloques destruidos por el jugador
    @Column(name = "destroyed_blocks")
    private int destroyedBlocks;
    
    // Fecha de la partida
    @Column(name = "date_game")
    private Date dateGame;

    // Método que se ejecuta antes de que se persista el jugador
    @PrePersist
    protected void onCreate() {
        position = 0;
        dateGame = new Date();
    }
} 