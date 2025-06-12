package aarcosb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import java.io.IOException;
import aarcosb.model.dto.CloudinaryResponse;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import aarcosb.model.repository.ListingRepository;
import aarcosb.model.entity.Listing;
import java.util.List;
import aarcosb.util.FileUploadUtil;
import aarcosb.model.entity.User;
import aarcosb.model.repository.UserRepository;

@Service
public class CloudinaryService {
    @Autowired private Cloudinary cloudinary;
    @Autowired private ListingRepository listingRepository;
    @Autowired private UserRepository userRepository;

    // Método privado para subir un archivo (imagen o video) a Cloudinary
    // Determina automáticamente si es imagen o video basado en el tipo de contenido
    // Genera un nombre de archivo único en la carpeta correspondiente
    // Retorna la URL pública y el ID del recurso en Cloudinary
    private CloudinaryResponse upload(MultipartFile file, String fileName) {
        try {
            String contentType = file.getContentType();
            String resourceType = (contentType != null && contentType.startsWith("image/")) ? "image" : "video";
            
            Map<String, String> params = Map.of(
                "public_id", "listings/" + resourceType + "/" + fileName,
                "resource_type", resourceType
            );

            @SuppressWarnings("unchecked")
            final Map<String, Object> result = cloudinary.uploader().upload(file.getBytes(), params);
            
            final String url = (String) result.get("secure_url");
            final String publicId = (String) result.get("public_id");
            
            return CloudinaryResponse.builder().publicId(publicId).url(url).build();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file bytes: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during upload: " + e.getMessage(), e);
        }
    }

    // Sube múltiples imágenes para un listing
    // Realiza validaciones:
    // - Cantidad: mínimo 3, máximo 10 imágenes
    // - Formato: solo JPG, PNG y JPEG permitidos
    // Si hay imágenes existentes, las elimina antes de subir las nuevas
    // Todas las imágenes se suben en una única transacción
    @Transactional
    public void uploadImages(final Long listingId, final List<MultipartFile> images) {
        if (images == null || images.isEmpty() || images.get(0).isEmpty()) {
            return;
        }

        // Validación de cantidad de imágenes
        if (images.size() < 3 || images.size() > 10) {
            throw new IllegalArgumentException("You must upload between 3 and 10 images");
        }

        // Validación de formato de cada imagen
        for (MultipartFile image : images) {
            FileUploadUtil.assertImageAllowed(image);
        }

        final Listing listing = listingRepository.findById(listingId)
            .orElseThrow(() -> new RuntimeException("Listing not found"));
        
        // Limpia las imágenes existentes y sube las nuevas
        listing.getImages().clear();
        for (MultipartFile image : images) {
            final String fileName = FileUploadUtil.getFileName(image.getOriginalFilename());
            final CloudinaryResponse response = upload(image, fileName);
            listing.getImages().add(response.getUrl());
        }

        listingRepository.save(listing);
    }

    // Sube un video para un listing
    // Realiza validaciones de formato: solo MP4, MOV, AVI, etc. permitidos
    // Si ya existe un video, lo reemplaza con el nuevo
    // La subida se realiza en una única transacción
    @Transactional
    public void uploadVideo(final Long listingId, final MultipartFile video) {
        if (video == null || video.isEmpty()) {
            return;
        }

        FileUploadUtil.assertVideoAllowed(video);
        
        final Listing listing = listingRepository.findById(listingId)
            .orElseThrow(() -> new RuntimeException("Listing not found"));
        
        final String fileName = FileUploadUtil.getFileName(video.getOriginalFilename());
        final CloudinaryResponse response = upload(video, fileName);
        listing.setVideo(response.getUrl());

        listingRepository.save(listing);
    }

    // Sube una imagen de perfil de usuario
    // Realiza validaciones de formato: solo JPG, PNG y JPEG permitidos
    // Retorna la URL pública del recurso en Cloudinary
    @Transactional
    public void uploadUserProfilePic(final User user, final MultipartFile image) {
        if (image == null || image.isEmpty()) {
            return;
        }
        
        FileUploadUtil.assertImageAllowed(image);

        final String fileName = FileUploadUtil.getFileName(image.getOriginalFilename());
        final CloudinaryResponse response = upload(image, fileName);
        user.setProfilePic(response.getUrl());
        
        userRepository.save(user);
    }
} 