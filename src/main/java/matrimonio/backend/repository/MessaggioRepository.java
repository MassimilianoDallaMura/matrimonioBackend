package matrimonio.backend.repository;

import matrimonio.backend.entity.Messaggio;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessaggioRepository extends JpaRepository<Messaggio, Long> {
    // query personalizzate se necessario
}
