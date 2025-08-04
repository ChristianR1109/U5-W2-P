package christian_ragonese.U5_W2_P.services;

import christian_ragonese.U5_W2_P.entities.Journey;
import christian_ragonese.U5_W2_P.payloads.NewJourneyDTO;
import christian_ragonese.U5_W2_P.repositories.JourneyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JourneyService {
    @Autowired
    private JourneyRepository journeyRepository;

    public Journey save(NewJourneyDTO payload) {
        String destination = payload.destination();
        String date = payload.date();
        String status = payload.status();

        Journey newJourney = new Journey(destination, date, status);
        Journey savedJouney = this.journeyRepository.save(newJourney);

        log.info("Il viaggio con id : " + savedJouney.getId() + " è stato salvato correttamente!");
        return savedJouney;
    }

}
