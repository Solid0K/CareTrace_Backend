package com.krishu.caretracev2.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.krishu.caretracev2.DTO.AIQueryRequest;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController 
@RequestMapping("/api/ai")


public class AIController {

    @PostMapping("/query")
    public ResponseEntity<?> query(@RequestBody AIQueryRequest request)
    {
        System.out.println("This was the patient id received "+request.getPatientId());
        System.out.println("The question was recevied: "+ request.getQuestion());
        return ResponseEntity.ok(Map.of("patientId", request.getPatientId(), "response", "This is a response to the question: " + request.getQuestion()));
    }

    
}
