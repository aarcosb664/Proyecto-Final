package aarcosb.controller;

import aarcosb.model.entity.User;
import aarcosb.model.repository.UserRepository;
import aarcosb.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Controlador para la página 'about' y el envío de emails desde esa página
@Controller
public class AboutController {

    // Autowired pone aquí automáticamente los objetos que necesita la clase
    @Autowired private UserRepository userRepository;
    @Autowired private EmailSenderService emailSenderService;

    // Obtiene el usuario autenticado actual usando el email del Authentication principal
    private User getCurrentUser(UserDetails userDetails) 
    {
        return (userDetails != null) ? userRepository.findByEmail(userDetails.getUsername()) : null;
    }

    // Añade el usuario actual al modelo y retorna la vista 'about'
    @GetMapping("/about")
    public String about(Model model, @AuthenticationPrincipal UserDetails userDetails) 
    {
        User currentUser = getCurrentUser(userDetails);
        model.addAttribute("currentUser", currentUser);

        return "about";
    }

    // Envía un email usando el EmailSenderService y redirige a /about
    @PostMapping("/send-email")
    public String sendEmail(@RequestParam String subject, @RequestParam String body, @AuthenticationPrincipal UserDetails userDetails) 
    {
        User currentUser = getCurrentUser(userDetails);
        emailSenderService.sendEmail(currentUser.getEmail(), subject, body);

        return "redirect:/about";
    }
}
