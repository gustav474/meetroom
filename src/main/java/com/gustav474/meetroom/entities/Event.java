package com.gustav474.meetroom.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

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

//    @Temporal(TemporalType.DATE)
//    private Date dateOfCreation;
//    private LocalDate dateOfCreation;

//    @Temporal(TemporalType.TIME)
//    private Date timeOfCreation;
//    private LocalTime timeOfCreation;

//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateTimeOfCreation;

//    @Temporal(TemporalType.DATE)
//    private Date dateOfBegin;
//    private LocalDate dateOfBegin;

//    @Temporal(TemporalType.TIME)
//    private Date timeOfBegin;
//    private LocalTime timeOfBegin;

//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateTimeOfBegin;

//    @Temporal(TemporalType.DATE)
//    private Date dateOfEnd;
//    private LocalDate dateOfEnd;

//    @Temporal(TemporalType.TIME)
//    private Date timeOfEnd;
//    private LocalTime timeOfEnd;

//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateTimeOfEnd;

    private Long createdByUserId;

}
