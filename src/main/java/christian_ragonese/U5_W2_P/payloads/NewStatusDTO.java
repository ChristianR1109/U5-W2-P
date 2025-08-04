package christian_ragonese.U5_W2_P.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewStatusDTO(
        @NotEmpty(message = "Lo status è obbligatorio!")
        @Size(min = 9, max = 9, message = "Lo status deve essere di 9 caratteri, SCHEDULED O COMPLETED")
        String status
) {
}
