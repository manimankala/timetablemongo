package com.timetable.TimeTableProject.common;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {
  private int errorCode;
    public CustomException(String message,int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
