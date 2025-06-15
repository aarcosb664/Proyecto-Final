package aarcosb.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentForm {
    // Título del comentario
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 250, message = "The title must be between 1 and 250 characters")
    private String title;

    // Texto del comentario
    @NotBlank(message = "Text is required")
    @Size(min = 1, max = 250, message = "The comment must be between 1 and 250 characters")
    private String text;
} 