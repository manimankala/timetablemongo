package com.timetable.TimeTableProject.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.UUID;

public interface UserRepo extends MongoRepository<User, UUID> {
    @Query("{'contactDetails.email': ?0}")
    User findByEmail( String email);
}
