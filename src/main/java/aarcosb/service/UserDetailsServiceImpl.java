package aarcosb.service;

import java.util.ArrayList;
import aarcosb.model.entity.Listing;
import aarcosb.model.entity.Player;
import aarcosb.model.entity.User;
import aarcosb.model.repository.ListingRepository;
import aarcosb.model.repository.PlayerRepository;
import aarcosb.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // Autowired pone aquí automáticamente los objetos que necesita la clase
    @Autowired private UserRepository userRepository;
    @Autowired private ListingRepository listingRepository;
    @Autowired private PlayerRepository playerRepository;

    // Carga un usuario por su email
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        
        // Crea una lista de autoridades
        List<GrantedAuthority> authorities = new ArrayList<>();
        // Añade la autoridad del rol del usuario
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        // Crea un usuario con sus autoridades
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            authorities
        );
    }

    // Elimina un usuario
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    // Obtiene los listings de un usuario
    public List<Listing> getListings(Long userId) {
        return listingRepository.findByUserId(userId);
    }

    // Obtiene los listings favoritos de un usuario
    public List<Listing> getFavorites(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        // Si el usuario no tiene listings favoritos, retorna una lista vacía
        if (user.getFavListings() == null || user.getFavListings().isEmpty()) {
            return new ArrayList<>();
        }

        // Obtiene los listings favoritos donde el ID del listing está en la lista de favoritos del usuario
        return listingRepository.findByIdIn(user.getFavListings());
    }

    // Obtiene el número total de listings de un usuario
    public int countTotalListings(Long userId) {
        return getListings(userId).size();
    }

    // Obtiene el número total de listings favoritos de un usuario
    public int countTotalFavorites(Long userId) {
        List<Listing> favorites = getFavorites(userId);
        return favorites != null ? favorites.size() : 0;
    }

    // Obtiene los juegos de un usuario
    public List<Player> getGames(Long userId) {
        return playerRepository.findByUserId(userId);
    }
} 