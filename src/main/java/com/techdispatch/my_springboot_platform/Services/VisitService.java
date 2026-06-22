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
        Long technicianId = visit.getTechnician() != null ? visit.getTechnician().getId() : null;
        assertNoConflict(technicianId, visit.getDate(), null);
        visitRepository.save(visit);
        return getVisits();
    }

    // Reject a slot that is already taken by the same technician. Two visits
    // "double-book" when they share a technician AND the exact same date string
    // (our times are half-hour slots, so an exact match is the conflict unit).
    // ignoreVisitId lets reschedule skip the visit it is moving.
    private void assertNoConflict(Long technicianId, String date, Long ignoreVisitId) {
        if (technicianId == null || date == null || date.isBlank())
            return;
        List<Visit> existing = visitRepository.findByTechnicianId(technicianId);
        for (Visit other : existing) {
            if (ignoreVisitId != null && other.getId().equals(ignoreVisitId))
                continue;
            if (date.equals(other.getDate()))
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Technician already has a visit at " + date);
        }
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

    // Move a visit to a new date (used by the calendar drag-and-drop).
    public VisitDto rescheduleVisit(long id, String date) {
        Optional<Visit> visitOptional = visitRepository.findById(id);
        if (!visitOptional.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit not found");
        Visit visit = visitOptional.get();
        Long technicianId = visit.getTechnician() != null ? visit.getTechnician().getId() : null;
        assertNoConflict(technicianId, date, id);
        visit.setDate(date);
        visitRepository.save(visit);
        return VisitDto.from(visit);
    }

}
