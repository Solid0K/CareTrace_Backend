package com.krishu.caretracev2.Controller;

import com.krishu.caretracev2.DTO.AlertResponse;
import com.krishu.caretracev2.Service.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alert")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping()
    public ResponseEntity<List<AlertResponse>> getPatientAlert(Authentication authentication){
        return ResponseEntity.ok(alertService.getPatientsAlerts(authentication));
    }
}
