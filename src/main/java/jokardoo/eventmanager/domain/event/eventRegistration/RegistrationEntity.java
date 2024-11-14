package jokardoo.eventmanager.domain.event.eventRegistration;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "registration")
@Entity
@Data
public class RegistrationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "user_id")
    private Long userId;

    public RegistrationEntity(Long id, Long eventId, Long userId) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
    }

    public RegistrationEntity() {

    }
}
