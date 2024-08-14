package matrimonio.backend.DTO;

import lombok.Data;
import matrimonio.backend.enums.Dieta;
import matrimonio.backend.enums.Partecipazione;
import matrimonio.backend.enums.Ruolo;

import java.util.List;

@Data
public class InvitiatiDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password; // should be stored as a hash
    private List<String> allergies;
    private Partecipazione partecipazione;
    private boolean plusOneAllowed;
    private boolean plusOneConfirmed;
    private Ruolo ruolo;
    private PlusOneDTO plusOne;
    private Dieta dieta;
}