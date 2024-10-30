package jokardoo.eventmanager.repository;

import jokardoo.eventmanager.domain.event.eventRegistration.EventRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistrationEntity, Long> {
    boolean existsByEventIdAndUserId(Long eventId, Long userId);

    Optional<EventRegistrationEntity> findByEventIdAndUserId(Long eventId, Long userId);

    List<EventRegistrationEntity> findAllByUserId(Long userId);
}
