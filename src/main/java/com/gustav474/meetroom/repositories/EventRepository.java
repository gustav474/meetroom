package com.gustav474.meetroom.repositories;

import com.gustav474.meetroom.entities.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author Sergey Lapshin
 */
@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    @Query(value="SELECT * FROM events WHERE ( " +
            "(date_time_of_begin BETWEEN :dateTimeOfBegin and :dateTimeOfEnd)) OR " +
            "(date_time_of_end BETWEEN :dateTimeOfBegin and :dateTimeOfEnd);", nativeQuery = true)
    List<Event> findAllByDateOfBeginAndEnd(@Param("dateTimeOfBegin") LocalDateTime dateTimeOfBegin,
                                     @Param("dateTimeOfEnd") LocalDateTime dateTimeOfEnd);

    Event findEventById(Long id);

    @Query(value="SELECT * FROM events WHERE ( " +
            "(date_time_of_begin BETWEEN :dateTimeOfBegin and :dateTimeOfEnd)" +
            "and" +
            "(date_time_of_end BETWEEN :dateTimeOfBegin and :dateTimeOfEnd)" +
            "and" +
            "(:dateTimeOfBegin BETWEEN date_time_of_begin and date_time_of_end)" +
            "and" +
            "(:dateTimeOfEnd BETWEEN date_time_of_begin and date_time_of_end));", nativeQuery = true)
    List<Event> findAllCrossedEvents(@Param("dateTimeOfBegin") LocalDateTime dateTimeOfBegin,
                                     @Param("dateTimeOfEnd") LocalDateTime dateTimeOfEnd);
}
