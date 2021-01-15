package com.gustav474.meetroom.DTO;

import com.gustav474.meetroom.entities.Event;
import lombok.Data;

/**
 * @author Sergey Lapshin
 */
@Data
public class CellDTO {
//    private Event event;
    private Long eventId;
    private String eventTimeOfBegin;
    private Integer hour;
}
