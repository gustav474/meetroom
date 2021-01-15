package com.gustav474.meetroom.services;

import com.gustav474.meetroom.DTO.EventDTO;
import com.gustav474.meetroom.DTO.EventFormDTO;
import com.gustav474.meetroom.entities.Event;
import com.gustav474.meetroom.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Sergey Lapshin
 */
@Service
public class EventServiceImpl implements EventService {
    static final long ONE_MINUTE_IN_MILLIS = 60000;

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
    public boolean makeEvents(EventFormDTO eventFormDTO) throws CantCreateEventOnPastException, IntersectingEventsException {
        Event event = convertFromFormDTO(eventFormDTO);

        if (event.getDateTimeOfBegin().isBefore(LocalDateTime.now())) {
            throw new CantCreateEventOnPastException("Cant create event on past");
        }

//        Getting events in the database with intersections
            List<Event> crosserEvents = eventRepository.findAllCrossedEvents(event.getDateTimeOfBegin(),
                    event.getDateTimeOfEnd());

        if (crosserEvents.size() != 0) {
            throw new IntersectingEventsException("Intersecting events");
        } else {
            eventRepository.save(event);
            return true;
        }
    }

    public Event convertFromFormDTO(EventFormDTO eventFormDTO) {
        Event event = new Event();

        LocalDateTime dateTimeOfEnd;
        LocalDate dateOfBegin;
        LocalTime timeOfBegin;

        event.setDescription(eventFormDTO.getDescription());
        event.setDateTimeOfCreation(LocalDateTime.now());

        String sDateOfBegin = eventFormDTO.getDateOfBegin();
        String sHourOfBegin = eventFormDTO.getHour();
        String sMinutesOfBegin = eventFormDTO.getMinutes();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timerFormatter = DateTimeFormatter.ofPattern("H:mm");

        dateOfBegin = LocalDate.parse(sDateOfBegin, dateFormatter);
        timeOfBegin = LocalTime.parse(sHourOfBegin + ":" + sMinutesOfBegin, timerFormatter);

        event.setDateTimeOfBegin(LocalDateTime.of(dateOfBegin, timeOfBegin));

        Long duration = Long.parseLong(eventFormDTO.getDuration());
        dateTimeOfEnd = LocalDateTime.of(dateOfBegin, timeOfBegin.plusMinutes(duration));

        event.setDateTimeOfEnd(dateTimeOfEnd);
        event.setCreatedByUserId(eventFormDTO.getCreatedByUserId());
        return event;
    }

    public EventDTO convertFromEvent(Event event) {
        EventDTO eventDTO = new EventDTO();

        eventDTO.setDateOfBegin(event.getDateTimeOfBegin().toLocalDate());
        eventDTO.setTimeOfBegin(event.getDateTimeOfBegin().toLocalTime());
        eventDTO.setDateOfEnd(event.getDateTimeOfEnd().toLocalDate());
        eventDTO.setTimeOfEnd(event.getDateTimeOfEnd().toLocalTime());
        eventDTO.setDescription(event.getDescription());

        return eventDTO;
    }

}
