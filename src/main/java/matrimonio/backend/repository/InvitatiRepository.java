package matrimonio.backend.repository;

import matrimonio.backend.entity.Invitati;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitatiRepository extends JpaRepository<Invitati, Long> {
    Optional<Invitati> findByFirstNameAndLastName(String firstName, String lastName);
    Optional<Invitati> findByUsername(String username);
}