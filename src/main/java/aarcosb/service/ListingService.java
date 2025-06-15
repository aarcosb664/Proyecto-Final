package aarcosb.service;

import aarcosb.model.entity.Listing;
import aarcosb.model.repository.ListingRepository;
import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListingService {

    // Autowired pone aquí automáticamente los objetos que necesita la clase
    @Autowired private ListingRepository listingRepository;

    // Obtiene todos los listings
    public Page<Listing> getAllListings(Pageable pageable) {
        return listingRepository.findAll(pageable);
    }

    // Obtiene un listing específico por su ID
    public Listing getListingById(Long id) {
        return listingRepository.findById(id).orElse(null);
    }

    // Obtiene todos los listings de un usuario específico
    public List<Listing> getListingsByUserId(Long userId) {
        return listingRepository.findByUserId(userId);
    }

    // Convierte un string de tags separados por comas a un Set
    public Set<String> convertTags(String tags) {
        // Si no hay tags o el string está vacío, retorna un Set vacío
        if (tags == null || tags.trim().isEmpty()) {
            return Set.of();
        }
        // Convierte el string de tags separados por comas a un Set
        return Arrays.stream(tags.split(","))
                    .map(String::trim)
                    .filter(tag -> !tag.isEmpty())
                    .collect(Collectors.toSet());
    }

    // Actualiza un listing existente
    public Listing updateListing(Listing listing) {
        return listingRepository.save(listing);
    }

    // Elimina un listing y todos sus datos asociados
    public void deleteListing(Long id) {
        listingRepository.deleteById(id);
    }

    // Cuenta el número total de usuarios que han marcado como favorito este listing
    public int countTotalFavorites(Long listingId) {
        return listingRepository.countTotalFavorites(listingId);
    }

    // Busca y filtra listings según los criterios proporcionados
    public Page<Listing> searchAndFilter(String query, Double minRating, Double maxRating, Date dateFrom, Date dateTo, Pageable pageable) {
        // Si query es null o solo espacios, pásalo como null al repo
        String q = (query != null && !query.trim().isEmpty()) ? query.trim().toLowerCase() : null;

        // Obtiene una página de listings que coincidan con los criterios
        return listingRepository.searchAndFilter(q, minRating, maxRating, dateFrom, dateTo, pageable);
    }
} 