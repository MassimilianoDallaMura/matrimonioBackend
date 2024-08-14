package matrimonio.backend.DTO;

import lombok.Data;
import matrimonio.backend.enums.Categoria;

import java.util.List;

@Data
public class RaccomandazioneDTO {
    private Long id;
    private String titolo;
    private String descrizione;
    private Categoria categoria;
    private List<String> images; // Mantieni come List<String> per i percorsi delle immagini
}

