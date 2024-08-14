package matrimonio.backend.entity;

import lombok.Data;
import jakarta.persistence.*;
import matrimonio.backend.enums.Dieta;

import java.util.List;

@Entity
@Data
public class PlusOne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @ElementCollection
    private List<String> allergies;

    @Enumerated(EnumType.STRING)
    private Dieta dieta;
}
