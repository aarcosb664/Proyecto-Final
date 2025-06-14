package aarcosb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import aarcosb.model.entity.Player;
import aarcosb.model.repository.PlayerRepository;


@Service
public class GameService {
  
    @Autowired PlayerRepository playerRepository;

    public List<Player> findAll(Sort sort) {
        return playerRepository.findAll(sort);
    }

    public void saveGame(Player player) {
        playerRepository.save(player);
    }

    public void setAllPlayersPosition() {
        List<Player> playersByScoreDesc = playerRepository.findAllByOrderByScoreDesc();
        int position = 1;
        
        for (Player player : playersByScoreDesc) {
            player.setPosition(position);
            playerRepository.save(player);
            position++;
        }
    }
}
