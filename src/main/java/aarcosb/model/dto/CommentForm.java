package aarcosb.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentForm {
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 250, message = "The title must be between 1 and 250 characters")
    private String title;

    @NotBlank(message = "Text is required")
    @Size(min = 1, max = 250, message = "The comment must be between 1 and 250 characters")
    private String text;
} 