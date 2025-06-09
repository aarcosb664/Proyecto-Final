package aarcosb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import aarcosb.model.entity.Listing;
import aarcosb.model.repository.ListingRepository;
import java.util.List;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.Date;

@Service
public class ListingService {

    @Autowired
    private ListingRepository listingRepository;

    public Page<Listing> getAllListings(Pageable pageable) {
        return listingRepository.findAll(pageable);
    }

    public Listing getListingById(Long id) {
        return listingRepository.findById(id).orElse(null);
    }

    public List<Listing> getListingsByUserId(Long userId) {
        return listingRepository.findByUserId(userId);
    }

    public Listing createListing(Listing listing) {
        return listingRepository.save(listing);
    }

    public Listing updateListing(Listing listing) {
        return listingRepository.save(listing);
    }

    public void deleteListing(Long id) {
        listingRepository.deleteById(id);
    }

    public Set<String> convertTags(String tags) {
        // Si el usuario no ha introducido ningun tag, se devuelve null
        if (tags == null || tags.isEmpty()) {
            return null;
        }

        // Se comprueba que el formato de los tags es correcto
        String regexp = "^\\S+(,\\s*\\S+){1,100}$";
        if (!tags.matches(regexp)) {
            throw new IllegalArgumentException("The tags must have a length between 1 and 100, and separated by commas");
        }

        // Si el usuario ha introducido un tag con una coma al final, se elimina
        if (tags.endsWith(",")) {
            tags = tags.substring(0, tags.length() - 1);
        }

        /*
         * 1. Se convierte el string en un array de strings, 
         * 2. Se eliminan los espacios en blanco de cada tag,
         * 3. Se convierte de nuevo a un set
         */
        return Arrays.asList(tags.split(",")).stream()
                     .map(String::trim)
                     .collect(Collectors.toSet());
    }

    public Page<Listing> searchAndFilter(String query, Double minRating, Double maxRating, Date dateFrom, Date dateTo, Pageable pageable) {
        // Si query es null o solo espacios, pásalo como null al repo (NO devuelvas vacío)
        String q = (query == null || query.trim().isEmpty()) ? null : query.trim().toLowerCase();
        return listingRepository.searchAndFilter(q, minRating, maxRating, dateFrom, dateTo, pageable);
    }
} 