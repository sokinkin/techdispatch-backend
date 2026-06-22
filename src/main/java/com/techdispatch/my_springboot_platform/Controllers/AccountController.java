package com.techdispatch.my_springboot_platform.Controllers;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techdispatch.my_springboot_platform.DTO.ChangePasswordRequest;
import com.techdispatch.my_springboot_platform.Services.AuthService;

// Authenticated account actions. The current user comes from the JWT (Principal),
// so these endpoints sit behind the security filter — no token, no access.
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AuthService authService;

    public AccountController(AuthService authService) {
        this.authService = authService;
    }

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(Principal principal, @RequestBody ChangePasswordRequest request) {
        authService.changePassword(principal.getName(), request.getOldPassword(), request.getNewPassword());
    }
}
