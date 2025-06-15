package aarcosb.service;

import aarcosb.model.entity.Player;
import aarcosb.model.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import static org.mockito.Mockito.*;

class GameServiceTest {
    @Mock private PlayerRepository playerRepository;
    private GameService gameService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        gameService = new GameService();
        java.lang.reflect.Field playerRepoField = GameService.class.getDeclaredField("playerRepository");
        playerRepoField.setAccessible(true);
        playerRepoField.set(gameService, playerRepository);
    }

    @Test
    void findAll_callsRepository() {
        Sort sort = Sort.by("score");
        gameService.findAll(sort);
        verify(playerRepository).findAll(sort);
    }

    @Test
    void setAllPlayersPosition_callsRepository() {
        gameService.setAllPlayersPosition();
        verify(playerRepository).findAllByOrderByScoreDesc();
    }

    @Test
    void saveGame_callsRepository() {
        Player player = new Player();
        gameService.saveGame(player);
        verify(playerRepository).save(player);
    }
} 