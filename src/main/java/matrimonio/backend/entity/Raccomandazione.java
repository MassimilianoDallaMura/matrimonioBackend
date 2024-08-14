package matrimonio.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import matrimonio.backend.enums.Categoria;
import matrimonio.backend.enums.Dieta;

import java.util.List;

@Entity
@Table(name = "raccomandazioni")
@Data
public class Raccomandazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false)
    private String descrizione;


    @Enumerated(EnumType.STRING)
    public Categoria categoria;

    @ElementCollection
    @CollectionTable(name = "raccomandazione_images", joinColumns = @JoinColumn(name = "raccomandazione_id"))
    @Column(name = "image_url")
    private List<String> images;


}