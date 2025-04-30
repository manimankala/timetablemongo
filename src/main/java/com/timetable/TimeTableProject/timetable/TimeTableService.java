package com.timetable.TimeTableProject.timetable;

import com.timetable.TimeTableProject.common.Constants;
import com.timetable.TimeTableProject.common.CustomException;
import com.timetable.TimeTableProject.user.User;
import com.timetable.TimeTableProject.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TimeTableService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private  final TimeTableRepo timeTableRepo;
    private final UserRepo userRepo;

    @Autowired
    public TimeTableService(TimeTableRepo timeTableRepo, UserRepo userRepo) {
        this.timeTableRepo = timeTableRepo;
        this.userRepo = userRepo;
    }

    public TimeTable createTimeTable(TimeTable timeTable) {
        DateTimeFormatter timeFormat=DateTimeFormatter.ofPattern("hh:mm:a");
        DateTimeFormatter dateFormat=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if(timeTable.getCreatedById()!=null){
            Optional<User> user=userRepo.findById(timeTable.getCreatedById());
            if(user.isPresent()){
                if(user.get().getRole()== Constants.Role.ADMIN) {
                    timeTable.setStartDate(LocalDate.parse(timeTable.getStartDateInput(), dateFormat));
                    timeTable.setEndDate(LocalDate.parse(timeTable.getEndDateInput(), dateFormat));
                    int daysInSem = timeTable.getEndDate().getDayOfYear()-timeTable.getStartDate().getDayOfYear();
                    for (Map.Entry<DayOfWeek, List<TimeTableEntry>> entry : timeTable.getElemList().entrySet()) {
                        List<TimeTableEntry> timeTableEntries = entry.getValue();
                        for (TimeTableEntry timeTableEntry : timeTableEntries) {
                            timeTableEntry.setId(UUID.randomUUID());
                            timeTableEntry.setIsAvailable(new boolean[daysInSem]);
                            Arrays.fill(timeTableEntry.getIsAvailable(), true);                        }
                    }
                    return timeTableRepo.save(timeTable);
                }else {
                    throw new RuntimeException("User is not authorized to create timetable");
                }
            }else {
                throw new RuntimeException("User not found");
            }
        }
        return null;
    }

    public TimeTable updateTimeTable(TimeTable timeTable) {
        if(timeTable.getCreatedById()!=null){
            Optional<User> user=userRepo.findById(timeTable.getCreatedById());
            if(user.isPresent()){
                if(user.get().getRole()== Constants.Role.ADMIN) {
                    AtomicReference<TimeTable> tt=new AtomicReference<>();
                    Optional<TimeTable> existingTimeTable = timeTableRepo.findById(timeTable.getId());
                    existingTimeTable.ifPresent(e -> {
                        e.setName(timeTable.getName());
                        e.setCreatedBy(timeTable.getCreatedBy());
                        e.setCreatedById(timeTable.getCreatedById());
                        e.setElemList(timeTable.getElemList());
                        e.setBranch(timeTable.getBranch());
                        e.setClassName(timeTable.getClassName());
                        e.setSemester(timeTable.getSemester());
                        e.setSection(timeTable.getSection());
                        e.setUpdatedTimeStamp(timeTable.getUpdatedTimeStamp());
                        tt.set(e);
                    });
                    return timeTableRepo.save(tt.get());
                }else {
                    throw new RuntimeException("User is not authorized to update timetable");
                }
            }else {
                throw new RuntimeException("User not found");
            }
        }
        return null;
    }

    public List<TimeTable> getFilterTimeTable(TimeTableFilter filter) {
        Query query = new Query();
        if(filter.getBranch() != null) {
            query.addCriteria(Criteria.where("branch").is(filter.getBranch()));
        }
        if (filter.getSemester()!= null) {
            query.addCriteria(Criteria.where("semester").is(filter.getSemester()));
        }
        if (filter.getClassName() != null) {
            query.addCriteria(Criteria.where("className").is(filter.getClassName()));
        }
        if (filter.getSection() != null) {
            query.addCriteria(Criteria.where("section").is(filter.getSection()));
        }
        return mongoTemplate.find(query, TimeTable.class);
    }
    public String deleteTimeTable(UUID id){
        if(timeTableRepo.findById(id).isPresent()) {
            String result= timeTableRepo.findById(id).get().getName().toLowerCase();
            timeTableRepo.deleteById(id);
            return result+" Deleted Successfully";
        }
        else {
            throw new RuntimeException("User not found");
        }
    }
    public String markStaffAttendance(boolean status,UUID timeTableEntryId,UUID timeTableId,String date,DayOfWeek dayOfWeek){
        Optional<TimeTable> timeTable = timeTableRepo.findById(timeTableId);
        DateTimeFormatter dateFormat=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if (timeTable.isPresent()) {
            TimeTable tt = timeTable.get();
            int index = LocalDate.parse(date, dateFormat).getDayOfYear() - tt.getStartDate().getDayOfYear();
            TimeTableEntry timeTableEntry = tt.getElemList().get(dayOfWeek).stream().filter(e -> e.getId() != null && e.getId().equals(timeTableEntryId)).findFirst().orElse(null);
            if (timeTableEntry != null) {
                timeTableEntry.getIsAvailable()[index] = status;
                timeTableRepo.save(tt);
                return "Attendance marked successfully";
            } else {
                throw new CustomException("ERROR!!!!",1001);
            }
        }
        else{
            throw new CustomException("TimeTable not found",1001);
        }
    }
}
