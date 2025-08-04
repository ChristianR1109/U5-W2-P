package christian_ragonese.U5_W2_P.services;

import christian_ragonese.U5_W2_P.entities.Journey;
import christian_ragonese.U5_W2_P.exceptions.NotFoundException;
import christian_ragonese.U5_W2_P.payloads.NewJourneyDTO;
import christian_ragonese.U5_W2_P.repositories.JourneyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class JourneyService {
    @Autowired
    private JourneyRepository journeyRepository;

    //--------------------SAVE------------------------------------------
    public Journey save(NewJourneyDTO payload) {
        String destination = payload.destination();
        String date = payload.date();
        String status = payload.status();

        Journey newJourney = new Journey(destination, date, status);
        Journey savedJouney = this.journeyRepository.save(newJourney);

        log.info("Il viaggio con id : " + savedJouney.getId() + " è stato salvato correttamente!");
        return savedJouney;
    }

    //------------------------FIND ALL----------------------------------------
    public Page<Journey> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        return this.journeyRepository.findAll(pageable);
    }

    //------------------------FIND BY ID------------------------------------

    public Journey findById(UUID journeyId) {
        return this.journeyRepository.findById(journeyId).orElseThrow(() -> new NotFoundException(String.valueOf(journeyId)));
    }

    //--------------------FIND BY ID AND UPDATE------------------------------------------------

    public Journey findByIdAndUpdate(UUID journeyId, NewJourneyDTO payload) {

        Journey found = this.findById(journeyId);

        found.setDestination(payload.destination());
        found.setDate(payload.date());
        found.setStatus(payload.status().toUpperCase());

        Journey modifiedJourney = this.journeyRepository.save(found);

        log.info("Il viaggio con id : " + found.getId() + " è stato modificato con successo!");

        return modifiedJourney;
    }

    //--------------------FIND BY ID AND DELETE------------------------------------------------
    public void findByIdAndDelete(UUID journeyId) {
        Journey found = this.findById(journeyId);
        this.journeyRepository.delete(found);
    }

}
