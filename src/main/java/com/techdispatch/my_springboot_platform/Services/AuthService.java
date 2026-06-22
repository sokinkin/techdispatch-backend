package com.techdispatch.my_springboot_platform.Services;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.techdispatch.my_springboot_platform.DTO.AuthResponse;
import com.techdispatch.my_springboot_platform.DTO.LoginRequest;
import com.techdispatch.my_springboot_platform.DTO.RegisterRequest;
import com.techdispatch.my_springboot_platform.Models.Account;
import com.techdispatch.my_springboot_platform.Models.Customer;
import com.techdispatch.my_springboot_platform.Models.Role;
import com.techdispatch.my_springboot_platform.Models.Technician;
import com.techdispatch.my_springboot_platform.Repositories.AccountRepository;
import com.techdispatch.my_springboot_platform.Repositories.CustomerRepository;
import com.techdispatch.my_springboot_platform.Repositories.TechnicianRepository;
import com.techdispatch.my_springboot_platform.Security.JwtService;

// Handles registration and login. Passwords are stored only as BCrypt hashes;
// a successful call returns a signed JWT plus the info the frontend needs.
@Service
public class AuthService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TechnicianRepository technicianRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AccountRepository accountRepository,
            CustomerRepository customerRepository,
            TechnicianRepository technicianRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.technicianRepository = technicianRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest req) {
        if (isBlank(req.getEmail()) || isBlank(req.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and password are required.");

        Role role;
        try {
            role = Role.valueOf(String.valueOf(req.getRole()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role must be CUSTOMER or TECHNICIAN.");
        }

        String email = req.getEmail().trim().toLowerCase();
        if (accountRepository.existsByEmail(email))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An account with this email already exists.");

        Account account = new Account(email, passwordEncoder.encode(req.getPassword()), role);
        String displayName;

        if (role == Role.CUSTOMER) {
            Customer customer = new Customer();
            customer.setName(req.getName());
            customer.setCompanyName(req.getCompanyName());
            customer.setEmail(email);
            customer.setPhoneNumber(req.getPhoneNumber());
            customerRepository.save(customer);
            account.setCustomerId(customer.getId());
            displayName = req.getName();
        } else {
            Technician technician = new Technician();
            technician.setFirstName(req.getFirstName());
            technician.setLastName(req.getLastName());
            technician.setEmail(email);
            technician.setPhoneNumber(req.getPhoneNumber());
            technicianRepository.save(technician);
            account.setTechnicianId(technician.getId());
            displayName = trimToEmpty(req.getFirstName() + " " + req.getLastName());
        }

        accountRepository.save(account);
        return buildResponse(account, displayName);
    }

    public AuthResponse login(LoginRequest req) {
        String email = isBlank(req.getEmail()) ? "" : req.getEmail().trim().toLowerCase();
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password."));
        if (req.getPassword() == null || !passwordEncoder.matches(req.getPassword(), account.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password.");
        return buildResponse(account, displayNameFor(account));
    }

    // Change the logged-in user's password. The old password must match before
    // the new one is accepted. 403 = wrong current password, 400 = bad new one.
    public void changePassword(String email, String oldPassword, String newPassword) {
        if (newPassword == null || newPassword.length() < 6)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password must be at least 6 characters.");

        String normalized = isBlank(email) ? "" : email.trim().toLowerCase();
        Account account = accountRepository.findByEmail(normalized)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not logged in."));

        if (oldPassword == null || !passwordEncoder.matches(oldPassword, account.getPassword()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Current password is incorrect.");

        account.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(account);
    }

    private String displayNameFor(Account account) {
        if (account.getRole() == Role.CUSTOMER && account.getCustomerId() != null) {
            return customerRepository.findById(account.getCustomerId())
                    .map(Customer::getName).orElse(account.getEmail());
        }
        if (account.getRole() == Role.TECHNICIAN && account.getTechnicianId() != null) {
            return technicianRepository.findById(account.getTechnicianId())
                    .map(t -> trimToEmpty(t.getFirstName() + " " + t.getLastName()))
                    .orElse(account.getEmail());
        }
        return account.getEmail();
    }

    private AuthResponse buildResponse(Account account, String displayName) {
        String token = jwtService.generateToken(account);
        return new AuthResponse(token, account.getRole().name(), account.getEmail(),
                account.getCustomerId(), account.getTechnicianId(), displayName);
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    private String trimToEmpty(String s) {
        return s == null ? "" : s.trim();
    }
}
