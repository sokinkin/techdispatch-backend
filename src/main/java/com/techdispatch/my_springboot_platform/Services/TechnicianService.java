package com.techdispatch.my_springboot_platform.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.techdispatch.my_springboot_platform.DTO.TechnicianDto;
import com.techdispatch.my_springboot_platform.Models.Technician;
import com.techdispatch.my_springboot_platform.Repositories.TechnicianRepository;

@Service
public class TechnicianService {

    @Autowired
    TechnicianRepository technicianRepository;

    public List<TechnicianDto> getTechnicians() {
        List<Technician> technicians = technicianRepository.findAll();
        List<TechnicianDto> technicianDtos = new ArrayList<>();
        for (Technician technician : technicians) {
            technicianDtos.add(TechnicianDto.from(technician));
        }
        return technicianDtos;
    }

    public TechnicianDto getTechnician(Long id) {
        Optional<Technician> optional = technicianRepository.findById(id);
        if (!optional.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Technician not found");
        return TechnicianDto.from(optional.get());
    }

    public List<TechnicianDto> addTechnician(Technician technician) {
        technicianRepository.save(technician);
        return getTechnicians();
    }

}
