package christian_ragonese.U5_W2_P.services;

import christian_ragonese.U5_W2_P.entities.Journey;
import christian_ragonese.U5_W2_P.enums.JourneyStatus;
import christian_ragonese.U5_W2_P.exceptions.BadRequestException;
import christian_ragonese.U5_W2_P.exceptions.NotFoundException;
import christian_ragonese.U5_W2_P.payloads.NewJourneyDTO;
import christian_ragonese.U5_W2_P.payloads.NewStatusDTO;
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
        JourneyStatus status = JourneyStatus.valueOf(payload.status());

        Journey newJourney = new Journey(destination, date, status);
        Journey savedJourney = this.journeyRepository.save(newJourney);

        log.info("Il viaggio con id : " + savedJourney.getId() + " è stato salvato correttamente!");
        return savedJourney;
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
        found.setStatus(JourneyStatus.valueOf(payload.status().toUpperCase()));

        Journey modifiedJourney = this.journeyRepository.save(found);

        log.info("Il viaggio con id : " + found.getId() + " è stato modificato con successo!");

        return modifiedJourney;
    }

    //--------------------FIND BY ID AND DELETE------------------------------------------------
    public void findByIdAndDelete(UUID journeyId) {
        Journey found = this.findById(journeyId);
        this.journeyRepository.delete(found);
    }

    //-------------------UPDATE STATUS----------------------------------------------
    public Journey updateStatus(UUID journeyId, NewStatusDTO payload) {
        Journey journey = this.findById(journeyId);
        JourneyStatus newStatus;

        try {
            newStatus = JourneyStatus.valueOf(payload.status().toUpperCase());

        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Status non valido! --- " + payload.status() + " devi inserire SCHEDULED o COMPLETED");
        }
        journey.setStatus(newStatus);
        return journeyRepository.save(journey);
    }
}
