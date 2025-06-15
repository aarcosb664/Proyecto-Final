package aarcosb.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    // ID del usuario
    private Long id;

    // Nombre de usuario
    @NotBlank(message = "Username is required")
    @Size(min = 2, max = 15, message = "Username must be between 2 and 15 characters")
    private String userName;

    // Email del usuario
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;

    // Contraseña del usuario
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 16, message = "La contraseña debe tener entre 8 y 16 caracteres")
    private String password;

    // Confirmación de contraseña del usuario
    @NotBlank(message = "La confirmación de contraseña es obligatoria")
    @Size(min = 8, max = 16, message = "La confirmación de contraseña debe tener entre 8 y 16 caracteres")
    private String confirmPassword;
} 