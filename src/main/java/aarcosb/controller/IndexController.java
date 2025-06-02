package aarcosb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import aarcosb.model.repository.PlayerRepository;

@Controller
public class IndexController {
    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("players", playerRepository.findTop5ByOrderByScoreDesc());
        model.addAttribute("activePage", "index");
        return "index";
    }
}
