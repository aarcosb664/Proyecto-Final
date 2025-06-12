package aarcosb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.validation.Valid;
import aarcosb.model.dto.UserDto;
import aarcosb.model.entity.User;
import aarcosb.model.repository.UserRepository;
import java.security.Principal;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private SecurityContextRepository securityContextRepository;
    
    /**
     * Muestra la página de login
     */
    @GetMapping("/login")
    public String login(@RequestParam(defaultValue = "login") String tab, Model model, Principal principal) {
        // Si el usuario ya está autenticado, redirigir a la página de inicio
        if (principal != null) return "redirect:/";

        // Añadir el formulario de registro al modelo
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserDto());
        }
        
        model.addAttribute("tab", tab.toLowerCase());
        return "login";
    }
    
    /**
     * Procesa el registro de usuarios
     */
    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("user") UserDto dto,
            BindingResult result, RedirectAttributes redirect,
            HttpServletRequest request, HttpServletResponse response) {

        // Si el email ya está registrado, rechazar el registro
        if (userRepository.existsByEmail(dto.getEmail())) {
            result.rejectValue("email", "duplicated", "Email ya registrado");
        }

        // Si las contraseñas no coinciden, rechazar el registro
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "mismatch", "Las contraseñas no coinciden");
        }

        // Si el userName ya está registrado, rechazar el registro
        if (userRepository.existsByUserName(dto.getUserName())) {
            result.rejectValue("userName", "duplicated", "Username already taken");
        }

        // Si hay errores, volver a la vista de registro
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirect.addFlashAttribute("user", dto);
            return "redirect:/login?tab=register";
        }

        // Crear usuario
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setUserName(dto.getUserName());
        userRepository.save(user);

        // Autenticar usuario
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        // Crear contexto de autenticación
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        // Establecer autenticación en el contexto
        context.setAuthentication(auth);
        securityContextRepository.saveContext(context, request, response);

        return "redirect:/";
    }
}
