package com.techdispatch.my_springboot_platform.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techdispatch.my_springboot_platform.Models.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByCustomerId(long customerId);

}
