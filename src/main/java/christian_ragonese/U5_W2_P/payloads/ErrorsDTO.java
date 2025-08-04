package christian_ragonese.U5_W2_P.payloads;


import java.time.LocalDateTime;

public record ErrorsDTO(String message, LocalDateTime timestamp) {
}
