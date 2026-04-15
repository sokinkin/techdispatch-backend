package com.techdispatch.my_springboot_platform.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techdispatch.my_springboot_platform.Models.Visit;
import com.techdispatch.my_springboot_platform.Models.VisitStatus;
import com.techdispatch.my_springboot_platform.Services.VisitService;

@RestController
@RequestMapping("/visit")
public class VisitController {

    @Autowired
    VisitService visitService;

    @GetMapping("/all")
    public List<Visit> getVisits() {
        return visitService.getVisits();
    }

    @GetMapping()
    public Visit getVisit(@RequestParam Long id) {
        return visitService.getVisit(id);
    }

    @PostMapping()
    public List<Visit> addVisit(@RequestBody Visit visit) {
        return visitService.addVisit(visit);
    }

    // ------------------------- EXTRA ENDPOINTS (TO DO) -------------------------

    // GET EVERY VISIT OF A CUSTOMER 
    @GetMapping("/by-customer")
    public List<Visit> getVisitsByCustomer(@RequestParam Long customerId) {
        return visitService.getVisitsByCustomer(customerId);
    }
    
    // GET EVERY VISIT OF A TECHNICIAN
    @GetMapping("/by-technician")
    public List<Visit> getVisitsByTechnician(@RequestParam long technicianId) {
        return visitService.getVisitsByTechnician(technicianId);
    }

    // UPDATE THE STATUS OF A VISIT
    @PutMapping("/status")
    public Visit updateVisitStatus(@RequestParam long id, @RequestParam VisitStatus status) {
        return visitService.updateVisitStatus(id, status);
    }

}
