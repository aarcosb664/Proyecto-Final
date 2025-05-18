package aarcosb.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotEmpty;
import aarcosb.model.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    // Id
    private Long id;

    // Name
    @NotEmpty(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String name;

    // Last Name
    @NotEmpty(message = "Los apellidos son obligatorios")   
    @Size(min = 2, max = 50, message = "Los apellidos deben tener entre 2 y 50 caracteres")
    private String lastName;

    // Email
    @NotEmpty(message = "El correo electrónico es obligatorio")
    @Email(message = "Formato de correo inválido")
    private String email;

    // Password
    @NotEmpty(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "Mínimo 8 caracteres")
    private String password;
    
    // Confirm password
    @NotEmpty(message = "Confirme su contraseña")
    @Size(min = 8, message = "Mínimo 8 caracteres")
    private String confirmPassword;

    // Role
    private Role role;
}
