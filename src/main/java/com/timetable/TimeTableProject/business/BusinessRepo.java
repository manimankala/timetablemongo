package com.timetable.TimeTableProject.business;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BusinessRepo extends MongoRepository<Business, UUID> {
}
