package com.timetable.TimeTableProject.business;

import com.timetable.TimeTableProject.common.AddressDetails;
import com.timetable.TimeTableProject.common.ContactDetails;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document
@Data
public class Business {
    @Id
    private UUID id;
    private String name;
    private AddressDetails address;
    private ContactDetails contact;
    private Date createdTimeStamp;
    private Date updatedTimeStamp;

    public Business() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
            this.createdTimeStamp = new Date();
            this.updatedTimeStamp = this.createdTimeStamp;
        }
    }
}
