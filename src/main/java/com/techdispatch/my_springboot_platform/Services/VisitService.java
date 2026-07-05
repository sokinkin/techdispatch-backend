package com.techdispatch.my_springboot_platform.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    // Update an existing visit's editable fields (used by the Edit dialog). Status
    // is left unchanged here — it moves through its own status endpoint.
    public VisitDto updateVisit(long id, Visit incoming) {
        Optional<Visit> visitOptional = visitRepository.findById(id);
        if (!visitOptional.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit not found");
        Visit visit = visitOptional.get();

        Long technicianId = incoming.getTechnician() != null && incoming.getTechnician().getId() != null
                ? incoming.getTechnician().getId()
                : (visit.getTechnician() != null ? visit.getTechnician().getId() : null);
        assertNoConflict(technicianId, incoming.getDate(), id);

        visit.setDate(incoming.getDate());
        visit.setDescription(incoming.getDescription());
        visit.setServices(incoming.getServices());
        visit.setCost(incoming.getCost());
        if (incoming.getTechnician() != null)
            visit.setTechnician(incoming.getTechnician());
        if (incoming.getLocation() != null)
            visit.setLocation(incoming.getLocation());

        visitRepository.save(visit);
        return VisitDto.from(visit);
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
            // A cancelled visit no longer occupies its slot, so it can be rebooked.
            if (other.getStatus() == VisitStatus.CANCELLED)
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

    // Date/time format used for the stored visit date, e.g. "2026-06-22 14:30".
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public VisitDto updateVisitStatus(long id, VisitStatus status) {
        Optional<Visit> visitOptional = visitRepository.findById(id);
        if (!visitOptional.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit not found");
        Visit visit = visitOptional.get();

        // A cancelled visit is final: it cannot re-enter the workflow, and a
        // visit that is already completed can no longer be cancelled.
        if (visit.getStatus() == VisitStatus.CANCELLED)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A cancelled visit cannot change status.");
        if (status == VisitStatus.CANCELLED && visit.getStatus() == VisitStatus.COMPLETED)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A completed visit cannot be cancelled.");

        // A visit may only be started on the day it is scheduled for. Starting it
        // snaps its time to the current half-hour slot (server time) so the
        // schedule reflects when the work actually began.
        if (status == VisitStatus.IN_PROGRESS) {
            if (!isScheduledForToday(visit.getDate()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "A visit can only be started on the day it is scheduled.");
            visit.setDate(currentHalfHourSlot());
        }

        visit.setStatus(status);
        visitRepository.save(visit);
        return VisitDto.from(visit);
    }

    // True if the stored date ("yyyy-MM-dd HH:mm") falls on the current server day.
    private boolean isScheduledForToday(String date) {
        if (date == null || date.length() < 10)
            return false;
        return date.substring(0, 10).equals(LocalDate.now().toString());
    }

    // The start of the current half-hour slot (server time), e.g. 14:17 -> "... 14:00".
    private String currentHalfHourSlot() {
        LocalDateTime now = LocalDateTime.now();
        int flooredMinute = now.getMinute() < 30 ? 0 : 30;
        return now.withMinute(flooredMinute).withSecond(0).withNano(0).format(DATE_FMT);
    }

    // Move a visit to a new date (used by the calendar drag-and-drop).
    public VisitDto rescheduleVisit(long id, String date) {
        Optional<Visit> visitOptional = visitRepository.findById(id);
        if (!visitOptional.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit not found");
        Visit visit = visitOptional.get();
        if (visit.getStatus() == VisitStatus.CANCELLED)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A cancelled visit cannot be rescheduled.");
        Long technicianId = visit.getTechnician() != null ? visit.getTechnician().getId() : null;
        assertNoConflict(technicianId, date, id);
        visit.setDate(date);
        visitRepository.save(visit);
        return VisitDto.from(visit);
    }

}
