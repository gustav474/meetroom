package com.gustav474.meetroom.DTO;

import lombok.Data;

/**
 * @author Sergey Lapshin
 */
@Data
public class CellDTO {
    private Long eventId;
    private String eventTimeOfBegin;
    private String eventTimeOfEnd;
    private Integer hour;
    public boolean isBusy;
}
