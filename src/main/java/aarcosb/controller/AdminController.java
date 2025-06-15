package aarcosb.controller;

import aarcosb.model.entity.User;
import aarcosb.model.repository.UserRepository;
import aarcosb.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // Autowired pone aquí automáticamente los objetos que necesita la clase
    @Autowired private UserRepository userRepository;
    @Autowired private UserDetailsServiceImpl userDetailsServiceImpl;

    // Obtiene el usuario autenticado actual usando el email
    private User getCurrentUser(UserDetails userDetails) 
    {
        return (userDetails != null) ? userRepository.findByEmail(userDetails.getUsername()) : null;
    }

    // Muestra el panel de administración con el usuario actual y la lista de usuarios
    @GetMapping("/panel")
    public String admin(Model model, @AuthenticationPrincipal UserDetails userDetails) 
    {
        User currentUser = getCurrentUser(userDetails);
        model.addAttribute("currentUser", currentUser);

        // Añade la lista de usuarios al modelo
        model.addAttribute("users", userRepository.findAll());

        return "admin/panel";
    }

    // Elimina un usuario por su ID y redirige al panel de administración
    @PostMapping("/user/{userId}/delete")
    public String deleteUser(@PathVariable Long userId) 
    {
        User user = userRepository.findById(userId).orElse(null);

        // Si el usuario no existe, lanza una excepción
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        userDetailsServiceImpl.deleteUser(userId);
        return "redirect:/admin/panel";
    }
}
