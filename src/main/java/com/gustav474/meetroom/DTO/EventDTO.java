package com.gustav474.meetroom.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventDTO {
    private Long eventId;
    private LocalDate dateOfBegin;
    private LocalTime timeOfBegin;
    private LocalDate dateOfEnd;
    private LocalTime timeOfEnd;
    private String description;
}
