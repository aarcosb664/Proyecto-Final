package aarcosb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController {
    // Devuelve la vista 'error' cuando ocurre un error en la aplicación
    @GetMapping("/error")
    public String error() 
    {
        return "error";
    }
}
