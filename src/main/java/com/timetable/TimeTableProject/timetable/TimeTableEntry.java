package com.timetable.TimeTableProject.timetable;

import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.time.LocalTime;
import java.util.UUID;

@Data
public class TimeTableEntry {
    private UUID id;
    @Transient
    private String fromTimeInput;
    @Transient
    private String toTimeInput;
    private LocalTime fromTime;
    private LocalTime toTime;
    private String subject;
    private UUID staffId;
    private String staffName;
    private boolean[] isAvailable;
}