package jokardoo.eventmanager.domain.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventCreateRequestDto {

    @NotNull
    @Size(min = 2, max = 100, message = "Event name size should be between 2 and 100 characters")
    private String name;

    @Positive(message = "The number of seats should be positive!")
    @NotNull(message = "The number of seats must be determined!")
    private Integer maxPlaces;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;

    @Positive(message = "Cost should be positive!")
    @NotNull(message = "Cost can't be null!")
    private Integer cost;

    @Positive(message = "Duration should be positive!")
    @NotNull(message = "Duration can't be null!")
    private Integer duration;

    @NotNull(message = "Location id can't be null!")
    @Positive(message = "Location id should be positive!")
    private Long locationId;

}
