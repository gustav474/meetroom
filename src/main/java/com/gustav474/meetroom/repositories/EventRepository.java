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
    List<Event> findAllByDateTimeOfBegin(LocalDateTime dateTimeOfBegin);
    Event findEventById(Long id);

//    We take all events that intersect with the newly added
//    @Query(value="SELECT * FROM events WHERE ( " +
//           "(date_of_begin BETWEEN :dateOfBegin and :dateOfEnd)" +
//        "and" +
//            "(time_of_begin BETWEEN :timeOfBegin AND :timeOfEnd)" +
//        "or" +
//            "(date_of_end BETWEEN :dateOfBegin and :dateOfEnd)" +
//        "and" +
//            "(time_of_end BETWEEN :timeOfBegin AND :timeOfEnd));", nativeQuery = true)
//    List<Event> findAllCrossedEvents(@Param("dateOfBegin") Date dateOfBegin,
//                                     @Param("timeOfBegin") Date timeOfBegin,
//                                     @Param("dateOfEnd") Date dateOfEnd,
//                                     @Param("timeOfEnd") Date timeOfEnd);

    @Query(value="SELECT * FROM events WHERE ( " +
            "(date_time_of_begin BETWEEN :dateTimeOfBegin and :dateTimeOfEnd)" +
            "and" +
//            "(time_of_begin BETWEEN :timeOfBegin AND :timeOfEnd)" +
//            "or" +
            "(date_time_of_end BETWEEN :dateTimeOfBegin and :dateTimeOfEnd));", nativeQuery = true)
//            "and" +
//            "(time_of_end BETWEEN :timeOfBegin AND :timeOfEnd));", nativeQuery = true)
    List<Event> findAllCrossedEvents(@Param("dateTimeOfBegin") LocalDateTime dateTimeOfBegin,
                                     @Param("dateTimeOfEnd") LocalDateTime dateTimeOfEnd);
}
