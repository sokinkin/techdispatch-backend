package com.techdispatch.my_springboot_platform.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techdispatch.my_springboot_platform.DTO.CustomerDto;
import com.techdispatch.my_springboot_platform.Models.Customer;
import com.techdispatch.my_springboot_platform.Services.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/all")
    public List<CustomerDto> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping()
    public CustomerDto getCustomer(@RequestParam Long id) {
        return customerService.getCustomer(id);
    }

    @PostMapping()
    public List<CustomerDto> addCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    // UPDATE A CUSTOMER'S PROFILE
    @PutMapping()
    public CustomerDto updateCustomer(@RequestParam Long id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    // ------------------------- EXTRA ENDPOINTS (TO DO) -------------------------

}
