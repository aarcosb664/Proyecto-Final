package aarcosb;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class FinalApplication extends SpringBootServletInitializer {

    // Método principal que inicia la aplicación
    public static void main(String[] args) {
        SpringApplication.run(FinalApplication.class, args);
    }

    // Este método se usa al arrancar como WAR en un contenedor externo
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(FinalApplication.class);
    }
} 