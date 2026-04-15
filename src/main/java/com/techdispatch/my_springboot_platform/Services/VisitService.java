package com.techdispatch.my_springboot_platform.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.techdispatch.my_springboot_platform.Models.Visit;
import com.techdispatch.my_springboot_platform.Repositories.VisitRepository;

@Service
public class VisitService {

    @Autowired
    VisitRepository visitRepository;

    public List<Visit> getVisits() {
        return visitRepository.findAll();
    }

    public Visit getVisit(Long id) {
        Optional<Visit> optional = visitRepository.findById(id);
        if (!optional.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit not found");
        return optional.get();
    }

    public List<Visit> addVisit(Visit visit) {
        visitRepository.save(visit);
        return visitRepository.findAll();
    }

    public List<Visit> getVisitsByCustomer(Long customerId) {
        return visitRepository.findByLocationCustomerId(customerId);
    }

    public List<Visit> getVisitsByTechnician(long technicianId) {
        return visitRepository.findByTechnicianId(technicianId);
    }

}
