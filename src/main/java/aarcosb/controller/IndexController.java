package aarcosb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import aarcosb.model.repository.PlayerRepository;
import aarcosb.model.repository.UserRepository;
import java.security.Principal;
import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index(Model model, Principal principal, HttpSession session) {
        model.addAttribute("players", playerRepository.findTop5ByOrderByScoreDesc());
        model.addAttribute("activePage", "index");
        
        // Si el usuario no está en la sesión, añadirlo
        if (principal != null && session.getAttribute("user") == null) {
            session.setAttribute("user", userRepository.findByEmail(principal.getName()));
        }
        
        return "index";
    }
}
