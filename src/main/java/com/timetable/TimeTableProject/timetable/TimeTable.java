package com.timetable.TimeTableProject.timetable;

import com.timetable.TimeTableProject.common.Constants;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Data
@Document
public class TimeTable {
    @Id
    private UUID id;
    private String name;
    private UUID businessId;
    private String businessName;
    private UUID createdById;
    private String createdBy;
    private Constants.Class className;
    private Constants.Section section;
    private Constants.Semester semester;
    private Constants.Branch branch;
    private Map<DayOfWeek, List<TimeTableEntry>> elemList;
    @Transient
    private String startDateInput;
    @Transient
    private String endDateInput;
    private LocalDate startDate;
    private LocalDate endDate;
    private Date createdTimeStamp;
    private Date updatedTimeStamp;

    public TimeTable() {
        if (id == null) {
            this.id = UUID.randomUUID();
            this.createdTimeStamp = new Date();
            this.updatedTimeStamp = this.createdTimeStamp;
            this.elemList = new HashMap<>();
        }
    }

}