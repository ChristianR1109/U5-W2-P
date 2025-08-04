package christian_ragonese.U5_W2_P.entities;

import christian_ragonese.U5_W2_P.enums.JourneyStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "journeys")
@Getter
@Setter
@NoArgsConstructor
public class Journey {
    @Id
    @GeneratedValue
    private UUID id;
    private String destination;
    private String date;
    @Enumerated(EnumType.STRING)
    private JourneyStatus status;

    public Journey(String destination, String date, JourneyStatus status) {
        this.destination = destination;
        this.date = date;
        this.status = status;
    }
}
