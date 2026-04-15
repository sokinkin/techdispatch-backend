package com.techdispatch.my_springboot_platform.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techdispatch.my_springboot_platform.Models.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {


    List<Visit> findByLocationCustomerId(long customerId);

    List<Visit> findByTechnicianId(long technicianId);
}
