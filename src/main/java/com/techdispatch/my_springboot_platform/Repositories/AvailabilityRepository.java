package com.techdispatch.my_springboot_platform.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techdispatch.my_springboot_platform.Models.Availability;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    List<Availability> findByTechnicianId(Long technicianId);

}
