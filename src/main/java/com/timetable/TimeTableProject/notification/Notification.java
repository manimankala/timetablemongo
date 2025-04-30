package com.timetable.TimeTableProject.notification;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document
@Data
public class Notification {
    @Id
    private UUID notificationId;
    private UUID userId;
    private String message;
    private Date createdTimeStamp;
    private Date updatedTimeStamp;
}
