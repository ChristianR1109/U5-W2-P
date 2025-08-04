package christian_ragonese.U5_W2_P.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewJourneyDTO(
        @NotEmpty(message = "La destinazione è obbligatoria!")
        @Size(min = 2, max = 50, message = "La destinazione deve essere compresa tra 2 e  50 caratteri!")
        String destination,
        @NotEmpty(message = "La data è obbligatoria!")
        @Size(min = 2, max = 50, message = "La data deve essere compresa tra 2 e 50 caratteri!")
        String date,
        @NotEmpty(message = "Lo stato è obbligatorio! e deve essere SCHEDULED o COMPLETED ")
        @Size(min = 9, max = 9, message = "Lo stato deve essere precisamente di 9 caratteri!")
        String status) {

}
