package com.gustav474.meetroom.repositories;

import com.gustav474.meetroom.entities.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

/**
 * @author Sergey Lapshin
 */
@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findAllByDateOfBeginOrderByTimeOfBegin(Date dateOfBegin);
    Event findEventById(Long id);

//    We take all events that intersect with the newly added
    @Query(value="SELECT * FROM events WHERE ( " +
           "(date_of_begin BETWEEN :dateOfBegin and :dateOfEnd)" +
        "and" +
            "(time_of_begin BETWEEN :timeOfBegin AND :timeOfEnd)" +
        "or" +
            "(date_of_end BETWEEN :dateOfBegin and :dateOfEnd)" +
        "and" +
            "(time_of_end BETWEEN :timeOfBegin AND :timeOfEnd));", nativeQuery = true)
    List<Event> findAllCrossedEvents(@Param("dateOfBegin") Date dateOfBegin,
                                     @Param("timeOfBegin") Date timeOfBegin,
                                     @Param("dateOfEnd") Date dateOfEnd,
                                     @Param("timeOfEnd") Date timeOfEnd);
}
