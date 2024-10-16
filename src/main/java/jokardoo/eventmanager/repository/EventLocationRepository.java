package jokardoo.eventmanager.repository;

import jokardoo.eventmanager.domain.location.EventLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventLocationRepository extends JpaRepository<EventLocationEntity, Integer> {

    List<EventLocationEntity> findAll();

    Optional<EventLocationEntity> findById(Integer id);

    void deleteById(Integer id);

    EventLocationEntity save(EventLocationEntity eventLocation);


}
