package com.gustav474.meetroom.services;

import com.gustav474.meetroom.DTO.WeekDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Sergey Lapshin
 */
public interface IndexPageService {

    /**
     * Return hours for cell associacions
     * @return List of Integers
     */
    public List<Integer> getHours();

    /**
     * Return a string hours representation (00:00 format)
     * @return List of String
     */
    public List<String> getHours24();

    /**
     * Returns week (without sunday) with information about the date and day of the week
     * @param weekNumber Week number to be displayed
     * @return WeekDTO
     */
    public WeekDTO getWeek(LocalDateTime dateTime, Integer ...weekNumber);


}
