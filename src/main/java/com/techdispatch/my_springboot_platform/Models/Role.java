package com.techdispatch.my_springboot_platform.Models;

// The two kinds of account. Decides which dashboard a user lands on and which
// entity (Customer or Technician) their login is linked to.
public enum Role {
    CUSTOMER,
    TECHNICIAN
}
