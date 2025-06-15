package aarcosb.model.dto;

import java.util.Date;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListingFilterDTO {
    // Página actual
    private Integer page = 1;

    // Número de elementos por página
    private Integer size = 9;

    // Campo por el que se ordenarán los resultados
    private String sort = "updatedAt";

    // Dirección de la ordenación
    private String order = "desc";

    // Término de búsqueda
    private String query = "";

    // Rango mínimo de valoración
    private Double minRating;

    // Rango máximo de valoración
    private Double maxRating;

    // Fecha de inicio del rango
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFrom;

    // Fecha de fin del rango
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateTo;
}