package jokardoo.eventmanager.repository;

import jokardoo.eventmanager.domain.location.EventLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EventLocationRepository extends JpaRepository<EventLocationEntity, Integer> {

}
