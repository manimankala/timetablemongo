package com.timetable.TimeTableProject.timetable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TimeTableRepo extends MongoRepository<TimeTable, UUID> {
}
