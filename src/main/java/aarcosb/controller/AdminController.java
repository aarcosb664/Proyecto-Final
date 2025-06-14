package aarcosb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import aarcosb.model.entity.User;
import aarcosb.model.repository.UserRepository;
import aarcosb.service.UserDetailsServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private UserRepository userRepository;
    @Autowired private UserDetailsServiceImpl userDetailsServiceImpl;

    private User getCurrentUser(Authentication authentication) 
    {
        return userRepository.findByEmail(authentication.getName());
    }

    @GetMapping("/panel")
    public String admin(Model model, Authentication authentication) 
    {
        User currentUser = getCurrentUser(authentication);
        model.addAttribute("currentUser", currentUser);

        model.addAttribute("users", userRepository.findAll());

        return "admin/panel";
    }

    @PostMapping("/user/{userId}/delete")
    public String deleteUser(@PathVariable Long userId) 
    {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        userDetailsServiceImpl.deleteUser(userId);
        return "redirect:/admin/panel";
    }
}
