package aarcosb.model.repository;

import aarcosb.model.entity.Player;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    // Busca los jugadores de un usuario
    List<Player> findByUserId(Long userId);

    // Busca todos los jugadores ordenados por puntuación de forma descendente
    List<Player> findAllByOrderByScoreDesc();

    // Busca los 5 mejores jugadores ordenados por puntuación de forma descendente
    List<Player> findTop5ByOrderByScoreDesc();
} 