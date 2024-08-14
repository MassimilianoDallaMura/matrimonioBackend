package matrimonio.backend.repository;

import matrimonio.backend.entity.DonationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationInfoRepository extends JpaRepository<DonationInfo, Long> {
    // Trova il primo e unico record (per gestire un'unica informazione)
    DonationInfo findFirstByOrderById();
}
