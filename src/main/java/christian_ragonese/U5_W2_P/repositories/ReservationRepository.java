package christian_ragonese.U5_W2_P.repositories;

import christian_ragonese.U5_W2_P.entities.Employee;
import christian_ragonese.U5_W2_P.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    boolean existsByEmployeeAndTripDate(Employee employee, LocalDate reservationDate);

    boolean existsByEmployeeId(UUID employeeId);
}
