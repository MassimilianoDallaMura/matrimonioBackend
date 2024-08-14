package matrimonio.backend.controller;

import matrimonio.backend.DTO.RaccomandazioneDTO;
import matrimonio.backend.entity.Raccomandazione;
import matrimonio.backend.enums.Categoria;
import matrimonio.backend.service.RaccomandazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/raccomandazioni")
public class RaccomandazioneController {

    @Autowired
    private RaccomandazioneService raccomandazioneService;

    @PostMapping("/salva")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'NORMAL_USER')")
    public ResponseEntity<Raccomandazione> salvaRaccomandazione(
            @RequestParam("titolo") String titolo,
            @RequestParam("descrizione") String descrizione,
            @RequestParam("categoria") Categoria categoria,
            @RequestParam("images") List<MultipartFile> images) {

        // Verifica se 'images' è presente e non è vuota
        if (images == null || images.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        RaccomandazioneDTO raccomandazioneDTO = new RaccomandazioneDTO();
        raccomandazioneDTO.setTitolo(titolo);
        raccomandazioneDTO.setDescrizione(descrizione);
        raccomandazioneDTO.setCategoria(categoria);

        // Salvataggio delle immagini e ottenimento dei percorsi
        List<String> savedImages = images.stream()
                .map(raccomandazioneService::saveImage)
                .collect(Collectors.toList());

        raccomandazioneDTO.setImages(savedImages);

        Raccomandazione raccomandazioneSalvata = raccomandazioneService.salvaRaccomandazione(raccomandazioneDTO);
        return ResponseEntity.ok(raccomandazioneSalvata);
    }


    @GetMapping("/tutte")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'NORMAL_USER')")
    public ResponseEntity<List<Raccomandazione>> getAllRaccomandazioni() {
        List<Raccomandazione> raccomandazioni = raccomandazioneService.getAllRaccomandazioni();
        return ResponseEntity.ok(raccomandazioni);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'NORMAL_USER')")
    public ResponseEntity<Raccomandazione> getRaccomandazioneById(@PathVariable Long id) {
        Optional<Raccomandazione> raccomandazione = raccomandazioneService.getRaccomandazioneById(id);
        return raccomandazione.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/elimina/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'NORMAL_USER')")
    public ResponseEntity<Void> deleteRaccomandazione(@PathVariable Long id) {
        try {
            raccomandazioneService.deleteRaccomandazione(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
