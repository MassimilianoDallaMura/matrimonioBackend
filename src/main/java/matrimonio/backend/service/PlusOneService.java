package matrimonio.backend.service;

import jakarta.transaction.Transactional;
import matrimonio.backend.DTO.PlusOneDTO;
import matrimonio.backend.entity.PlusOne;
import matrimonio.backend.repository.PlusOneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlusOneService {

    @Autowired
    private PlusOneRepository plusOneRepository;

    @Transactional
    public PlusOne savePlusOne(PlusOneDTO plusOneDTO) {
        PlusOne plusOne = new PlusOne();
        plusOne.setFirstName(plusOneDTO.getFirstName());
        plusOne.setLastName(plusOneDTO.getLastName());
        plusOne.setAllergies(plusOneDTO.getAllergies());
        plusOne.setDieta(plusOneDTO.getDieta());

        return plusOneRepository.save(plusOne);
    }

    public Optional<PlusOne> getPlusOneById(Long id) {
        return plusOneRepository.findById(id);
    }

    @Transactional
    public void deletePlusOne(Long id) {
        PlusOne plusOne = plusOneRepository.findById(id).orElseThrow(() -> new RuntimeException("PlusOne non trovato con ID: " + id));
        plusOneRepository.delete(plusOne);
    }
}
