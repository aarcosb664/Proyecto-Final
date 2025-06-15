package aarcosb.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListingForm {
    // Título del listing
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 100, message = "1 to 100 characters are required")
    private String title;

    // Descripción del listing
    @NotBlank(message = "Description is required")
    @Size(min = 30, max = 999, message = "30 to 999 characters are required")
    private String description;

    // URL oficial del listing
    @NotBlank(message = "Official URL is required")
    @Size(min = 1, max = 50, message = "1 to 50 characters are required")
    @URL(message = "Invalid URL format")
    private String officialUrl;

    // Imágenes del listing
    @Size(min = 3, max = 10, message = "You must upload between 3 and 10 images")
    private List<MultipartFile> images;

    // Vídeo del listing
    private MultipartFile video;

    // Etiquetas del listing
    @Pattern(regexp = "^$|^\\S+(,\\s*\\S+)*$", message = "Tags must be non-empty and separated by commas")
    private String tags;
} 