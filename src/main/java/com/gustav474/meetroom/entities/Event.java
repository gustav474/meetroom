package com.gustav474.meetroom.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Sergey Lapshin
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private LocalDateTime dateTimeOfCreation;
    private LocalDateTime dateTimeOfBegin;
    private LocalDateTime dateTimeOfEnd;
    private Long createdByUserId;

}
