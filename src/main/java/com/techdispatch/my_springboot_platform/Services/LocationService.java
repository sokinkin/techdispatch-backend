package com.techdispatch.my_springboot_platform.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.techdispatch.my_springboot_platform.Models.Location;
import com.techdispatch.my_springboot_platform.Repositories.LocationRepository;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    public List<Location> getLocations() {
        return locationRepository.findAll();
    }

    public Location getLocation(Long id) {
        Optional<Location> locationOptional = locationRepository.findById(id);
        if(!locationOptional.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not found");
        return locationOptional.get();
    }

    public List<Location> addLocation(Location location) {
        locationRepository.save(location);
        return locationRepository.findAll();
    }

    public List<Location> getLocationsByCustomer(Long customerId) {
        return locationRepository.findByCustomerId(customerId);
    }
    
}
