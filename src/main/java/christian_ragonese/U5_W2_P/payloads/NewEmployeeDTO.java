package christian_ragonese.U5_W2_P.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewEmployeeDTO(

        @NotEmpty(message = "L'username è obbligatorio!")
        @Size(min = 2, max = 40, message = "L'username deve essere compreso tra 2 e 15 caratteri!")
        String username,
        @NotEmpty(message = "Il nome è obbligatorio!")
        @Size(min = 2, max = 40, message = "Il nome deve essere compreso tra 2 e 40 caratteri!")
        String name,
        @NotEmpty(message = "Il cognome è obbligatorio!")
        @Size(min = 2, max = 40, message = "Il cognome deve essere compreso tra 2 e 40 caratteri!")
        String surname,
        @NotEmpty(message = "L'indirizzo email è obbligatorio")
        @Email(message = "L'indirizzo email inserito non è nel formato giusto")
        String email,
        String avatar) {


}
