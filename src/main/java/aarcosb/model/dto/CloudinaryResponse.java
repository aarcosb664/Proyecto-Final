package aarcosb.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CloudinaryResponse {
    // ID público del archivo
    private String publicId;

    // URL del archivo
    private String url;
}
