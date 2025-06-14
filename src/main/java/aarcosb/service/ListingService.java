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
import java.time.LocalDate;

@Service
public class ListingService {

    @Autowired private ListingRepository listingRepository;

    // Obtiene una página de listings con sus imágenes
    // La paginación se maneja a nivel de base de datos para eficiencia
    // Las imágenes se cargan después para cada listing
    public Page<Listing> getAllListings(Pageable pageable) {
        Page<Listing> listings = listingRepository.findAll(pageable);
        listings.forEach(listing -> listing.setImages(listingRepository.findImagesByListingId(listing.getId())));
        return listings;
    }

    // Obtiene un listing específico por su ID
    // Incluye la carga de sus imágenes asociadas
    // Retorna null si no existe el listing
    public Listing getListingById(Long id) {
        return listingRepository.findByIdWithImages(id);
    }

    // Obtiene todos los listings de un usuario específico
    // Incluye la carga de imágenes para cada listing
    // Útil para mostrar el perfil del usuario
    public List<Listing> getListingsByUserId(Long userId) {
        List<Listing> listings = listingRepository.findByUserId(userId);
        listings.forEach(listing -> listing.setImages(listingRepository.findImagesByListingId(listing.getId())));
        return listings;
    }

    // Convierte un string de tags separados por comas a un Set
    // Ejemplo: "tag1, tag2, tag3" -> Set("tag1", "tag2", "tag3")
    // Retorna null si no hay tags o el string está vacío
    public Set<String> convertTags(String tags) {
        if (tags == null || tags.trim().isEmpty()) {
            return Set.of();
        }
        return Arrays.stream(tags.split(","))
                    .map(String::trim)
                    .filter(tag -> !tag.isEmpty())
                    .collect(Collectors.toSet());
    }

    // Actualiza un listing existente
    // Mantiene el ID y fecha de creación originales
    // Actualiza la fecha de modificación automáticamente
    public Listing updateListing(Listing listing) {
        Listing savedListing = listingRepository.save(listing);
        savedListing.setImages(listingRepository.findImagesByListingId(savedListing.getId()));
        return savedListing;
    }

    // Elimina un listing y todos sus datos asociados
    // La eliminación en cascada se maneja a nivel de base de datos
    // Incluye imágenes, video, comentarios y valoraciones
    public void deleteListing(Long id) {
        listingRepository.deleteById(id);
    }

    // Cuenta el número total de usuarios que han marcado como favorito este listing
    // Útil para mostrar la popularidad del listing
    public int countTotalFavorites(Long listingId) {
        return listingRepository.countTotalFavorites(listingId);
    }

    // Obtiene todas las URLs de imágenes asociadas a un listing
    // Usado internamente para cargar las imágenes de un listing
    public List<String> getImagesByListingId(Long listingId) {
        return listingRepository.findImagesByListingId(listingId);
    }

    // Busca y filtra listings según los criterios proporcionados
    // Puede buscar por título, tags, valoración, fecha de creación o actualización
    // También permite filtrar por rango de valoración y fechas
    // Retorna una página de listings que coincidan con los criterios   
    public Page<Listing> searchAndFilter(String query, Double minRating, Double maxRating, LocalDate dateFrom, LocalDate dateTo, Pageable pageable) {
        // Si query es null o solo espacios, pásalo como null al repo (NO devuelvas vacío)
        String q = (query == null || query.trim().isEmpty()) ? null : query.trim().toLowerCase();
        Page<Listing> listings = listingRepository.searchAndFilter(q, minRating, maxRating, dateFrom, dateTo, pageable);
        listings.forEach(listing -> listing.setImages(listingRepository.findImagesByListingId(listing.getId())));
        return listings;
    }
} 