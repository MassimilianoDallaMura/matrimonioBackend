package matrimonio.backend.service;

import matrimonio.backend.DTO.MessaggioDTO;
import matrimonio.backend.entity.Messaggio;
import matrimonio.backend.entity.Invitati;
import matrimonio.backend.repository.MessaggioRepository;
import matrimonio.backend.repository.InvitatiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessaggioService {

    @Autowired
    private MessaggioRepository messaggioRepository;

    @Autowired
    private InvitatiRepository invitatiRepository;

    public Messaggio salvaMessaggio(MessaggioDTO messaggioDTO) {
        Messaggio messaggio = new Messaggio();
        messaggio.setTesto(messaggioDTO.getTesto());
        messaggio.setTimestamp(LocalDateTime.now());


        Optional<Invitati> autore = invitatiRepository.findById(messaggioDTO.getAutoreId());
        autore.ifPresent(messaggio::setAutore);

        return messaggioRepository.save(messaggio);
    }

    public List<Messaggio> getTuttiMessaggi() {
        return messaggioRepository.findAll();
    }

    public Optional<Messaggio> getMessaggioById(Long id) {
        return messaggioRepository.findById(id);
    }
}
