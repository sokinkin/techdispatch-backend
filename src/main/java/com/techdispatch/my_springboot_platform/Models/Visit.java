package com.techdispatch.my_springboot_platform.Models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Visit {
    
    @Id
    @GeneratedValue
    private Long id;
    private String date;
    @Enumerated(EnumType.STRING)
    private VisitStatus status;
    private String description;
    private String notes;
    @ManyToOne
    @JoinColumn(name = "technician_id", referencedColumnName = "id")
    private Technician technician;
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
    @OneToMany(mappedBy = "visit")
    private List<Job> jobs;

    public Visit() {}

    public Visit(String date, VisitStatus status, String description, String notes, Technician technician,
            Location location) {
        super();
        this.date = date;
        this.status = status;
        this.description = description;
        this.notes = notes;
        this.technician = technician;
        this.location = location;
    }

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

    public Technician getTechnician() {
        return technician;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    
}
