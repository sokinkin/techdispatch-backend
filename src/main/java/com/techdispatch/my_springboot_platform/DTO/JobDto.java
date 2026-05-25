package com.techdispatch.my_springboot_platform.DTO;

import com.techdispatch.my_springboot_platform.Models.Job;

public class JobDto {

    private Long id;
    private String name;
    private String description;
    private Long visitId;


    public JobDto() {}


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public Long getVisitId() {
        return visitId;
    }


    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }

    // ----------------------------------------------------------

    public static JobDto from(Job job) {
        JobDto dto = new JobDto();
        dto.id = job.getId();
        dto.name = job.getName();
        dto.description = job.getDescription();

        if (job.getVisit() != null) {
            dto.visitId = job.getVisit().getId();
        }

        return dto;
    }

}
