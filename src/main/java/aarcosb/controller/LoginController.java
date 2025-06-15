package aarcosb.controller;

import aarcosb.model.dto.UserDto;
import aarcosb.model.entity.User;
import aarcosb.model.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    
    // Autowired pone aquí automáticamente los objetos que necesita la clase
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private SecurityContextRepository securityContextRepository;

    // Muestra la página de login
    @GetMapping("/login")
    public String login(@RequestParam(defaultValue = "login") String tab, 
                        Model model, 
                        Principal principal) 
    {
        // Si el usuario ya está autenticado, lanza una excepción
        if (principal != null) {
            throw new IllegalArgumentException("User already authenticated");
        }

        // Si no existe el formulario de registro, lo añade al modelo
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserDto());
        }
        
        model.addAttribute("tab", tab.toLowerCase());
        return "login";
    }
    
    // Procesa el registro de usuarios
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserDto dto,
                           BindingResult result, 
                           RedirectAttributes redirect,
                           HttpServletRequest request, 
                           HttpServletResponse response) 
    {
        // Si el email ya está registrado, añade un error al formulario
        if (userRepository.existsByEmail(dto.getEmail())) {
            result.rejectValue("email", "duplicated", "Email ya registrado");
        }

        // Si las contraseñas no coinciden, añade un error al formulario
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "mismatch", "Las contraseñas no coinciden");
        }

        // Si el userName ya está registrado, añade un error al formulario
        if (userRepository.existsByUserName(dto.getUserName())) {
            result.rejectValue("userName", "duplicated", "Username already taken");
        }

        // Si hay errores, añade el formulario de registro al modelo
        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirect.addFlashAttribute("user", dto);
            return "redirect:/login?tab=register";
        }

        // Crea el usuario y lo guarda en la base de datos
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setUserName(dto.getUserName());
        userRepository.save(user);

        // Carga los detalles del usuario
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        // Crea el contexto de autenticación con los detalles del usuario
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        // Establece la autenticación en el contexto de autenticación
        context.setAuthentication(auth);

        // Guarda el contexto de autenticación en el repositorio de contexto
        securityContextRepository.saveContext(context, request, response);

        return "redirect:/";
    }
}
