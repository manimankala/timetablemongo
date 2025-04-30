package com.timetable.TimeTableProject.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/business", produces = MediaType.APPLICATION_JSON_VALUE)
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Business createBusiness(@RequestBody Business business) {
        return businessService.saveBusiness(business);
    }

    @GetMapping(value = "/")
    public List<Business> getAllBusiness() {
        return businessService.getAll();
    }

    @DeleteMapping(value = "/")
    public String deleteBusiness(@RequestParam UUID id) {
        return businessService.deleteBusiness(id);
    }
}