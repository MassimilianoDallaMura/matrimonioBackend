package matrimonio.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Messaggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String testo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitati_id", nullable = false)
    private Invitati autore;

    @Column(nullable = false)
    private LocalDateTime timestamp;

}
