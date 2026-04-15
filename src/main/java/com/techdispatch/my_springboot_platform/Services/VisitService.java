package com.techdispatch.my_springboot_platform.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.techdispatch.my_springboot_platform.Models.Visit;
import com.techdispatch.my_springboot_platform.Models.VisitStatus;
import com.techdispatch.my_springboot_platform.Repositories.VisitRepository;

@Service
public class VisitService {

    @Autowired
    VisitRepository visitRepository;

    public List<Visit> getVisits() {
        return visitRepository.findAll();
    }

    public Visit getVisit(Long id) {
        Optional<Visit> visitOptional = visitRepository.findById(id);
        if (!visitOptional.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit not found");
        return visitOptional.get();
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

    public Visit updateVisitStatus(long id, VisitStatus status) {
        Optional<Visit> visitOptional = visitRepository.findById(id);
        if (!visitOptional.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit not found");
        Visit visit = visitOptional.get();
        visit.setStatus(status);
        visitRepository.save(visit);
        return visit;
    }

}
