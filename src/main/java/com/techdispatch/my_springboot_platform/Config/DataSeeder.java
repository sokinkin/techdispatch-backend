package com.techdispatch.my_springboot_platform.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.techdispatch.my_springboot_platform.Models.Account;
import com.techdispatch.my_springboot_platform.Models.Customer;
import com.techdispatch.my_springboot_platform.Models.Role;
import com.techdispatch.my_springboot_platform.Models.Technician;
import com.techdispatch.my_springboot_platform.Repositories.AccountRepository;
import com.techdispatch.my_springboot_platform.Repositories.CustomerRepository;
import com.techdispatch.my_springboot_platform.Repositories.TechnicianRepository;

// On startup, give every pre-existing customer/technician that has no login yet
// a BCrypt-hashed account (using the record's own email), so data created before
// auth existed stays reachable. Idempotent: anyone who already has an account
// for their email is skipped, so it is safe to run on every boot.
@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TechnicianRepository technicianRepository;
    private final PasswordEncoder passwordEncoder;
    private final String defaultPassword;

    public DataSeeder(AccountRepository accountRepository,
            CustomerRepository customerRepository,
            TechnicianRepository technicianRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.seed.default-password:password123}") String defaultPassword) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.technicianRepository = technicianRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultPassword = defaultPassword;
    }

    @Override
    public void run(String... args) {
        int seeded = 0;
        for (Customer customer : customerRepository.findAll()) {
            if (seedAccount(customer.getEmail(), Role.CUSTOMER, customer.getId(), null)) seeded++;
        }
        for (Technician technician : technicianRepository.findAll()) {
            if (seedAccount(technician.getEmail(), Role.TECHNICIAN, null, technician.getId())) seeded++;
        }
        if (seeded == 0) {
            log.info("DataSeeder: every customer/technician already has a login — nothing to seed.");
        } else {
            log.info("DataSeeder: created {} login(s) with default password \"{}\".", seeded, defaultPassword);
        }
    }

    private boolean seedAccount(String rawEmail, Role role, Long customerId, Long technicianId) {
        if (rawEmail == null || rawEmail.isBlank()) return false;
        String email = rawEmail.trim().toLowerCase();
        if (accountRepository.existsByEmail(email)) return false;

        Account account = new Account(email, passwordEncoder.encode(defaultPassword), role);
        account.setCustomerId(customerId);
        account.setTechnicianId(technicianId);
        accountRepository.save(account);
        log.info("DataSeeder: seeded {} login for {}", role, email);
        return true;
    }
}
