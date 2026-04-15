package com.techdispatch.my_springboot_platform.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.techdispatch.my_springboot_platform.Models.Job;
import com.techdispatch.my_springboot_platform.Repositories.JobRepository;

@Service
public class JobService {

    @Autowired
    JobRepository jobRepository;

    public List<Job> getJobs() {
        return jobRepository.findAll();
    }

    public Job getJob(Long id) {
        Optional<Job> optional = jobRepository.findById(id);
        if (!optional.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found");
        return optional.get();
    }

    public List<Job> addJob(Job job) {
        jobRepository.save(job);
        return jobRepository.findAll();
    }

}
