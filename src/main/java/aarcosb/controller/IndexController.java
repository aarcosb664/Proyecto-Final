package aarcosb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import aarcosb.model.entity.User;
import aarcosb.model.repository.UserRepository;
import aarcosb.model.repository.PlayerRepository;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping("/")
    public String index(Model model, Authentication authentication) {
        model.addAttribute("activePage", "index");
        
        if (authentication != null) {
            User user = userRepository.findByEmail(authentication.getName());
            model.addAttribute("user", user);
        }
        
        model.addAttribute("players", playerRepository.findTop5ByOrderByScoreDesc());
        return "index";
    }
}
