package com.techdispatch.my_springboot_platform.Security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Runs once per request: if there is a valid "Authorization: Bearer <jwt>"
// header, it puts the user into the Spring Security context so the request is
// treated as authenticated. No header / bad token => request stays anonymous.
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Claims claims = jwtService.parse(token);
                String email = claims.getSubject();
                String role = claims.get("role", String.class);
                if (email != null && role != null
                        && SecurityContextHolder.getContext().getAuthentication() == null) {
                    var authority = new SimpleGrantedAuthority("ROLE_" + role);
                    var authentication = new UsernamePasswordAuthenticationToken(
                            email, null, List.of(authority));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // Invalid or expired token: leave the request unauthenticated.
            }
        }

        filterChain.doFilter(request, response);
    }
}
