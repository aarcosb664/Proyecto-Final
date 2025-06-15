package aarcosb.service;

import aarcosb.model.dto.CloudinaryResponse;
import aarcosb.model.entity.Listing;
import aarcosb.model.entity.User;
import aarcosb.model.repository.ListingRepository;
import aarcosb.model.repository.UserRepository;
import aarcosb.util.FileUploadUtil;
import com.cloudinary.Cloudinary;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
public class CloudinaryService {

    // Autowired pone aquí automáticamente los objetos que necesita la clase
    @Autowired private Cloudinary cloudinary;
    @Autowired private ListingRepository listingRepository;
    @Autowired private UserRepository userRepository;

    // Sube un archivo (imagen o video) a Cloudinary
    private CloudinaryResponse upload(MultipartFile file, String fileName) throws Exception {
        // Intenta subir el archivo a Cloudinary
        try {
            // Obtiene el tipo de contenido del archivo
            String contentType = file.getContentType();
            // Determina si es imagen o video basado en el tipo de contenido
            String resourceType = (contentType != null && contentType.startsWith("image/")) ? "image" : "video";
            
            // Define los parámetros de subida
            Map<String, String> params = Map.of(
                "public_id", "listings/" + resourceType + "/" + fileName,
                "resource_type", resourceType
            );

            // Realiza la subida del archivo a Cloudinary
            @SuppressWarnings("unchecked")
            final Map<String, Object> result = cloudinary.uploader().upload(file.getBytes(), params);
            
            // Obtiene la URL pública del archivo
            final String url = (String) result.get("secure_url");
            // Obtiene el ID público del archivo
            final String publicId = (String) result.get("public_id");
            
            // Retorna la URL pública y el ID público del archivo
            return CloudinaryResponse.builder().publicId(publicId).url(url).build();
        } catch (IOException e) {
            // Si hay un error al leer los bytes del archivo, lanza una excepción
            throw new Exception("Failed to read file bytes: " + e.getMessage(), e);
        } catch (Exception e) {
            // Si hay un error al subir el archivo, lanza una excepción
            throw new Exception("Unexpected error during upload: " + e.getMessage(), e);
        }
    }

    // Sube múltiples imágenes para un listing
    // Transaccional hace que todas las imágenes se suban en una única transacción y si hay un error, se deshace la transacción
    @Transactional
    public void uploadImages(final Long listingId, final List<MultipartFile> images) throws Exception {
        // Si no hay imágenes, no se suben y lanza una excepción
        if (images == null || images.isEmpty() || images.get(0).isEmpty()) {
            throw new IllegalArgumentException("Images are required");
        }

        // Validación de cantidad de imágenes y lanza una excepción si no está entre 3 y 10
        if (images.size() < 3 || images.size() > 10) {
            throw new IllegalArgumentException("You must upload between 3 and 10 images");
        }

        // Validación de formato de cada imagen
        for (MultipartFile image : images) {
            FileUploadUtil.assertImageAllowed(image);
        }

        // Obtiene el listing y lanza una excepción si no existe
        final Listing listing = listingRepository.findById(listingId)
            .orElseThrow(() -> new RuntimeException("Listing not found"));
        
        // Limpia las imágenes existentes y sube las nuevas
        listing.getImages().clear();
        // Sube cada imagen
        for (MultipartFile image : images) {
            final String fileName = FileUploadUtil.getFileName(image.getOriginalFilename());
            final CloudinaryResponse response = upload(image, fileName);
            listing.getImages().add(response.getUrl());
        }

        listingRepository.save(listing);
    }

    // Sube un video para un listing
    @Transactional
    public void uploadVideo(final Long listingId, final MultipartFile video) throws Exception {
        // Si no hay video, no se sube y lanza una excepción
        if (video == null || video.isEmpty()) {
            throw new IllegalArgumentException("Video is required");
        }

        // Valida el formato del video
        FileUploadUtil.assertVideoAllowed(video);

        // Obtiene el listing y lanza una excepción si no existe
        final Listing listing = listingRepository.findById(listingId).orElseThrow(() -> new RuntimeException("Listing not found"));
        
        // Sube el video
        final String fileName = FileUploadUtil.getFileName(video.getOriginalFilename());
        final CloudinaryResponse response = upload(video, fileName);
        // Actualiza el video del listing
        listing.setVideo(response.getUrl());

        listingRepository.save(listing);
    }

    // Sube una imagen de perfil de usuario
    @Transactional
    public void uploadUserProfilePic(final User user, final MultipartFile image) throws Exception {
        // Si no hay imagen, no se sube y lanza una excepción
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Image is required");
        }
        
        // Valida el formato de la imagen
        FileUploadUtil.assertImageAllowed(image);

        // Obtiene el nombre del archivo
        final String fileName = FileUploadUtil.getFileName(image.getOriginalFilename());
        final CloudinaryResponse response = upload(image, fileName);
        // Actualiza la imagen de perfil del usuario
        user.setProfilePic(response.getUrl());

        userRepository.save(user);
    }
} 