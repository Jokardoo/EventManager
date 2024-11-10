package jokardoo.eventmanager.dto.event;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RegistrationDto {

    @Positive(message = "Event id should be positive!")
    private Long eventId;

    @Positive(message = "User id should be positive!")
    private Long userId;

    public RegistrationDto(Long eventId, Long userId) {
        this.eventId = eventId;
        this.userId = userId;
    }
}
