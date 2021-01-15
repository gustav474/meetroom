package com.gustav474.meetroom.DTO;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Sergey Lapshin
 */
@Data
public class EventFormDTO {
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private String dateOfBegin;

    @Digits(integer=2, fraction = 0)
    @Max(value = 24, message = "A day has just 23 hours!")
    @NotNull
    private String hour;

    @Digits(integer=2, fraction = 0)
    @Max(value = 60, message="An hour has just 60 minutes!")
    @NotNull
    private String minutes;

    @Min(value = 30, message = "Minimun booking time = 30 minutes")
    @Max(value = 7 * 24 * 60, message = "Maximum booking time = 1 week")
    @NotNull
    private String duration;

    private Long createdByUserId;

    
}
