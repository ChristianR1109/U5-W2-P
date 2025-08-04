package christian_ragonese.U5_W2_P.controllers;

import christian_ragonese.U5_W2_P.entities.Reservation;
import christian_ragonese.U5_W2_P.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<Reservation> getReservations() {
        return this.reservationService.findAll();
    }
}
