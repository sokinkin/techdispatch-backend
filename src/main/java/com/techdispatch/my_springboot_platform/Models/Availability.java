package com.techdispatch.my_springboot_platform.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Availability {

    @Id
    @GeneratedValue
    private Long id;
    private String dayOfWeek;  // e.g. "MONDAY"
    private String startTime;  // e.g. "09:00"
    private String endTime;    // e.g. "17:00"
    @ManyToOne
    @JoinColumn(name = "technician_id", referencedColumnName = "id")
    private Technician technician;

    public Availability() {}

    public Availability(String dayOfWeek, String startTime, String endTime, Technician technician) {
        super();
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.technician = technician;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Technician getTechnician() {
        return technician;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

}
