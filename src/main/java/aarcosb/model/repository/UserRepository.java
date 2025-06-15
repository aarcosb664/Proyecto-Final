package aarcosb.model.repository;

import aarcosb.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Busca un usuario por su email
    User findByEmail(String email);

    // Verifica si existe un usuario con un email
    boolean existsByEmail(String email);

    // Verifica si existe un usuario con un nombre de usuario
    boolean existsByUserName(String userName);
}
