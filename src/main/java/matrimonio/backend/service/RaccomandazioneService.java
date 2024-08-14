package matrimonio.backend.service;

import jakarta.transaction.Transactional;
import matrimonio.backend.DTO.RaccomandazioneDTO;
import matrimonio.backend.entity.Raccomandazione;
import matrimonio.backend.enums.Categoria;
import matrimonio.backend.repository.RaccomandazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RaccomandazioneService {

    @Autowired
    private RaccomandazioneRepository raccomandazioneRepository;

    private final Path rootLocation = Paths.get("uploads");

    @Transactional
    public Raccomandazione salvaRaccomandazione(RaccomandazioneDTO raccomandazioneDTO) {
        Raccomandazione raccomandazione = new Raccomandazione();
        raccomandazione.setTitolo(raccomandazioneDTO.getTitolo());
        raccomandazione.setDescrizione(raccomandazioneDTO.getDescrizione());
        raccomandazione.setCategoria(raccomandazioneDTO.getCategoria());
        raccomandazione.setImages(raccomandazioneDTO.getImages()); // Usa direttamente i percorsi salvati

        return raccomandazioneRepository.save(raccomandazione);
    }

    public List<Raccomandazione> getAllRaccomandazioni() {
        return raccomandazioneRepository.findAll();
    }

    public Optional<Raccomandazione> getRaccomandazioneById(Long id) {
        return raccomandazioneRepository.findById(id);
    }

    @Transactional
    public void deleteRaccomandazione(Long id) {
        Raccomandazione raccomandazione = raccomandazioneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Raccomandazione non trovata con ID: " + id));
        raccomandazioneRepository.delete(raccomandazione);
    }

    public String saveImage(MultipartFile file) {
        try {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path destinationFile = this.rootLocation.resolve(Paths.get(filename))
                    .normalize().toAbsolutePath();

            Files.createDirectories(destinationFile.getParent());

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            return destinationFile.toString();
        } catch (IOException e) {
            throw new RuntimeException("Impossibile salvare l'immagine: " + e.getMessage());
        }
    }
}

