package com.gustav474.meetroom.DTO;

import lombok.Data;
import java.util.List;

/**
 * @author Sergey Lapshin
 */
@Data
public class WeekDTO {
    private Integer weekNumber;
    private Integer currentWeekNumber;
    private List<DayOfWeekDTO> daysOfWeek;
}
