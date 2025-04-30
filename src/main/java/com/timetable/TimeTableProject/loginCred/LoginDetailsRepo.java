package com.timetable.TimeTableProject.loginCred;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LoginDetailsRepo extends MongoRepository<LoginDetails, UUID> {
    @Query("{'username':?0}")
    LoginDetails findByUsername(String username);
}
