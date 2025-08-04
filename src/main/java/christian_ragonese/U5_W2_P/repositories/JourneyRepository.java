package christian_ragonese.U5_W2_P.repositories;

import christian_ragonese.U5_W2_P.entities.Journey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JourneyRepository extends JpaRepository<Journey, UUID> {
}
