package aarcosb.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aarcosb.model.entity.Player;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    
    // Find top 5 players by score
    List<Player> findTop5ByOrderByScoreDesc();
} 