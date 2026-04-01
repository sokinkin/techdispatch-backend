package com.techdispatch.my_springboot_platform.Models;

import java.util.List;

public class Visit {
    
    private Long id;
    private String date;
    private String status;
    private String description;
    private String notes;
    private Technician technician;
    private Location location;
    private List<Service> services;
}
