package com.krishu.caretracev2.Controller;

import com.krishu.caretracev2.DTO.AlertResponse;
import com.krishu.caretracev2.Service.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alert")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping("/getPatientAslert")
    public ResponseEntity<List<AlertResponse>> getPatientAlert(Authentication authentication){
        return ResponseEntity.ok(alertService.getPatientsAlerts(authentication));
    }

    @PostMapping("/sos")
    public ResponseEntity<AlertResponse> sendSoS(Authentication authentication){
        return ResponseEntity.ok(alertService.createSoSAlert(authentication));
    }

    @PutMapping("/resolveSoS/{alertId}")
    public ResponseEntity<AlertResponse> resolveSoSAlert(@PathVariable String alertId,Authentication authentication){
        return ResponseEntity.ok(alertService.ResolveSoSAlert(alertId,authentication));
    }
}
