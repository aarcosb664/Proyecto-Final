package aarcosb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import aarcosb.model.entity.Player;
import aarcosb.model.repository.UserRepository;
import aarcosb.model.entity.User;
import aarcosb.service.GameService;
import java.util.List;

@Controller
public class GameController {

    @Autowired private GameService gameService;
    @Autowired private UserRepository userRepository;

    // Método auxiliar para obtener el usuario actual a partir de la autenticación
    private User getCurrentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName());
    }

    @GetMapping("/game")
    public String game(Authentication authentication, Model model) 
    {
        User currentUser = getCurrentUser(authentication);
        model.addAttribute("currentUser", currentUser);

        return "game";
    }
    
    @GetMapping("/ranking")
    public String ranking(@RequestParam(defaultValue = "score") String sort,
                          @RequestParam(defaultValue = "desc") String order,
                          Authentication authentication, Model model) 
    {
        User currentUser = getCurrentUser(authentication);
        model.addAttribute("currentUser", currentUser);
        gameService.setAllPlayersPosition();

        Sort sortBy = order.equals("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        List<Player> players = gameService.findAll(sortBy);

        model.addAttribute("sort", sort);
        model.addAttribute("order", order);
        model.addAttribute("players", players);
        return "ranking";
    }
    
    @PostMapping("/saveGame")
    public String saveRanking(@RequestParam("userName") String userName,
                              @RequestParam("score") int score,
                              @RequestParam("destroyedBlocks") int destroyedBlocks,
                              Authentication authentication,
                              Model model)
    {
        User currentUser = getCurrentUser(authentication);
        model.addAttribute("currentUser", currentUser);

        // Crear y guardar jugador
        Player player = new Player();
        player.setUserName(userName);
        player.setUserId(currentUser.getId());
        player.setScore(score);
        player.setDestroyedBlocks(destroyedBlocks);
        gameService.saveGame(player);
        
        return "game";
    }
} 