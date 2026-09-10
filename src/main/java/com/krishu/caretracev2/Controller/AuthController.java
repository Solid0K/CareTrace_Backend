package com.krishu.caretracev2.Controller;

import com.krishu.caretracev2.DTO.LoginRequest;
import com.krishu.caretracev2.DTO.RegisterRequest;
import com.krishu.caretracev2.DTO.RegisterResponse;
import com.krishu.caretracev2.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<RegisterResponse> signUp(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.loginUser(request));
    }
}
