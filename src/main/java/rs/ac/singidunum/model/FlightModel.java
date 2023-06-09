package rs.ac.singidunum.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class FlightModel {
    private Integer id;
    private String flightKey;
    private String flightNumber;
    private String destination;
    private LocalDateTime scheduledAt;
    private LocalDateTime estimatedAt;
    private String connectedType;
    private String connectedFlight;
    private String plane;
    private String gate;
    private String terminal;
}
