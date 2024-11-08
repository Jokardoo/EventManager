package jokardoo.eventmanager.domain.event.eventRegistration;

import lombok.Data;

@Data
public class Registration {
    private Long id;
    private Long eventId;
    private Long userId;
}
