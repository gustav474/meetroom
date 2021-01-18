package com.gustav474.meetroom.services;

import com.gustav474.meetroom.DTO.CellDTO;
import com.gustav474.meetroom.DTO.DayOfWeekDTO;
import com.gustav474.meetroom.DTO.WeekDTO;
import com.gustav474.meetroom.entities.Event;
import com.gustav474.meetroom.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.*;

/**
 * @author Sergey Lapshin
 */
@Service
public class IndexPageServiceImpl  implements  IndexPageService{

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Integer> getHours() {
        List hours = new ArrayList<Integer>();
        for (Integer i = 1; i < 25; i++) {
            hours.add(i);
        }
        return hours;
    }

    @Override
    public List<String> getHours24() {
        List hours24 = new ArrayList<String>();
        for (Integer i = 0; i < 24; i++) {
            String hour;
            if (i <= 9)  hour = "0" + i;
            else hour = i.toString();
            hours24.add(hour + ":" + "00");
        }
        return hours24;
    }

    @Override
    public WeekDTO getWeek(LocalDateTime dateTime, Integer ... weekNumber) {
        Integer _weekNumber;
        Locale rus = new Locale("ru", "RU");
        WeekFields weekFields = WeekFields.of(rus);
        Integer сurrentWeekNumber = dateTime.get(weekFields.weekOfWeekBasedYear());

        if (weekNumber.length != 0) {
            _weekNumber = weekNumber[0];

            if (_weekNumber > сurrentWeekNumber) {
                return getNextWeek(dateTime, _weekNumber, сurrentWeekNumber);
            } else if (_weekNumber < сurrentWeekNumber) {
                return getLastWeek(dateTime, _weekNumber, сurrentWeekNumber);
            } else if (_weekNumber == сurrentWeekNumber) {
                return getCurrentWeek(dateTime, сurrentWeekNumber);
            }
        }
        return getCurrentWeek(dateTime, сurrentWeekNumber);
    }

    private WeekDTO getNextWeek(LocalDateTime dateNow, Integer requestedWeekNumber, Integer сurrentWeekNumber) {
        WeekDTO week = new WeekDTO();
        LocalDateTime dateInRequestedWeek = dateNow.plusWeeks(requestedWeekNumber - сurrentWeekNumber);
        week.setDaysOfWeek(getWeekByDate(dateInRequestedWeek));
        week.setWeekNumber(requestedWeekNumber);
        week.setCurrentWeekNumber(сurrentWeekNumber);
        return week;
    }

    private WeekDTO getLastWeek(LocalDateTime dateNow, Integer requestedWeekNumber, Integer сurrentWeekNumber) {
        WeekDTO week = new WeekDTO();
        LocalDateTime dateInRequestedWeek = dateNow.minusWeeks(сurrentWeekNumber - requestedWeekNumber);
        week.setDaysOfWeek(getWeekByDate(dateInRequestedWeek));
        week.setWeekNumber(requestedWeekNumber);
        week.setCurrentWeekNumber(сurrentWeekNumber);
        return week;
    }

