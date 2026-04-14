package com.techdispatch.my_springboot_platform.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.techdispatch.my_springboot_platform.Models.Customer;
import com.techdispatch.my_springboot_platform.Repositories.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (!customer.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        return customer.get();
    }

    public List<Customer> addCustomer(Customer customer) {
        customerRepository.save(customer);
        return customerRepository.findAll();
    }

}
