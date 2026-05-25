package com.techdispatch.my_springboot_platform.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.techdispatch.my_springboot_platform.DTO.JobDto;
import com.techdispatch.my_springboot_platform.Models.Job;
import com.techdispatch.my_springboot_platform.Repositories.JobRepository;

@Service
public class JobService {

    @Autowired
    JobRepository jobRepository;

    public List<JobDto> getJobs() {
        List<Job> jobs = jobRepository.findAll();
        List<JobDto> jobDtos = new ArrayList<>();
        for (Job job : jobs) {
            jobDtos.add(JobDto.from(job));
        }
        return jobDtos;
    }

    public JobDto getJob(Long id) {
        Optional<Job> optional = jobRepository.findById(id);
        if (!optional.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found");
        return JobDto.from(optional.get());
    }

    public List<JobDto> addJob(Job job) {
        jobRepository.save(job);
        return getJobs();
    }

}
