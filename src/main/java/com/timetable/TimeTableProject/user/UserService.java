package com.timetable.TimeTableProject.user;

import com.timetable.TimeTableProject.loginCred.LoginDetails;
import com.timetable.TimeTableProject.loginCred.LoginDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final LoginDetailsRepo loginDetailsRepo;
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo, LoginDetailsRepo loginDetailsRepo) {
        this.userRepo = userRepo;
        this.loginDetailsRepo = loginDetailsRepo;
    }


    public User createUser(User user) {
        LoginDetails loginDetails = new LoginDetails();
        if(userRepo.findByEmail(user.getContactDetails().getEmail()) != null) {
            throw new RuntimeException("User already exists");
        }
        String[] name=user.getName().split(" ");
        String defPas = name[0] + "@" + user.getContactDetails().getPhone().substring(6, 10);
        loginDetails.setUserId(user.getId());
        loginDetails.setUsername(user.getContactDetails().getEmail());
        loginDetails.setPassword(passwordEncoder.encode(defPas));
        loginDetails.setRole(user.getRole());
        loginDetailsRepo.save(loginDetails);
        return  userRepo.save(user);
    }

    public String updateUser(User user) {
        Optional<User> user1 = userRepo.findById(user.getId());
        AtomicReference<User> user2 = new AtomicReference<>();
        if (user1.isPresent()) {
            user1.get().setName(user.getName());
            user1.get().setAddressDetails(user.getAddressDetails());
            user1.get().getContactDetails().setPhone(user.getContactDetails().getPhone());
            user1.get().getContactDetails().setWebsite(user.getContactDetails().getWebsite());
            user1.get().setSubject(user.getSubject());
            user1.get().setDesignation(user.getDesignation());
            user1.get().setClassName(user.getClassName());
            user1.get().setSection(user.getSection());
            user1.get().setSemester(user.getSemester());
            user1.get().setBranch(user.getBranch());
            user1.get().setUpdatedTimeStamp(new Date());
            user2.set(user1.get());
            userRepo.save(user2.get());
            return "User Updated";
        }
        else {
            throw new RuntimeException("User not found");
        }
    }

    public String changePassword(String userName,String password) {//Admin can change the password
        LoginDetails loginDetails = loginDetailsRepo.findByUsername(userName);
        if (loginDetails == null) {
            throw new RuntimeException("User not found");
        } else {
            loginDetails.setPassword(passwordEncoder.encode(password));
            loginDetailsRepo.save(loginDetails);
            return "Password Changed";
        }
    }

    public List<User> filterUsers(UserFilter filter) {
        Query query = new Query();
        if(filter.getUserId()!=null){
            query.addCriteria(Criteria.where("id").is(filter.getUserId()));
        }
        if(filter.getBusinessId()!=null){
            query.addCriteria(Criteria.where("businessId").is(filter.getBusinessId()));
        }
        if(filter.getBranch()!=null){
            query.addCriteria(Criteria.where("branch").is(filter.getBranch()));
        }
        if(filter.getSemester()!=null){
            query.addCriteria(Criteria.where("semester").is(filter.getSemester()));
        }
        if(filter.getSection()!=null){
            query.addCriteria(Criteria.where("section").is(filter.getSection()));
        }
        if(filter.getClassName()!=null){
            query.addCriteria(Criteria.where("className").is(filter.getClassName()));
        }
        if(filter.getRole()!=null){
            query.addCriteria(Criteria.where("role").is(filter.getRole()));
        }
        if(filter.getDesignation()!=null){
            query.addCriteria(Criteria.where("designation").is(filter.getDesignation()));
        }
        if(filter.getDepartment()!=null){
            query.addCriteria(Criteria.where("department").is(filter.getDepartment()));
        }
        if(filter.getName()!=null){
            query.addCriteria(Criteria.where("name").regex(filter.getName()));
        }
        if(filter.getSubject()!=null){
            query.addCriteria(Criteria.where("subject").is(filter.getSubject()));
        }
        query.with(Sort.by(Sort.Direction.DESC, "createdTimeStamp"));
        if(filter.getPageNo()!=null && filter.getPageSize()!=null){
            Pageable pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize());
            query.with(pageable);
        }
        return  mongoTemplate.find(query, User.class);
    }


    public String deleteUser(UUID id){
        if(userRepo.findById(id).isPresent()) {
            String result= userRepo.findById(id).get().getName().toLowerCase();
            userRepo.deleteById(id);
            return result+" Deleted Successfully";
        }
        else {
            throw new RuntimeException("User not found");
        }
    }
}
