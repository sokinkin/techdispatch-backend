package com.techdispatch.my_springboot_platform.DTO;

// Incoming body for POST /auth/register. The relevant name fields depend on the
// role: a CUSTOMER uses name/companyName, a TECHNICIAN uses firstName/lastName.
public class RegisterRequest {

    private String role; // "CUSTOMER" or "TECHNICIAN"
    private String email;
    private String password;
    private String phoneNumber;

    private String name;        // customer
    private String companyName; // customer
    private String firstName;   // technician
    private String lastName;    // technician

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
