package jokardoo.eventmanager.repository;

import jokardoo.eventmanager.domain.event.EventEntity;
import jokardoo.eventmanager.domain.event.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @Query("""
            SELECT e from  EventEntity e 
            WHERE (:durationMax IS NULL OR e.duration <= :durationMax)
            AND (:durationMin IS NULL OR e.duration >= :durationMin)
            AND (CAST (:dateStartBefore AS date) IS NULL OR e.date <= :dateStartBefore)
            AND (CAST (:dateStartAfter AS date) IS NULL OR e.date >= :dateStartAfter)
            AND (:placesMin IS NULL OR e.maxPlaces >= :placesMin)
            AND (:placesMax IS NULL OR e.maxPlaces <= :placesMax)
            AND (:locationId IS NULL OR e.locationId = :locationId) 
            AND (:eventStatus IS NULL OR e.status = :eventStatus) 
            AND (:name IS NULL OR e.name LIKE %:name%) 
            AND (:costMin IS NULL OR e.cost >= :costMin)
            AND (:costMax IS NULL OR e.cost <= :costMax)
            """)
    List<EventEntity> searchByFilter(@Param("durationMax") Integer durationMax,
                                     @Param("durationMin") Integer durationMin,
                                     @Param("dateStartBefore") LocalDateTime dateStartBefore,
                                     @Param("dateStartAfter") LocalDateTime dateStartAfter,
                                     @Param("placesMin") Integer placesMin,
                                     @Param("placesMax") Integer placesMax,
                                     @Param("locationId") Long locationId,
                                     @Param("eventStatus") EventStatus eventStatus,
                                     @Param("name") String name,
                                     @Param("costMin") Integer costMin,
                                     @Param("costMax") Integer costMax);

    List<EventEntity> findByOwnerId(Long id);


}
