package christian_ragonese.U5_W2_P.controllers;


import christian_ragonese.U5_W2_P.entities.Journey;
import christian_ragonese.U5_W2_P.exceptions.ValidationException;
import christian_ragonese.U5_W2_P.payloads.NewJourneyDTO;
import christian_ragonese.U5_W2_P.payloads.NewJourneyRespDTO;
import christian_ragonese.U5_W2_P.services.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/journeys")
public class JourneyController {
    @Autowired
    private JourneyService journeyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewJourneyRespDTO save(@RequestBody @Validated NewJourneyDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            Journey newJourney = this.journeyService.save(payload);
            return new NewJourneyRespDTO(newJourney.getId());
        }
    }

}
