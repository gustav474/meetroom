package com.gustav474.meetroom.DTO;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Sergey Lapshin
 */
@Data
public class DayOfWeekDTO {
    private String dayOfWeek;
    private LocalDate date;
    private List<CellDTO> cells;
}
