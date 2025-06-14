package aarcosb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import aarcosb.model.repository.UserRepository;
import aarcosb.model.entity.User;
import aarcosb.service.EmailSenderService;

@Controller
public class AboutController {

    @Autowired private UserRepository userRepository;
    @Autowired private EmailSenderService emailSenderService;

    private User getCurrentUser(Authentication authentication) 
    {
        return userRepository.findByEmail(authentication.getName());
    }

    @GetMapping("/about")
    public String about(Model model, Authentication authentication) 
    {
        User currentUser = getCurrentUser(authentication);
        model.addAttribute("currentUser", currentUser);

        return "about";
    }

    @PostMapping("/send-email")
    public String sendEmail(@RequestParam String subject, @RequestParam String body, Authentication authentication) 
    {
        User currentUser = getCurrentUser(authentication);
        emailSenderService.sendEmail(currentUser.getEmail(), subject, body);

        return "redirect:/about";
    }
}
