package com.gustav474.meetroom.services;

import com.gustav474.meetroom.DTO.CellDTO;
import com.gustav474.meetroom.DTO.DayOfWeekDTO;
import com.gustav474.meetroom.DTO.WeekDTO;
import com.gustav474.meetroom.entities.Event;
import com.gustav474.meetroom.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
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
        for (Integer i = 1; i < 25; i++) {
            String hour = null;
            if (i <= 9)  hour = "0" + i;
            else hour = i.toString();
            hours24.add(hour + ":" + "00");
        }
        return hours24;
    }

    @Override
    public WeekDTO getWeek(Integer ... weekNumber) {
        WeekDTO week = new WeekDTO();
        Integer _weekNumber;
        LocalDate dateInRequestedWeek;
        Locale rus = new Locale("ru", "RU");
        LocalDate dateNow = LocalDate.now();
        WeekFields weekFields = WeekFields.of(rus);
        Integer сurrentWeekNumber = dateNow.get(weekFields.weekOfWeekBasedYear());

        if (weekNumber.length != 0) {
            _weekNumber = weekNumber[0];

            if (_weekNumber > сurrentWeekNumber) {
                dateInRequestedWeek = dateNow.plusWeeks(_weekNumber - сurrentWeekNumber);
                week.setDaysOfWeek(getWeekByDate(dateInRequestedWeek));
                week.setWeekNumber(_weekNumber);
                week.setCurrentWeekNumber(сurrentWeekNumber);
                return week;
            } else {
                dateInRequestedWeek = dateNow.minusWeeks(сurrentWeekNumber - _weekNumber);
                week.setDaysOfWeek(getWeekByDate(dateInRequestedWeek));
                week.setWeekNumber(_weekNumber);
                week.setCurrentWeekNumber(сurrentWeekNumber);
                return week;
            }

        } else {
            week.setDaysOfWeek(getWeekByDate(dateNow));
            week.setWeekNumber(сurrentWeekNumber);
            week.setCurrentWeekNumber(сurrentWeekNumber);
            return week;
        }
    }

//    Filling the day cells with events and hours
    private void fillingCells(DayOfWeekDTO dayOfWeekDTO,
                              List<Event> events,
                              List<CellDTO> cells,
                              List week,
                              String dayRU,
                              LocalDate _date) {
        if (events.size() != 0) {
            for (Integer y = 1; y < 25; y++) {
                CellDTO cell = new CellDTO();
                for (Event event : events) {
                    if (y == event.getTimeOfBegin().getHours()) {
                        cell.setEvent(event);
                    } else {
                        cell.setHour(y);
                    }
                }
                cells.add(cell);
            }
        } else {
            for (Integer y = 1; y < 25; y++) {
                CellDTO cell = new CellDTO();
                cell.setHour(y);
                cells.add(cell);
            }
        }

        dayOfWeekDTO.setDayOfWeek(dayRU);
        dayOfWeekDTO.setDate(_date);
        dayOfWeekDTO.setCells(cells);

        week.add(dayOfWeekDTO);
    }


    private void fillingWeek(LocalDate _date, DayOfWeekDTO dayOfWeekDTO, int i, Locale rus, List week) {

        java.time.DayOfWeek day = _date.getDayOfWeek();
        String dayRU = day.getDisplayName(TextStyle.FULL, rus);
        List<Event> events = getEventsByDateOfBegin(java.sql.Date.valueOf(_date));
        List<CellDTO> cells = new ArrayList();

        fillingCells(dayOfWeekDTO, events, cells, week, dayRU, _date);
    }

    private List<Event> getEventsByDateOfBegin(Date dateOfBegin) {
        List<Event> events = eventRepository.findAllByDateOfBeginOrderByTimeOfBegin(dateOfBegin);
        return events;
    }


    /**
     * Return week beginning with Monday by date
     * @return List of ColumnHeaderDTO
     */
    private List<DayOfWeekDTO> getWeekByDate(LocalDate date) {
        List week = new ArrayList<DayOfWeekDTO>();
        Locale rus = new Locale("ru", "RU");
        WeekFields weekFields = WeekFields.of(rus);

        int dayOfWeek = date.get(weekFields.dayOfWeek());
//        Without sunday
        if (dayOfWeek == 7) dayOfWeek = 6;

        for (int i = dayOfWeek; i >= 1; i--) {
            DayOfWeekDTO dayOfWeekDTO = new DayOfWeekDTO();
            LocalDate _date = date.minusDays(i);

            if (_date.get(weekFields.dayOfWeek()) == 7) continue;
            fillingWeek(_date, dayOfWeekDTO, i, rus, week);
        }

        for (int i = 0; i <= 7 - dayOfWeek ; i++) {
            DayOfWeekDTO dayOfWeekDTO = new DayOfWeekDTO();
            LocalDate _date = date.minusDays(i);

            if (_date.get(weekFields.dayOfWeek()) == 7) continue;
            fillingWeek(_date, dayOfWeekDTO, i, rus, week);

        }
        return week;
    }
}
