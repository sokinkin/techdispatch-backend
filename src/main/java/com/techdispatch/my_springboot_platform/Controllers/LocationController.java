package com.techdispatch.my_springboot_platform.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.techdispatch.my_springboot_platform.Models.Location;
import com.techdispatch.my_springboot_platform.Services.LocationService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    LocationService locationService;

    @GetMapping("/all")
    public List<Location> getLocations() {
        return locationService.getLocations();
    }

    @GetMapping()
    public Location getLocation(@RequestParam Long id) {
        return locationService.getLocation(id);
    }

    @PostMapping()
    public List<Location> addLocation(@RequestBody Location location) {
        return locationService.addLocation(location);
    }

    // ------------------------- EXTRA ENDPOINTS (TO DO) -------------------------

    // GET EVERY LOCATION OF A CUSTOMER
    @GetMapping("/by-customer")
    public List<Location> getLocationsByCustomer(@RequestParam Long customerId) {
        return locationService.getLocationsByCustomer(customerId);
    }


}
