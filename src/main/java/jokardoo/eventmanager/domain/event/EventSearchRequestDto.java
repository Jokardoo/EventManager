package jokardoo.eventmanager.domain.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventSearchRequestDto {

    private String name;

    @Positive(message = "The number of seats should be positive!")
    private int placesMin;

    @Positive(message = "The number of seats should be positive!")
    private int placesMax;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateStartBefore;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateStartAfter;

    @Positive(message = "Cost should be positive!")
    private int costMin;

    @Positive(message = "Cost should be positive!")
    private int costMax;

    @Positive(message = "Duration should be positive!")
    private int durationMin;

    @Positive(message = "Duration should be positive!")
    private int durationMax;

    private Long locationId;

    private EventStatus status;


}