    private WeekDTO getCurrentWeek(LocalDateTime dateNow, Integer сurrentWeekNumber) {
        WeekDTO week = new WeekDTO();
        week.setDaysOfWeek(getWeekByDate(dateNow));
        week.setWeekNumber(сurrentWeekNumber);
        week.setCurrentWeekNumber(сurrentWeekNumber);
        return week;
    }

//    Filling the day cells with events and hours
    private void fillingCells(DayOfWeekDTO dayOfWeekDTO,
                              List<Event> events,
                              List<CellDTO> cells,
                              List<DayOfWeekDTO> week,
                              String dayRU,
                              LocalDateTime _date) {
        if (events.size() != 0) {
            for (Integer y = 0; y < 24; y++) {
                CellDTO cell = new CellDTO();
                for (Event event : events) {
//                    If event begins in current day
                    if (event.getDateTimeOfBegin().toLocalDate().isEqual(_date.toLocalDate()) &&
                            y == event.getDateTimeOfBegin().getHour()) {
                        cell.setEventId(event.getId());
                        cell.setEventTimeOfBegin(event.getDateTimeOfBegin().toLocalTime().toString());
                        cell.setHour(y);
                        cell.setStringHour(LocalTime.of(y, 0).toString());
                        cell.setBusy(true);
                        break;
//                    If event filling many hours in same day events
                    } else if (y > event.getDateTimeOfBegin().getHour() &&
                            event.getDateTimeOfBegin().toLocalDate().isEqual(_date.toLocalDate()) &&
                            y < event.getDateTimeOfEnd().getHour()) {
                        cell.setEventId(event.getId());
                        cell.setHour(y);
                        cell.setStringHour(LocalTime.of(y, 0).toString());
                        cell.setBusy(true);
                        break;
//                    If event filling many hours in day of begins
                    } else if (y > event.getDateTimeOfBegin().getHour() &&
                            event.getDateTimeOfEnd().toLocalDate().isAfter(_date.toLocalDate())) {
                        cell.setEventId(event.getId());
                        cell.setHour(y);
                        cell.setStringHour(LocalTime.of(y, 0).toString());
                        cell.setBusy(true);
                        break;
//                    If event filling many hours in day of ends
                    } else if (y < event.getDateTimeOfEnd().getHour() &&
                            event.getDateTimeOfBegin().toLocalDate().isBefore(_date.toLocalDate())) {
                        cell.setEventId(event.getId());
                        cell.setHour(y);
                        cell.setStringHour(LocalTime.of(y, 0).toString());
                        cell.setBusy(true);
                        break;
//                     If event begins and ends in same day
                    } else if (event.getDateTimeOfEnd().toLocalDate().isEqual(event.getDateTimeOfBegin().toLocalDate())
                            && y == event.getDateTimeOfEnd().getHour()) {
                        cell.setEventId(event.getId());
                        cell.setEventTimeOfEnd(event.getDateTimeOfEnd().toLocalTime().toString());
                        cell.setHour(y);
                        cell.setStringHour(LocalTime.of(y, 0).toString());
                        cell.setBusy(true);
                        break;
//                     If event ends in current day
                    } else if (event.getDateTimeOfEnd().toLocalDate().isEqual(_date.toLocalDate())
                        && y == event.getDateTimeOfEnd().getHour()) {
                        cell.setEventId(event.getId());
                        cell.setEventTimeOfEnd(event.getDateTimeOfEnd().toLocalTime().toString());
                        cell.setHour(y);
                        cell.setStringHour(LocalTime.of(y, 0).toString());
                        cell.setBusy(true);
                        break;
                    } else {
                        cell.setHour(y);
                        cell.setStringHour(LocalTime.of(y, 0).toString());
                        cell.setBusy(false);
                    }
                }
                cells.add(cell);
            }
        } else {
            for (Integer y = 0; y < 24; y++) {
                CellDTO cell = new CellDTO();
                cell.setHour(y);
                cell.setStringHour(LocalTime.of(y, 0).toString());
                cell.setBusy(false);
                cells.add(cell);

            }
        }

        dayOfWeekDTO.setDayOfWeek(dayRU);
        dayOfWeekDTO.setDate(_date.toLocalDate());
        dayOfWeekDTO.setCells(cells);

        week.add(dayOfWeekDTO);
    }

    private void fillingWeek(LocalDateTime dateTime, DayOfWeekDTO dayOfWeekDTO, Locale rus, List<DayOfWeekDTO> week) {

        java.time.DayOfWeek day = dateTime.getDayOfWeek();
        String dayRU = day.getDisplayName(TextStyle.FULL, rus);
        List<Event> events = getEventsByDateOfBegin(dateTime.toLocalDate());
        List<CellDTO> cells = new ArrayList();

        fillingCells(dayOfWeekDTO, events, cells, week, dayRU, dateTime);
    }

    private List<Event> getEventsByDateOfBegin(LocalDate dateOfBegin) {
        List<Event> events = eventRepository.findAllByDateOfBeginAndEnd(dateOfBegin.atStartOfDay(), dateOfBegin.atStartOfDay().plusHours(24));
//        System.out.println("events: " + events);
        return events;
    }


    /**
     * Return week beginning with Monday by date
     * @return List of ColumnHeaderDTO
     */
    private List<DayOfWeekDTO> getWeekByDate(LocalDateTime date) {
        List<DayOfWeekDTO> week = new ArrayList();
        Locale rus = new Locale("ru", "RU");
        WeekFields weekFields = WeekFields.of(rus);
        LocalDateTime startingDate;

        int dayOfWeek = date.get(weekFields.dayOfWeek());

        if (dayOfWeek != 1) startingDate = date.minusDays(dayOfWeek - 1);
        else startingDate = date;

        for (int i = 0; i < 6; i++) {
            DayOfWeekDTO dayOfWeekDTO = new DayOfWeekDTO();
            LocalDateTime _date = startingDate.plusDays(i);
            fillingWeek(_date, dayOfWeekDTO, rus, week);
        }
        return week;
    }
}
