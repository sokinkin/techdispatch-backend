package com.techdispatch.my_springboot_platform.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techdispatch.my_springboot_platform.DTO.TechnicianDto;
import com.techdispatch.my_springboot_platform.Models.Technician;
import com.techdispatch.my_springboot_platform.Services.TechnicianService;

@RestController
@RequestMapping("/technician")
public class TechnicianController {

    @Autowired
    TechnicianService technicianService;

    @GetMapping("/all")
    public List<TechnicianDto> getTechnicians() {
        return technicianService.getTechnicians();
    }

    @GetMapping()
    public TechnicianDto getTechnician(@RequestParam Long id) {
        return technicianService.getTechnician(id);
    }

    @PostMapping()
    public List<TechnicianDto> addTechnician(@RequestBody Technician technician) {
        return technicianService.addTechnician(technician);
    }

    // ------------------------- EXTRA ENDPOINTS (TO DO) -------------------------

}
