package com.timetable.TimeTableProject.loginCred;

import com.timetable.TimeTableProject.common.Constants;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;
@Data
@Document
public class LoginDetails {
    @Id
    private UUID userId;
    private String username;
    private String password;
    private Constants.Role role;
}
