package com.school.school_api.controller;

import com.school.school_api.dto.AdminRegisterRequest;
import com.school.school_api.dto.AdminLoginRequest;
import com.school.school_api.dto.AuthResponse;
import com.school.school_api.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody AdminRegisterRequest request) {
        adminService.registerAdmin(request.getUsername(), request.getPassword());
        return ResponseEntity.ok("Admin registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AdminLoginRequest request) {
        String token = adminService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
