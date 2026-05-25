package com.techdispatch.my_springboot_platform.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.techdispatch.my_springboot_platform.DTO.VisitDto;
import com.techdispatch.my_springboot_platform.Models.Visit;
import com.techdispatch.my_springboot_platform.Models.VisitStatus;
import com.techdispatch.my_springboot_platform.Repositories.VisitRepository;

@Service
public class VisitService {

    @Autowired
    VisitRepository visitRepository;

    public List<VisitDto> getVisits() {
        List<Visit> visits = visitRepository.findAll();
        List<VisitDto> visitDtos = new ArrayList<>();
        for (Visit visit : visits) {
            visitDtos.add(VisitDto.from(visit));
        }
        return visitDtos;
    }

    public VisitDto getVisit(Long id) {
        Optional<Visit> visitOptional = visitRepository.findById(id);
        if (!visitOptional.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit not found");
        Visit visit = visitOptional.get();
        return VisitDto.from(visit);
    }

    public List<VisitDto> addVisit(Visit visit) {
        visitRepository.save(visit);
        return getVisits();
    }

    public List<VisitDto> getVisitsByCustomer(Long customerId) {
        List<Visit> visits = visitRepository.findByLocationCustomerId(customerId);
        List<VisitDto> visitDtos = new ArrayList<>();
        for (Visit visit : visits) {
            visitDtos.add(VisitDto.from(visit));
        }
        return visitDtos;
    }

    public List<VisitDto> getVisitsByTechnician(long technicianId) {
        List<Visit> visits = visitRepository.findByTechnicianId(technicianId);
        List<VisitDto> visitDtos = new ArrayList<>();
        for (Visit visit : visits) {
            visitDtos.add(VisitDto.from(visit));
        }
        return visitDtos;
    }

    public VisitDto updateVisitStatus(long id, VisitStatus status) {
        Optional<Visit> visitOptional = visitRepository.findById(id);
        if (!visitOptional.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit not found");
        Visit visit = visitOptional.get();
        visit.setStatus(status);
        visitRepository.save(visit);
        return VisitDto.from(visit);
    }

}
