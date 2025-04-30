package com.timetable.TimeTableProject.timetable;

import lombok.Data;

import java.util.UUID;

@Data
public class TimeTableEntry {
    private UUID id;
    private String subject;
    private UUID staffId;
    private String staffName;
    private boolean[] isAvailable;
}