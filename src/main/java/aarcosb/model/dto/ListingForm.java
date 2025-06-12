package aarcosb.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import org.hibernate.validator.constraints.URL;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListingForm {
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 100, message = "1 to 100 characters are required")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 30, max = 500, message = "30 to 500 characters are required")
    private String description;

    @NotBlank(message = "Official URL is required")
    @Size(min = 1, max = 50, message = "1 to 50 characters are required")
    @URL(message = "Invalid URL format")
    private String officialUrl;

    @Size(min = 3, max = 10, message = "You must upload between 3 and 10 images")
    private List<MultipartFile> images;

    private MultipartFile video;

    @Pattern(regexp = "^$|^\\S+(,\\s*\\S+)*$", message = "Tags must be non-empty and separated by commas")
    private String tags;
} 