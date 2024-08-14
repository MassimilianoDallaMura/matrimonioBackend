package matrimonio.backend.controller;

import matrimonio.backend.DTO.MessaggioDTO;
import matrimonio.backend.entity.Messaggio;
import matrimonio.backend.service.MessaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messaggi")
public class MessaggioController {

    @Autowired
    private MessaggioService messaggioService;

    @PostMapping("/salva")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'NORMAL_USER')")
    public ResponseEntity<Messaggio> salvaMessaggio(@RequestBody MessaggioDTO messaggioDTO) {
        Messaggio messaggioSalvato = messaggioService.salvaMessaggio(messaggioDTO);
        return ResponseEntity.ok(messaggioSalvato);
    }

    @GetMapping("/tutti")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'NORMAL_USER')")
    public ResponseEntity<List<Messaggio>> getTuttiMessaggi() {
        List<Messaggio> messaggi = messaggioService.getTuttiMessaggi();
        return ResponseEntity.ok(messaggi);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'NORMAL_USER')")
    public ResponseEntity<Messaggio> getMessaggioById(@PathVariable Long id) {
        Optional<Messaggio> messaggio = messaggioService.getMessaggioById(id);
        return messaggio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
