package aarcosb.controller;

import aarcosb.model.entity.Player;
import aarcosb.model.entity.User;
import aarcosb.model.repository.UserRepository;
import aarcosb.service.GameService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GameController {

    // Autowired pone aquí automáticamente los objetos que necesita la clase
    @Autowired private GameService gameService;
    @Autowired private UserRepository userRepository;

    // Obtiene el usuario autenticado actual usando el email
    private User getCurrentUser(UserDetails userDetails) {
        return (userDetails != null) ? userRepository.findByEmail(userDetails.getUsername()) : null;
    }

    // Muestra la vista principal del juego con el usuario actual
    @GetMapping("/game")
    public String game(@AuthenticationPrincipal UserDetails userDetails, Model model) 
    {
        User currentUser = getCurrentUser(userDetails);
        model.addAttribute("currentUser", currentUser);

        return "game";
    }
    
    // Muestra el ranking de jugadores ordenado por el campo y dirección especificados
    @GetMapping("/ranking")
    public String ranking(@RequestParam(defaultValue = "score") String sort,
                          @RequestParam(defaultValue = "desc") String order,
                          @AuthenticationPrincipal UserDetails userDetails, 
                          Model model) 
    {
        User currentUser = getCurrentUser(userDetails);
        model.addAttribute("currentUser", currentUser);

        // Establece la posición de todos los jugadores
        gameService.setAllPlayersPosition();
        
        // Ordena los jugadores según los parámetros recibidos
        Sort sortBy = order.equals("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        List<Player> players = gameService.findAll(sortBy);

        model.addAttribute("sort", sort);
        model.addAttribute("order", order);
        model.addAttribute("players", players);
        return "ranking";
    }
    
    // Guarda la puntuación de un jugador después de una partida
    @PostMapping("/saveGame")
    public String saveRanking(@RequestParam("userName") String userName,
                              @RequestParam("score") int score,
                              @RequestParam("destroyedBlocks") int destroyedBlocks,
                              @AuthenticationPrincipal UserDetails userDetails,
                              Model model)
    {
        User currentUser = getCurrentUser(userDetails);
        model.addAttribute("currentUser", currentUser);
        
        // Crea y guarda el jugador con los datos de la partida
        Player player = new Player();
        player.setUserName(userName);
        player.setUserId(currentUser.getId());
        player.setScore(score);
        player.setDestroyedBlocks(destroyedBlocks);
        gameService.saveGame(player);

        return "game";
    }
} 