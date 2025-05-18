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
import aarcosb.model.entity.Role;
import aarcosb.model.entity.User;
import aarcosb.model.repository.UserRepository;
import java.security.Principal;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
public class LoginController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    /**
     * Muestra la página de login
     */
    @GetMapping("/login")
    public String login(Model model, Principal principal) {
        // Si el usuario ya está autenticado, redirigir a la página principal
        if (principal != null) {
            return "redirect:/";
        }
        
        // Establecer valor predeterminado para activeTab (pestaña de login)
        model.addAttribute("activeTab", null);
        
        // Inicializar el modelo para el formulario de registro
        if (!model.containsAttribute("registerUser")) {
            model.addAttribute("registerUser", new UserDto());
        }
        return "login";
    }
    
    /**
     * Procesa el registro de usuarios
     */
    @PostMapping("/register")
    public String registro(
            @Valid @ModelAttribute("registerUser") UserDto registerUserDto,
            BindingResult result,
            Model model) {
        if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error", "Las contraseñas no coinciden");
        }
        if (userRepository.findByEmail(registerUserDto.getEmail()) != null) {
            result.rejectValue("email", "error", "Email ya registrado");
        }
        
        if (result.hasErrors()) {
            // Establecer la pestaña de registro como activa cuando hay errores
            model.addAttribute("activeTab", "#register");
            return "login";
        }
        User usuario = new User();
        usuario.setEmail(registerUserDto.getEmail());
        usuario.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        usuario.setName(registerUserDto.getName());
        usuario.setLastName(registerUserDto.getLastName());
        usuario.setRole(Role.USER);
        usuario.setCreatedAt(new java.util.Date());
        userRepository.save(usuario);
        
        // Autenticar automáticamente al usuario después del registro
        try {
            // Cargar el usuario recién creado con sus autoridades
            UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());
            Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
                
            // Establecer la autenticación en el SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(auth);
            
            // Redirigir a la página principal ya que el usuario está autenticado
            return "redirect:/";
        } catch (Exception e) {
            // Si hay algún error en la autenticación, mostrar mensaje de éxito y redireccionar al login
            model.addAttribute("success", "Usuario registrado correctamente. Por favor, inicie sesión.");
            model.addAttribute("registerUser", new UserDto());
            model.addAttribute("activeTab", null);
            return "login";
        }
    }
}
