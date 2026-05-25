package com.techdispatch.my_springboot_platform.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.techdispatch.my_springboot_platform.DTO.LocationDto;
import com.techdispatch.my_springboot_platform.Models.Location;
import com.techdispatch.my_springboot_platform.Repositories.LocationRepository;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    public List<LocationDto> getLocations() {
        List<Location> locations = locationRepository.findAll();
        List<LocationDto> locationDtos = new ArrayList<>();
        for (Location location : locations) {
            locationDtos.add(LocationDto.from(location));
        }
        return locationDtos;
    }

    public LocationDto getLocation(Long id) {
        Optional<Location> locationOptional = locationRepository.findById(id);
        if(!locationOptional.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not found");
        return LocationDto.from(locationOptional.get());
    }

    public List<LocationDto> addLocation(Location location) {
        locationRepository.save(location);
        return getLocations();
    }

    public List<LocationDto> getLocationsByCustomer(Long customerId) {
        List<Location> locations = locationRepository.findByCustomerId(customerId);
        List<LocationDto> locationDtos = new ArrayList<>();
        for (Location location : locations) {
            locationDtos.add(LocationDto.from(location));
        }
        return locationDtos;
    }

}
