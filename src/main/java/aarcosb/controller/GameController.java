package aarcosb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import aarcosb.model.entity.Player;
import aarcosb.model.repository.PlayerRepository;

@Controller
public class GameController {

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping("/game")
    public String game() {
        return "game";
    }
    
    @GetMapping("/ranking")
    public String ranking(Model model) {
        model.addAttribute("players", playerRepository.findTop5ByOrderByScoreDesc());
        return "ranking";
    }
    
    @PostMapping("/ranking")
    public String saveRanking(
            @RequestParam("userName") String userName,
            @RequestParam("score") int score,
            @RequestParam("destroyedBlocks") int destroyedBlocks,
            Model model) {
        
        // Create and save player
        playerRepository.save(new Player(userName, score, destroyedBlocks));
        
        // Get updated ranking
        model.addAttribute("players", playerRepository.findTop5ByOrderByScoreDesc());
        return "ranking";
    }
} 