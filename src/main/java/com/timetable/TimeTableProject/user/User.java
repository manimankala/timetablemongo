package com.timetable.TimeTableProject.user;

import com.timetable.TimeTableProject.common.AddressDetails;
import com.timetable.TimeTableProject.common.Constants;
import com.timetable.TimeTableProject.common.ContactDetails;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document
@Data
public class User {
    @Id
    private UUID id;
    private String name;
    private String unqNo;//rollNo for everyone
    private UUID businessId;
    private String businessName;
    private AddressDetails addressDetails;
    private ContactDetails contactDetails;
    private String subject; //for teacher and admin
    private String designation;
    private Constants.Class className;
    private Constants.Section section;
    private Constants.Semester semester;
    private Constants.Branch branch;
    private Date createdTimeStamp;
    private Date updatedTimeStamp;
    private Constants.Role role;
    public User() {
        if (id == null) {
            this.id = UUID.randomUUID();
            this.createdTimeStamp = new Date();
            this.updatedTimeStamp = this.createdTimeStamp;
            this.contactDetails = new ContactDetails();
            this.addressDetails = new AddressDetails();
        }
    }
}
