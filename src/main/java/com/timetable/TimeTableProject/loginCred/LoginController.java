package com.timetable.TimeTableProject.loginCred;

import com.timetable.TimeTableProject.common.CustomException;
import com.timetable.TimeTableProject.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/login")
public class LoginController {
    private final LoginDetailsRepo loginDetailsRepo;

    private static final String secretKey = "89yjhabvdhjavsdjabsduyasdguyasdutaudyAhvvhvvhjvhjvhgchg";
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(LoginDetailsRepo loginDetailsRepo) {
        this.loginDetailsRepo = loginDetailsRepo;
    }

   @PostMapping(value = "/validate")
   public Response validateLogin(@RequestBody LoginDetails loginDetails) {
       Response response = new Response();
       LoginDetails ld = loginDetailsRepo.findByUsername(loginDetails.getUsername());
       if (ld != null) {
           if (passwordEncoder.matches(loginDetails.getPassword(), ld.getPassword())) {
               Map<String, String> data = new HashMap<>();
               data.put("Message", "Login Successful");
               data.put("userId", ld.getUserId().toString());
               response.setData(data);
           } else {
               throw new CustomException("Invalid Password", 1000);
           }
       } else {
           throw new CustomException("Invalid Username", 1001);
       }
       return response;
   }

    @GetMapping
    public Response getAllLoginDetails() {
        return new Response(loginDetailsRepo.findAll());
    }
}