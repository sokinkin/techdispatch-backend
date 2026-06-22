package com.techdispatch.my_springboot_platform.DTO;

// Incoming body for PUT /account/password. The user is identified from their
// JWT, so only the passwords are sent.
public class ChangePasswordRequest {

    private String oldPassword;
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
