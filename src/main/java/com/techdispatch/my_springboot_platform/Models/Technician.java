package com.techdispatch.my_springboot_platform.Models;

import java.util.List;

public class Technician {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private List<Visit> visits;
    
}
