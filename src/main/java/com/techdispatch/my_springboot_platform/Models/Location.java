package com.techdispatch.my_springboot_platform.Models;

import java.util.List;

public class Location {

    private Long id;
    private String address;
    private String city;
    private String description;
    private Customer customer;
    private List<Visit> visits;
    
}
