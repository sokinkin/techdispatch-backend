package com.techdispatch.my_springboot_platform.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techdispatch.my_springboot_platform.DTO.JobDto;
import com.techdispatch.my_springboot_platform.Models.Job;
import com.techdispatch.my_springboot_platform.Services.JobService;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    JobService jobService;

    @GetMapping("/all")
    public List<JobDto> getJobs() {
        return jobService.getJobs();
    }

    @GetMapping()
    public JobDto getJob(@RequestParam Long id) {
        return jobService.getJob(id);
    }

    @PostMapping()
    public List<JobDto> addJob(@RequestBody Job job) {
        return jobService.addJob(job);
    }

    // ------------------------- EXTRA ENDPOINTS (TO DO) -------------------------

}
