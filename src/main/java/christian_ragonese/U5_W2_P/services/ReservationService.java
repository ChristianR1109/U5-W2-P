package christian_ragonese.U5_W2_P.services;


import christian_ragonese.U5_W2_P.entities.Employee;
import christian_ragonese.U5_W2_P.entities.Journey;
import christian_ragonese.U5_W2_P.entities.Reservation;
import christian_ragonese.U5_W2_P.enums.JourneyStatus;
import christian_ragonese.U5_W2_P.exceptions.BadRequestException;
import christian_ragonese.U5_W2_P.payloads.NewReservationDTO;
import christian_ragonese.U5_W2_P.repositories.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JourneyService journeyService;

    public List<Reservation> findAll() {

        return this.reservationRepository.findAll();
    }


    public Reservation saveReservation(NewReservationDTO payload) {
        Employee employee = employeeService.findById(payload.employeeId());
        Journey journey = journeyService.findById(payload.journeyId());

        boolean alreadyReserved = reservationRepository.existsByEmployeeAndTripDate(employee, payload.reservationDate());
        if (alreadyReserved) {
            throw new BadRequestException("Il dipendente ha già una prenotazione per questa data");
        }
        if (journey.getStatus() != JourneyStatus.SCHEDULED) {
            throw new BadRequestException(" Non puoi creare una prenotazione già completata!");
        }
        Reservation reservation = new Reservation();
        reservation.setEmployee(employee);
        reservation.setJourney(journey);
        reservation.setReservationDate(payload.reservationDate());
        reservation.setNote(payload.note());

        return reservationRepository.save(reservation);
    }
}
