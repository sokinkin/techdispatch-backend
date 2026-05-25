package com.techdispatch.my_springboot_platform.DTO;

import com.techdispatch.my_springboot_platform.Models.Technician;

public class TechnicianDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;


    public TechnicianDto() {}


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // ----------------------------------------------------------

    public static TechnicianDto from(Technician technician) {
        TechnicianDto dto = new TechnicianDto();
        dto.id = technician.getId();
        dto.firstName = technician.getFirstName();
        dto.lastName = technician.getLastName();
        dto.email = technician.getEmail();
        dto.phoneNumber = technician.getPhoneNumber();
        return dto;
    }

}
