package matrimonio.backend.controller;

import matrimonio.backend.DTO.InvitiatiDTO;
import matrimonio.backend.DTO.PlusOneDTO;
import matrimonio.backend.entity.Invitati;
import matrimonio.backend.service.InvitatiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/invitati")
public class InvitatiController {

    @Autowired
    private InvitatiService invitatiService;

    @PostMapping("/saveBasic")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'NORMAL_USER')")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Invitati> saveBasicInvitato(@RequestBody InvitiatiDTO invitatiDTO) {
        Invitati savedInvitato = invitatiService.saveBasicInvitato(invitatiDTO);
        return ResponseEntity.ok(savedInvitato);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'NORMAL_USER')")
//    @PreAuthorize("@securityService.isCurrentUserOrAdmin(#id)")
    public ResponseEntity<Invitati> updateInvitatoProperties(@PathVariable Long id, @RequestBody InvitiatiDTO invitatiDTO) {
        Invitati updatedInvitato = invitatiService.updateInvitatoProperties(id, invitatiDTO);
        return ResponseEntity.ok(updatedInvitato);
    }

    @GetMapping("/all")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'NORMAL_USER')")
    public ResponseEntity<List<Invitati>> getAllInvitati() {
        List<Invitati> invitatiList = invitatiService.getAllInvitati();
        return ResponseEntity.ok(invitatiList);
    }

    @GetMapping("/find")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'NORMAL_USER')")
    public ResponseEntity<Invitati> getInvitatoByNameAndSurname(@RequestParam String firstName, @RequestParam String lastName) {
        Optional<Invitati> invitato = invitatiService.getInvitatoByNameAndSurname(firstName, lastName);
        return invitato.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'NORMAL_USER')")
    public ResponseEntity<String> deleteInvitato(@PathVariable Long id) {
        try {
            invitatiService.deleteInvitato(id);
            return ResponseEntity.ok("Invitato con ID " + id + " eliminato correttamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/find/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'NORMAL_USER')")
    public ResponseEntity<Invitati> getInvitatoById(@PathVariable Long id) {
        Optional<Invitati> invitato = invitatiService.getInvitatoById(Math.toIntExact(id));
        return invitato.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
