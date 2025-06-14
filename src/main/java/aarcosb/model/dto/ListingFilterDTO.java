package aarcosb.model.dto;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListingFilterDTO {
    private Integer page = 1;
    private Integer size = 9;
    private String sort = "updatedAt";
    private String order = "desc";
    private String query = "";
    private Double minRating;
    private Double maxRating;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTo;
}