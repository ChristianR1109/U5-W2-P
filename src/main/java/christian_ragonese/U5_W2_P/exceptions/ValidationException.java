package christian_ragonese.U5_W2_P.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {

    private List<String> errorMessages;

    public ValidationException(List<String> errorMessages) {
        super("Errori nella validazione!");
        this.errorMessages = errorMessages;

    }
}
