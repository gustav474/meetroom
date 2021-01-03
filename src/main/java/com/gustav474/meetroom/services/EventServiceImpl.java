package com.gustav474.meetroom.services;

import com.gustav474.meetroom.DTO.EventDTO;
import com.gustav474.meetroom.entities.Event;
import com.gustav474.meetroom.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Sergey Lapshin
 */
@Service
public class EventServiceImpl implements EventService{
    static final long ONE_MINUTE_IN_MILLIS=60000;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public Event getEvent(Long id) {
        Event event = eventRepository.findEventById(id);
        return event;
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            eventRepository.deleteById(id);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public List<Event> getEvents() {
        List<Event> events = (List<Event>) eventRepository.findAll();
        return events;
    }

    @Override
    public boolean makeEvents (EventDTO eventDTO) throws CantCreateEventOnPastException, IntersectingEventsException {
        Event event = convertFromDTO(eventDTO);

        Long eventDateAndTimeForCompareInMillis = event.getDateOfBegin().getTime() +
                                                  event.getTimeOfBegin().getTime() +
                                                  (4*60*ONE_MINUTE_IN_MILLIS);

        Date eventDateAndTimeForCompare = new Date(eventDateAndTimeForCompareInMillis);

//        Checking for the impossibility of creating an event in the past
        if (eventDateAndTimeForCompare.compareTo(new Date()) < 0) {
            throw new CantCreateEventOnPastException("Cant create event on past");
        }


//        Getting events in the database with intersections
        List<Event> crosserEvents = eventRepository.findAllCrossedEvents(event.getDateOfBegin(),
                                                                        event.getTimeOfBegin(),
                                                                        event.getDateOfEnd(),
                                                                        event.getTimeOfEnd());
        if (crosserEvents.size() != 0) {
            throw new IntersectingEventsException("Intersecting events");
        } else {
            eventRepository.save(event);
            return true;
        }
    }

    public Event convertFromDTO (EventDTO eventDTO) {
        Event event = new Event();

        Date date = null;
        Date time = null;

        event.setDescription(eventDTO.getDescription());
        event.setDateOfCreation(new Date());
        event.setTimeOfCreation(new Date());

        String sDate = eventDTO.getDateOfBegin();
        String sHour = eventDTO.getHour();
        String sMinutes = eventDTO.getMinutes();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        try {
            date = dateFormat.parse(sDate);
            time = timeFormat.parse(sHour + ":" + sMinutes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        event.setDateOfBegin(date);
        event.setTimeOfBegin(time);

        String duration = eventDTO.getDuration();
        event.setDateOfEnd(new Date(date.getTime() + Long.parseLong(duration) * ONE_MINUTE_IN_MILLIS));
        event.setTimeOfEnd(new Date(time.getTime() + Long.parseLong(duration) * ONE_MINUTE_IN_MILLIS));
        event.setCreatedByUserId(eventDTO.getCreatedByUserId());
        return event;
    }
}
