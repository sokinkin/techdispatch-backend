package com.techdispatch.my_springboot_platform.DTO;

// What we send back from register/login: the signed token plus the bits the
// frontend needs to know who is logged in and where to route them.
public class AuthResponse {

    private String token;
    private String role;
    private String email;
    private Long customerId;
    private Long technicianId;
    private String displayName;

    public AuthResponse() {}

    public AuthResponse(String token, String role, String email,
            Long customerId, Long technicianId, String displayName) {
        this.token = token;
        this.role = role;
        this.email = email;
        this.customerId = customerId;
        this.technicianId = technicianId;
        this.displayName = displayName;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getTechnicianId() {
        return technicianId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
