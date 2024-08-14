package matrimonio.backend.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import matrimonio.backend.entity.Invitati;
import matrimonio.backend.enums.Ruolo;
import matrimonio.backend.enums.Dieta;
import matrimonio.backend.enums.Partecipazione;
import matrimonio.backend.repository.InvitatiRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private InvitatiRepository invitatiRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (invitatiRepository.findByUsername("admin") == null) {
            Invitati admin = new Invitati();
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("12345678"));
            admin.setRuolo(Ruolo.ADMIN);
            admin.setDieta(Dieta.VEGANA);
            admin.setPartecipazione(Partecipazione.PENDIENTE);
            admin.setPlusOneAllowed(false);

            invitatiRepository.save(admin);

            System.out.println("Utente admin creato con successo!");
        } else {
            System.out.println("Utente admin esistente, nessuna creazione necessaria.");
        }
    }
}
