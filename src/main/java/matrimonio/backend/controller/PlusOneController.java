package matrimonio.backend.controller;

import matrimonio.backend.DTO.PlusOneDTO;
import matrimonio.backend.entity.PlusOne;
import matrimonio.backend.service.PlusOneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/plusone")
public class PlusOneController {

    @Autowired
    private PlusOneService plusOneService;

    @PostMapping("/save")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'NORMAL_USER')")
    public ResponseEntity<PlusOne> savePlusOne(@RequestBody PlusOneDTO plusOneDTO) {
        PlusOne savedPlusOne = plusOneService.savePlusOne(plusOneDTO);
        return ResponseEntity.ok(savedPlusOne);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'NORMAL_USER')")
    public ResponseEntity<PlusOne> getPlusOneById(@PathVariable Long id) {
        Optional<PlusOne> plusOne = plusOneService.getPlusOneById(id);
        return plusOne.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'NORMAL_USER')")
    public ResponseEntity<String> deletePlusOne(@PathVariable Long id) {
        try {
            plusOneService.deletePlusOne(id);
            return ResponseEntity.ok("PlusOne con ID " + id + " eliminato correttamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
