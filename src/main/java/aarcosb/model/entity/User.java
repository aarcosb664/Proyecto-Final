package aarcosb.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    // ID del usuario
    @Id
    // Genera el ID del usuario de forma automática
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre de usuario
    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    // Email del usuario
    @Column(name = "email", unique = true)
    @Email
    private String email;

    // Contraseña del usuario
    @Column(name = "password")
    private String password;

    // Imagen de perfil del usuario
    @Column(name = "profile_pic")
    private String profilePic;

    // Listado de listings favoritos del usuario
    @ElementCollection 
    @Column(name = "fav_listings")
    private List<Long> favListings;

    // Rol del usuario
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    
    // Fecha de creación del usuario
    @Column(name = "created_at")
    private Date createdAt;

    // Método que se ejecuta antes de que se persista el usuario
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        profilePic = "/img/default.png";
        favListings = new ArrayList<>();
        role = Role.USER;
    }
}
