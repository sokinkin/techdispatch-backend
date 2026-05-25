package com.techdispatch.my_springboot_platform.DTO;

import java.util.stream.Collectors;
import java.util.List;

import com.techdispatch.my_springboot_platform.Models.Visit;
import com.techdispatch.my_springboot_platform.Models.VisitStatus;

public class VisitDto {
    
    private Long id;
    private String date;
    private VisitStatus status;
    private String description;
    private String notes;

    private Long technicianId;
    private String technicianName;
    private Long locationId;
    private String locationAddress;
    private Long customerId;
    private String customerName;
    private List<String> jobNames;


    public VisitDto() {}


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }


    public VisitStatus getStatus() {
        return status;
    }


    public void setStatus(VisitStatus status) {
        this.status = status;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getNotes() {
        return notes;
    }


    public void setNotes(String notes) {
        this.notes = notes;
    }


    public Long getTechnicianId() {
        return technicianId;
    }


    public void setTechnicianId(Long technicianId) {
        this.technicianId = technicianId;
    }


    public String getTechnicianName() {
        return technicianName;
    }


    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }


    public Long getLocationId() {
        return locationId;
    }


    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }


    public String getLocationAddress() {
        return locationAddress;
    }


    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }


    public Long getCustomerId() {
        return customerId;
    }


    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }


    public String getCustomerName() {
        return customerName;
    }


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public List<String> getJobNames() {
        return jobNames;
    }


    public void setJobNames(List<String> jobNames) {
        this.jobNames = jobNames;
    }

    // ----------------------------------------------------------

    public static VisitDto from(Visit visit) {
        VisitDto dto = new VisitDto();
        dto.id = visit.getId();
        dto.date = visit.getDate();
        dto.status = visit.getStatus();
        dto.description = visit.getDescription();
        dto.notes = visit.getNotes();
        
        if (visit.getTechnician() != null) {
            dto.technicianId = visit.getTechnician().getId();
            dto.technicianName = visit.getTechnician().getFirstName() 
                + " " + visit.getTechnician().getLastName();
        }

        if (visit.getLocation() != null) {
            dto.locationId = visit.getLocation().getId();
            dto.locationAddress = visit.getLocation().getAddress();
            if (visit.getLocation().getCustomer() != null) {
                dto.customerId = visit.getLocation().getCustomer().getId();
                dto.customerName = visit.getLocation().getCustomer().getName();
            }
        }

        if (visit.getJobs() != null) {
            dto.jobNames = visit.getJobs().stream()
                .map(job -> job.getName())
                .collect(Collectors.toList());
        }


        return dto;
    }

}
