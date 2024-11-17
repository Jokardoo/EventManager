package jokardoo.eventmanager.repository;

import jokardoo.eventmanager.domain.event.eventRegistration.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationEntity, Long> {
    boolean existsByEventIdAndUserId(Long eventId, Long userId);

    Optional<RegistrationEntity> findByEventIdAndUserId(Long eventId, Long userId);

    List<RegistrationEntity> findAllByUserId(Long userId);

    @Query("""
            SELECT r.userId FROM RegistrationEntity r WHERE r.eventId = :eventId 
            """)
    List<Long> findAllSubscriberId(@Param("eventId") Long eventId);
}
