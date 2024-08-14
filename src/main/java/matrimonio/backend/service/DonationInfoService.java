package matrimonio.backend.service;
import matrimonio.backend.DTO.DonationInfoDTO;
import matrimonio.backend.entity.DonationInfo;
import matrimonio.backend.repository.DonationInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonationInfoService {

    @Autowired
    private DonationInfoRepository donationInfoRepository;

    // Metodo per salvare le informazioni di donazione
    public DonationInfoDTO saveDonationInfo(DonationInfoDTO donationInfoDTO) {
        // Crea una nuova istanza e imposta il contenuto
        DonationInfo donationInfo = new DonationInfo();
        donationInfo.setContent(donationInfoDTO.getContent());

        // Salva nel database
        donationInfo = donationInfoRepository.save(donationInfo);

        // Converte l'entità salvata in DTO
        DonationInfoDTO savedDonationInfoDTO = new DonationInfoDTO();
        savedDonationInfoDTO.setId(donationInfo.getId());
        savedDonationInfoDTO.setContent(donationInfo.getContent());

        return savedDonationInfoDTO;
    }

    // Metodo per ottenere l'informazione di donazione corrente
    public DonationInfoDTO getDonationInfo() {
        DonationInfo donationInfo = donationInfoRepository.findFirstByOrderById();

        // Se non esiste nessuna informazione di donazione, gestisci il caso
        if (donationInfo == null) {
            return null; // O restituisci un DTO con valori predefiniti
        }

        // Converte l'entità in DTO
        DonationInfoDTO donationInfoDTO = new DonationInfoDTO();
        donationInfoDTO.setId(donationInfo.getId());
        donationInfoDTO.setContent(donationInfo.getContent());

        return donationInfoDTO;
    }
}
