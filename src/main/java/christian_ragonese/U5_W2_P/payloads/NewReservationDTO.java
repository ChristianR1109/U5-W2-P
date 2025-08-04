package christian_ragonese.U5_W2_P.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record NewReservationDTO(

        @NotNull(message = "L'id Dipendente è obbligatorio!")
        UUID employeeId,
        @NotNull(message = "L'id Viaggio è obbligatiorio!")
        UUID journeyId,
        @NotEmpty(message = "Le note sono obbligatorie!")
        @Size(min = 2, max = 100, message = "Le note devono essere comprese tra 2 e 100 caratteri!")
        String note,
        @NotNull(message = "La data è obbligatoria!")
        LocalDate reservationDate
) {
}
