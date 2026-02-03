package com.fixipy.controller;

import com.fixipy.entity.Repairman;
import com.fixipy.service.RepairmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RepairmanController {

    @Autowired
    private RepairmanService repairmanService;

    @PostMapping("/register-repairman")
    public ResponseEntity<?> registerRepairman(@RequestBody Repairman repairman) {
        try {
            Repairman savedRepairman = repairmanService.registerRepairman(repairman);
            return ResponseEntity.ok(Map.of("status", "success", "message", "Registered successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", e.getMessage()));
        }
    }
}
