package com.techdispatch.my_springboot_platform.DTO;

import com.techdispatch.my_springboot_platform.Models.Location;

public class LocationDto {

    private Long id;
    private String address;
    private String city;
    private String description;
    private Long customerId;
    private String customerName;


    public LocationDto() {}


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public String getCity() {
        return city;
    }


    public void setCity(String city) {
        this.city = city;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
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

    // ----------------------------------------------------------

    public static LocationDto from(Location location) {
        LocationDto dto = new LocationDto();
        dto.id = location.getId();
        dto.address = location.getAddress();
        dto.city = location.getCity();
        dto.description = location.getDescription();

        if (location.getCustomer() != null) {
            dto.customerId = location.getCustomer().getId();
            dto.customerName = location.getCustomer().getName();
        }

        return dto;
    }

}
