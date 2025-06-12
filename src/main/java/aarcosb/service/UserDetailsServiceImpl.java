package aarcosb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import aarcosb.model.entity.Listing;
import aarcosb.model.repository.ListingRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.ArrayList;
import java.util.List;

import aarcosb.model.entity.User;
import aarcosb.model.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired private UserRepository userRepository;
    @Autowired private ListingRepository listingRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            authorities
        );
    }

    public List<Listing> getListings(Long userId) {
        return listingRepository.findByUserId(userId);
    }

    public List<Listing> getFavorites(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user.getFavListings() == null || user.getFavListings().isEmpty()) {
            return new ArrayList<>();
        }
        return listingRepository.findByIdIn(user.getFavListings());
    }

    public int countTotalListings(Long userId) {
        return getListings(userId).size();
    }

    public int countTotalFavorites(Long userId) {
        List<Listing> favorites = getFavorites(userId);
        return favorites != null ? favorites.size() : 0;
    }
} 