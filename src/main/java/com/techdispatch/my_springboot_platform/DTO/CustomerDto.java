package com.techdispatch.my_springboot_platform.DTO;

import java.util.ArrayList;
import java.util.List;

import com.techdispatch.my_springboot_platform.Models.Customer;
import com.techdispatch.my_springboot_platform.Models.Location;

public class CustomerDto {

    private Long id;
    private String name;
    private String companyName;
    private String email;
    private String phoneNumber;
    private List<LocationDto> locations;


    public CustomerDto() {}


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


    public String getCompanyName() {
        return companyName;
    }


    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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


    public List<LocationDto> getLocations() {
        return locations;
    }


    public void setLocations(List<LocationDto> locations) {
        this.locations = locations;
    }

    // ----------------------------------------------------------

    public static CustomerDto from(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.id = customer.getId();
        dto.name = customer.getName();
        dto.companyName = customer.getCompanyName();
        dto.email = customer.getEmail();
        dto.phoneNumber = customer.getPhoneNumber();

        if (customer.getLocations() != null) {
            List<LocationDto> locationDtos = new ArrayList<>();
            for (Location location : customer.getLocations()) {
                locationDtos.add(LocationDto.from(location));
            }
            dto.locations = locationDtos;
        }

        return dto;
    }

}
