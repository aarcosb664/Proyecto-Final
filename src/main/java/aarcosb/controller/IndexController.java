package aarcosb.controller;

import aarcosb.model.entity.User;
import aarcosb.model.repository.PlayerRepository;
import aarcosb.model.repository.UserRepository;
import aarcosb.service.CloudinaryService;
import aarcosb.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class IndexController {

    // Autowired pone aquí automáticamente los objetos que necesita la clase
    @Autowired private UserRepository userRepository;
    @Autowired private PlayerRepository playerRepository;
    @Autowired private CloudinaryService cloudinaryService;
    @Autowired private UserDetailsServiceImpl userDetailsService;

    // Obtiene el usuario autenticado actual usando el email
    private User getCurrentUser(Authentication authentication) 
    {
        return userRepository.findByEmail(authentication.getName());
    }

    // Muestra la página de inicio con el usuario actual y los 5 mejores jugadores
    @GetMapping("/")
    public String index(Model model, Authentication authentication) 
    {
        User currentUser = getCurrentUser(authentication);
        model.addAttribute("currentUser", currentUser);
        
        model.addAttribute("players", playerRepository.findTop5ByOrderByScoreDesc());
        return "index";
    }

    // Muestra el perfil de un usuario con el usuario actual
    @GetMapping("/profile/{id}")
    public String profile(@PathVariable Long id, Model model, Authentication authentication) 
    {
        User currentUser = getCurrentUser(authentication);
        model.addAttribute("currentUser", currentUser);
        
        // Si el usuario no existe, lanza una excepción
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        
        // Añade los datos del perfil al modelo
        model.addAttribute("user", user);
        model.addAttribute("myListings", userDetailsService.getListings(id));
        model.addAttribute("myFavorites", userDetailsService.getFavorites(id));
        model.addAttribute("totalListings", userDetailsService.countTotalListings(id));
        model.addAttribute("totalFavorites", userDetailsService.countTotalFavorites(id));
        model.addAttribute("allGames", userDetailsService.getGames(id));
        return "profile";
    }
    
    // Actualiza la imagen de perfil de un usuario
    @PostMapping("/profile/updateProfilePic")
    public String updateProfilePic(@RequestParam("id") Long id,
                                   @RequestParam("profilePic") MultipartFile file,
                                   Authentication authentication,
                                   Model model) 
    {
        User currentUser = getCurrentUser(authentication);
        model.addAttribute("currentUser", currentUser);
        
        // Si el usuario no es admin y no es el propietario del perfil, lanza una excepción
        if (currentUser.getRole().name() == "USER" && !currentUser.getId().equals(id)) {
            throw new IllegalArgumentException("User not authorized");
        }
        
        // Sube la imagen a Cloudinary
        try {
            cloudinaryService.uploadUserProfilePic(currentUser, file);
            return "redirect:/profile/" + currentUser.getId();
        } catch (Exception e) {
            // Si hay errores, añade el formulario de actualización de perfil al modelo y lanza una excepción
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("hasError", true);
            model.addAttribute("error", "Error updating profile picture: " + e.getMessage());
            return "profile";
        }
    }
}
