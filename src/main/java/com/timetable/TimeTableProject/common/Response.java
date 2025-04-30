package com.timetable.TimeTableProject.common;

import lombok.Data;

@Data
public class Response {
    private Object data;

    public Response(Object data) {
        this.data = data;
    }
    public Response(){

    }

}