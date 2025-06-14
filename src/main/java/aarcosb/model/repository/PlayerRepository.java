package aarcosb.model.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aarcosb.model.entity.Player;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    List<Player> findAll(Sort sort);
    List<Player> findByUserId(Long userId);
    List<Player> findAllByOrderByScoreDesc();
    List<Player> findTop5ByOrderByScoreDesc();
} 