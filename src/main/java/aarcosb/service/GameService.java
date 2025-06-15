package aarcosb.service;

import aarcosb.model.entity.Player;
import aarcosb.model.repository.PlayerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    // Autowired pone aquí automáticamente los objetos que necesita la clase
    @Autowired private PlayerRepository playerRepository;

    // Obtiene todos los jugadores ordenados por puntuación de forma descendente
    public List<Player> findAll(Sort sort) {
        return playerRepository.findAll(sort);
    }

    // Guarda un juego
    public void saveGame(Player player) {
        playerRepository.save(player);
    }

    // Establece la posición de todos los jugadores
    public void setAllPlayersPosition() {
        // Obtiene todos los jugadores ordenados por puntuación de forma descendente
        List<Player> playersByScoreDesc = playerRepository.findAllByOrderByScoreDesc();
        int position = 1;

        // Recorre todos los jugadores ordenados por puntuación de forma descendente
        for (Player player : playersByScoreDesc) {
            player.setPosition(position);
            playerRepository.save(player);
            position++;
        }
    }
}
