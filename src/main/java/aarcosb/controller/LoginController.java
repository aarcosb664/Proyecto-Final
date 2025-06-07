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
import org.springframework.security.web.context.SecurityContextRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private SecurityContextRepository securityContextRepository;
    
    /**
     * Muestra la página de login
     */
    @GetMapping("/login")
    public String login(@RequestParam(defaultValue = "login") String tab, Model model, Principal principal) {
        if (principal != null) return "redirect:/";
        model.addAttribute("user", model.containsAttribute("user") ? model.getAttribute("user") : new UserDto());
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

        if (userRepository.existsByEmail(dto.getEmail()))
            result.rejectValue("email", "duplicated", "Email ya registrado");
        if (!dto.getPassword().equals(dto.getConfirmPassword()))
            result.rejectValue("confirmPassword", "mismatch", "Las contraseñas no coinciden");

        if (result.hasErrors()) {
            redirect.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirect.addFlashAttribute("user", dto);
            return "redirect:/login?tab=register";
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setName(dto.getName());
        user.setLastName(dto.getLastName());
        user.setRole(Role.USER);
        userRepository.save(user);

        UserDetails ud = userDetailsService.loadUserByUsername(user.getEmail());
        Authentication auth = new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        securityContextRepository.saveContext(context, request, response);

        return "redirect:/";
    }
}
