package com.techdispatch.my_springboot_platform.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techdispatch.my_springboot_platform.Models.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

}
