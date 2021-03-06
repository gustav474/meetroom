package com.gustav474.meetroom.services;

import com.gustav474.meetroom.DTO.EventDTO;
import com.gustav474.meetroom.DTO.EventFormDTO;
import com.gustav474.meetroom.entities.Event;
import java.util.List;

/**
 * @author Sergey Lapshin
 */
public interface EventService {
    /**
     * Delete event from database by id
     * @param id Long id
     * @return True is success, False if not
     */
    public boolean deleteById(Long id);

    /**
     * Create event (booking)
     * @param eventFormDTO
     * @return True is success, False if not
     */
    public boolean makeEvents (EventFormDTO eventFormDTO) throws CantCreateEventOnPastException, IntersectingEventsException;

    /**
     * Return collection with all existing events
     * @return List
     */
    public List<Event> getEvents();

    /**
     * Return event by id
     * @param id
     * @return Event
     */
    public Event getEvent(Long id);

    /**
     * Return eventDTO from event
     * @param event
     * @return Event
     */
    public EventDTO convertFromEvent(Event event);

}
