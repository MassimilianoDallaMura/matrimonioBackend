package matrimonio.backend.repository;

import matrimonio.backend.entity.PlusOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlusOneRepository  extends JpaRepository<PlusOne, Long> {
}
