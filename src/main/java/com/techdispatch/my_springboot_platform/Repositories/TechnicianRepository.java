package com.techdispatch.my_springboot_platform.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techdispatch.my_springboot_platform.Models.Technician;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Long> {
    
}
