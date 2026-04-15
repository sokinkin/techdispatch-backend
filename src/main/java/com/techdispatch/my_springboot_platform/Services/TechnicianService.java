package com.techdispatch.my_springboot_platform.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.techdispatch.my_springboot_platform.Models.Technician;
import com.techdispatch.my_springboot_platform.Repositories.TechnicianRepository;

@Service
public class TechnicianService {

    @Autowired
    TechnicianRepository technicianRepository;

    public List<Technician> getTechnicians() {
        return technicianRepository.findAll();
    }

    public Technician getTechnician(Long id) {
        Optional<Technician> optional = technicianRepository.findById(id);
        if (!optional.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Technician not found");
        return optional.get();
    }

    public List<Technician> addTechnician(Technician technician) {
        technicianRepository.save(technician);
        return technicianRepository.findAll();
    }

}
