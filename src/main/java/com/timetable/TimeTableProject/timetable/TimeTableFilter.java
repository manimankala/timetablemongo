package com.timetable.TimeTableProject.timetable;

import com.timetable.TimeTableProject.common.Constants;
import lombok.Data;

@Data
public class TimeTableFilter {
    private Constants.Branch branch;
    private Constants.Class className;
    private Constants.Section section;
    private Constants.Semester semester;
}
