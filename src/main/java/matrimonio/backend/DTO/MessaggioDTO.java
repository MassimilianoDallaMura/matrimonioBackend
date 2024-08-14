package matrimonio.backend.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessaggioDTO {

    private Long id;
    private String testo;
    private Long autoreId;
    private LocalDateTime timestamp;
}
