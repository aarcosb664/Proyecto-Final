package aarcosb.service;

import aarcosb.model.entity.Role;
import aarcosb.model.entity.User;
import aarcosb.model.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.lang.reflect.Field;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {
    @Mock private UserRepository userRepository;
    private UserDetailsServiceImpl service;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        service = new UserDetailsServiceImpl();
        Field userRepoField = UserDetailsServiceImpl.class.getDeclaredField("userRepository");
        userRepoField.setAccessible(true);
        userRepoField.set(service, userRepository);
    }

    @Test
    void loadUserByUsername_returnsUserDetails_whenUserExists() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("pass");
        user.setRole(Role.USER);
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        UserDetails details = service.loadUserByUsername("test@example.com");
        assertEquals("test@example.com", details.getUsername());
        assertEquals("pass", details.getPassword());
        assertTrue(details.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void loadUserByUsername_throwsException_whenUserNotFound() {
        when(userRepository.findByEmail("no@no.com")).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("no@no.com"));
    }
} 