package matrimonio.backend.service;

import jakarta.transaction.Transactional;
import matrimonio.backend.DTO.InvitiatiDTO;
import matrimonio.backend.entity.Invitati;
import matrimonio.backend.entity.PlusOne;
import matrimonio.backend.enums.Partecipazione;
import matrimonio.backend.enums.Ruolo;
import matrimonio.backend.repository.InvitatiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvitatiService {

    @Autowired
    private InvitatiRepository invitatiRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Invitati saveBasicInvitato(InvitiatiDTO invitatiDTO) {
        Invitati invitato = new Invitati();
        invitato.setFirstName(invitatiDTO.getFirstName());
        invitato.setLastName(invitatiDTO.getLastName());
        invitato.setUsername(invitatiDTO.getUsername());
        invitato.setPassword(passwordEncoder.encode(invitatiDTO.getPassword())); // Codifica la password
        invitato.setRuolo(invitatiDTO.getRuolo() != null ? invitatiDTO.getRuolo() : Ruolo.NORMAL_USER);

        // Imposta un valore predefinito per Partecipazione se nullo
        Partecipazione partecipazione = Optional.ofNullable(invitatiDTO.getPartecipazione())
                .orElse(Partecipazione.PENDIENTE);
        invitato.setPartecipazione(partecipazione);

        invitato.setPlusOneAllowed(invitatiDTO.isPlusOneAllowed());
        invitato.setPlusOneConfirmed(invitatiDTO.isPlusOneConfirmed());

        if (invitatiDTO.getPlusOne() != null) {
            if (!invitato.isPlusOneAllowed()) {
                throw new IllegalArgumentException("Il PlusOne non è consentito per questo invitato.");
            }
            if (!invitato.isPlusOneConfirmed()) {
                throw new IllegalArgumentException("Il PlusOne deve essere confermato prima di aggiungerlo.");
            }

            PlusOne plusOne = new PlusOne();
            plusOne.setFirstName(invitatiDTO.getPlusOne().getFirstName());
            plusOne.setLastName(invitatiDTO.getPlusOne().getLastName());
            plusOne.setAllergies(invitatiDTO.getPlusOne().getAllergies());
            plusOne.setDieta(invitatiDTO.getPlusOne().getDieta());
            invitato.setPlusOne(plusOne);
        }

        return invitatiRepository.save(invitato);
    }


    @Transactional
    public Invitati updateInvitatoProperties(Long id, InvitiatiDTO invitatiDTO) {
        Invitati invitato = invitatiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invitato non trovato con ID: " + id));

        invitato.setRuolo(Optional.ofNullable(invitatiDTO.getRuolo()).orElse(invitato.getRuolo()));
        invitato.setDieta(Optional.ofNullable(invitatiDTO.getDieta()).orElse(invitato.getDieta()));
        invitato.setAllergies(Optional.ofNullable(invitatiDTO.getAllergies()).orElse(invitato.getAllergies()));
        invitato.setPartecipazione(invitatiDTO.getPartecipazione());

        if (invitato.isPlusOneAllowed()) {
            invitato.setPlusOneConfirmed(invitatiDTO.isPlusOneConfirmed());

            if (invitato.isPlusOneConfirmed()) {
                if (invitato.getPlusOne() == null) {
                    invitato.setPlusOne(new PlusOne());
                }
                PlusOne plusOne = invitato.getPlusOne();
                if (invitatiDTO.getPlusOne() != null) {
                    plusOne.setFirstName(Optional.ofNullable(invitatiDTO.getPlusOne().getFirstName()).orElse(plusOne.getFirstName()));
                    plusOne.setLastName(Optional.ofNullable(invitatiDTO.getPlusOne().getLastName()).orElse(plusOne.getLastName()));
                    plusOne.setAllergies(Optional.ofNullable(invitatiDTO.getPlusOne().getAllergies()).orElse(plusOne.getAllergies()));
                    plusOne.setDieta(Optional.ofNullable(invitatiDTO.getPlusOne().getDieta()).orElse(plusOne.getDieta()));
                }
            } else {
                invitato.setPlusOne(null);
            }
        } else {
            invitato.setPlusOne(null);
        }

        return invitatiRepository.save(invitato);
    }




    public Optional<Invitati> getInvitatoById(int id) {
        return invitatiRepository.findById((long) id);
    }

    public Optional<Invitati> getInvitatoByUsername(String username) {
        return invitatiRepository.findByUsername(username);
    }

    public List<Invitati> getAllInvitati() {
        return invitatiRepository.findAll();
    }

    public Optional<Invitati> getInvitatoByNameAndSurname(String firstName, String lastName) {
        return invitatiRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Transactional
    public void deleteInvitato(Long id) {
        Invitati invitato = invitatiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invitato non trovato con ID: " + id));
        invitatiRepository.delete(invitato);
    }
}
