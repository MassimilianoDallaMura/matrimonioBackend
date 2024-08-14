package matrimonio.backend.DTO;

import lombok.Data;
import matrimonio.backend.enums.Dieta;

import java.util.List;

@Data
public class PlusOneDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private List<String> allergies;
    private Dieta dieta;
}
