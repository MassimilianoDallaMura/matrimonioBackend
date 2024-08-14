package matrimonio.backend.repository;

import matrimonio.backend.entity.Raccomandazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaccomandazioneRepository extends JpaRepository<Raccomandazione, Long> {
}

