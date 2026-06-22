package com.techdispatch.my_springboot_platform.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.techdispatch.my_springboot_platform.DTO.CustomerDto;
import com.techdispatch.my_springboot_platform.Models.Customer;
import com.techdispatch.my_springboot_platform.Repositories.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<CustomerDto> getCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customer : customers) {
            customerDtos.add(CustomerDto.from(customer));
        }
        return customerDtos;
    }

    public CustomerDto getCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (!customer.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        return CustomerDto.from(customer.get());
    }

    public List<CustomerDto> addCustomer(Customer customer) {
        customerRepository.save(customer);
        return getCustomers();
    }

    // Update a customer's own profile fields (used by the Settings screen).
    public CustomerDto updateCustomer(Long id, Customer data) {
        Optional<Customer> optional = customerRepository.findById(id);
        if (!optional.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        Customer customer = optional.get();
        customer.setName(data.getName());
        customer.setCompanyName(data.getCompanyName());
        customer.setEmail(data.getEmail());
        customer.setPhoneNumber(data.getPhoneNumber());
        customerRepository.save(customer);
        return CustomerDto.from(customer);
    }

}
