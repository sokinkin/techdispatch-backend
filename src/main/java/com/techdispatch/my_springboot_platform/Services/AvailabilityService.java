package com.techdispatch.my_springboot_platform.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.techdispatch.my_springboot_platform.DTO.AvailabilityDto;
import com.techdispatch.my_springboot_platform.Models.Availability;
import com.techdispatch.my_springboot_platform.Models.Technician;
import com.techdispatch.my_springboot_platform.Models.Visit;
import com.techdispatch.my_springboot_platform.Repositories.AvailabilityRepository;
import com.techdispatch.my_springboot_platform.Repositories.TechnicianRepository;
import com.techdispatch.my_springboot_platform.Repositories.VisitRepository;

@Service
public class AvailabilityService {

    @Autowired
    AvailabilityRepository availabilityRepository;

    @Autowired
    TechnicianRepository technicianRepository;

    @Autowired
    VisitRepository visitRepository;

    // Availability may only be set inside the working-day window 08:00-20:00.
    private static final String DAY_START = "08:00";
    private static final String DAY_END = "20:00";

    // Matches a visit date like "2026-05-27 14:30" (time optional).
    private static final Pattern VISIT_DATE = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})(?:[ T](\\d{2}:\\d{2}))?");

    public List<AvailabilityDto> getByTechnician(Long technicianId) {
        List<Availability> list = availabilityRepository.findByTechnicianId(technicianId);
        List<AvailabilityDto> dtos = new ArrayList<>();
        for (Availability availability : list) {
            dtos.add(AvailabilityDto.from(availability));
        }
        return dtos;
    }

    // Add an availability slot for a technician (load the technician, link, save).
    public List<AvailabilityDto> addForTechnician(Long technicianId, Availability availability) {
        Optional<Technician> technicianOptional = technicianRepository.findById(technicianId);
        if (!technicianOptional.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Technician not found");
        validateWindow(availability.getStartTime(), availability.getEndTime());
        availability.setTechnician(technicianOptional.get());
        availabilityRepository.save(availability);
        return getByTechnician(technicianId);
    }

    public void deleteAvailability(Long id) {
        Optional<Availability> slotOptional = availabilityRepository.findById(id);
        if (!slotOptional.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Availability not found");
        Availability slot = slotOptional.get();
        if (hasVisitInWindow(slot))
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Cannot remove availability that still has scheduled visits.");
        availabilityRepository.deleteById(id);
    }

    // Reject hours outside 08:00-20:00, or a start that is not before the end.
    // Times are zero-padded "HH:mm", so string comparison matches clock order.
    private void validateWindow(String start, String end) {
        if (start == null || end == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start and end time are required.");
        if (start.compareTo(DAY_START) < 0 || end.compareTo(DAY_END) > 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Working hours must be between " + DAY_START + " and " + DAY_END + ".");
        if (start.compareTo(end) >= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start time must be before end time.");
    }

    // True if the technician has any visit that falls inside this slot's window
    // (same weekday, and — if the visit has a time — within [start, end)).
    private boolean hasVisitInWindow(Availability slot) {
        if (slot.getTechnician() == null)
            return false;
        List<Visit> visits = visitRepository.findByTechnicianId(slot.getTechnician().getId());
        for (Visit visit : visits) {
            Matcher m = VISIT_DATE.matcher(visit.getDate() == null ? "" : visit.getDate());
            if (!m.find())
                continue;
            String dayOfWeek;
            try {
                dayOfWeek = LocalDate.parse(m.group(1)).getDayOfWeek().name(); // "MONDAY"
            } catch (Exception e) {
                continue;
            }
            if (!dayOfWeek.equals(slot.getDayOfWeek()))
                continue;
            String time = m.group(2);
            // All-day visit on this weekday, or a timed visit inside the window.
            if (time == null
                    || (time.compareTo(slot.getStartTime()) >= 0 && time.compareTo(slot.getEndTime()) < 0))
                return true;
        }
        return false;
    }

}
