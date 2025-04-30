package com.timetable.TimeTableProject.user;

import com.timetable.TimeTableProject.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response createStaff(@RequestBody User user) {
        return new Response(userService.createUser(user));
    }
    @PutMapping(value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response filterUser(@RequestBody UserFilter filter) {
        return new Response(userService.filterUsers(filter));
    }
    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response updateUser(@RequestBody User user) {
        return new Response(userService.updateUser(user));
    }
    @PutMapping(value = "/change-password/admin")
    public Response changePassword(@RequestParam String email, @RequestParam String password) {
        return new Response(userService.changePassword(email, password));
    }
    @DeleteMapping(value = "")
    public Response deleteUser(@RequestParam UUID id) {
        return new Response(userService.deleteUser(id));
    }
}

