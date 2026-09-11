package com.krishu.caretracev2.Controller;

import com.krishu.caretracev2.DTO.AIQueryRequest;
import com.krishu.caretracev2.DTO.PatientContext;
import com.krishu.caretracev2.Service.AIContextService;
import com.krishu.caretracev2.Service.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final AIContextService aiContextService;
    private final GeminiService geminiService;

    public AIController(AIContextService aiContextService, GeminiService geminiService) {
        this.aiContextService = aiContextService;
        this.geminiService = geminiService;
    }

    @PostMapping("/query")
    public ResponseEntity<?> query(@RequestBody AIQueryRequest request) {
        // 1. Retrieve the patient's personal and medical context from MongoDB
        PatientContext patientContext = aiContextService.getPatientContext(request.getPatientId());

        // 2. Build a dementia-friendly, personalized prompt for the LLM
        String prompt = aiContextService.buildPrompt(patientContext, request.getQuestion());

        // 3. Send the prompt to Google Gemini to get an empathetic answer
        String aiResponse = geminiService.generateResponse(prompt);

        // 4. Return the structured response
        return ResponseEntity.ok(
                Map.of(
                        "patientId", request.getPatientId(),
                        "question", request.getQuestion(),
                        "context", patientContext,
                        "prompt", prompt,
                        "response", aiResponse
                )
        );
    }
}