package com.fixipy.controller;

import com.fixipy.entity.Application;
import com.fixipy.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/join-team")
    public ResponseEntity<?> joinTeam(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("role") String role,
            @RequestParam("reason") String reason,
            @RequestParam("resume") MultipartFile resume) {
        try {
            Application application = applicationService.submitApplication(name, email, phone, role, reason, resume);
            return ResponseEntity.ok(Map.of("status", "success", "message", "Application received"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", e.getMessage()));
        }
    }
}
