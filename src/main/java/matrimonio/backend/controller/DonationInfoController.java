package matrimonio.backend.controller;

import matrimonio.backend.DTO.DonationInfoDTO;
import matrimonio.backend.service.DonationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donation-info")
public class DonationInfoController {

    @Autowired
    private DonationInfoService donationInfoService;

    @PostMapping("/salva")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DonationInfoDTO> saveDonationInfo(@RequestBody DonationInfoDTO donationInfoDTO) {
        DonationInfoDTO savedDonationInfo = donationInfoService.saveDonationInfo(donationInfoDTO);
        return ResponseEntity.ok(savedDonationInfo);
    }

    @GetMapping("/mostra")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'NORMAL_USER')")
    public ResponseEntity<DonationInfoDTO> getDonationInfo() {
        DonationInfoDTO donationInfoDTO = donationInfoService.getDonationInfo();

        // Gestisci il caso in cui non ci siano dati
        if (donationInfoDTO == null) {
            return ResponseEntity.noContent().build(); // Restituisce 204 No Content
        }

        return ResponseEntity.ok(donationInfoDTO);
    }
}
