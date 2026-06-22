package com.techdispatch.my_springboot_platform.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techdispatch.my_springboot_platform.DTO.AvailabilityDto;
import com.techdispatch.my_springboot_platform.Models.Availability;
import com.techdispatch.my_springboot_platform.Services.AvailabilityService;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    AvailabilityService availabilityService;

    // GET EVERY AVAILABILITY SLOT OF A TECHNICIAN
    @GetMapping("/by-technician")
    public List<AvailabilityDto> getByTechnician(@RequestParam Long technicianId) {
        return availabilityService.getByTechnician(technicianId);
    }

    // ADD AN AVAILABILITY SLOT FOR A TECHNICIAN
    @PostMapping("/for-technician")
    public List<AvailabilityDto> addForTechnician(@RequestParam Long technicianId,
            @RequestBody Availability availability) {
        return availabilityService.addForTechnician(technicianId, availability);
    }

    // DELETE AN AVAILABILITY SLOT
    @DeleteMapping()
    public void deleteAvailability(@RequestParam Long id) {
        availabilityService.deleteAvailability(id);
    }

}
