package aarcosb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import aarcosb.model.entity.User;
import aarcosb.model.repository.UserRepository;
import aarcosb.model.repository.PlayerRepository;
import aarcosb.service.UserDetailsServiceImpl;
import aarcosb.service.CloudinaryService;

@Controller
public class IndexController {

    @Autowired private UserRepository userRepository;
    @Autowired private PlayerRepository playerRepository;
    @Autowired private CloudinaryService cloudinaryService;
    @Autowired private UserDetailsServiceImpl userDetailsService;

    // Método auxiliar para obtener el usuario actual a partir de la autenticación
    private User getCurrentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName());
    }

    @GetMapping("/")
    public String index(Model model, Authentication authentication) {
        if (authentication != null) {
            User user = userRepository.findByEmail(authentication.getName());
            model.addAttribute("user", user);
        }
        
        model.addAttribute("players", playerRepository.findTop5ByOrderByScoreDesc());
        return "index";
    }

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute("user", user);
        model.addAttribute("myListings", userDetailsService.getListings(id));
        model.addAttribute("myFavorites", userDetailsService.getFavorites(id));
        model.addAttribute("totalListings", userDetailsService.countTotalListings(id));
        model.addAttribute("totalFavorites", userDetailsService.countTotalFavorites(id));
        return "profile";
    }

    @PostMapping("/profile/updateProfilePic")
    public String updateProfilePic(@RequestParam("profilePic") MultipartFile file,
                                    Authentication authentication,
                                    Model model) 
    {
        User user = getCurrentUser(authentication);
        model.addAttribute("user", user);

        // Subir la imagen a Cloudinary
        try {
            cloudinaryService.uploadUserProfilePic(user, file);
            return "redirect:/profile/" + user.getId();
        } catch (Exception e) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", "Error updating profile picture: " + e.getMessage());
            return "profile";
        }
    }
}
