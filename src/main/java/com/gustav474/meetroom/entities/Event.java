package com.gustav474.meetroom.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
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

    @Temporal(TemporalType.DATE)
    private Date dateOfCreation;

    @Temporal(TemporalType.TIME)
    private Date timeOfCreation;

    @Temporal(TemporalType.DATE)
    private Date dateOfBegin;

    @Temporal(TemporalType.TIME)
    private Date timeOfBegin;

    @Temporal(TemporalType.DATE)
    private Date dateOfEnd;

    @Temporal(TemporalType.TIME)
    private Date timeOfEnd;

    private Long createdByUserId;

}
