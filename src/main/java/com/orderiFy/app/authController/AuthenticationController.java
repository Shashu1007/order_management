package com.orderiFy.app.authController;

import com.orderiFy.app.authService.JWTService;
import com.orderiFy.app.exception.InvalidCredentialsException;
import com.orderiFy.app.userModule.entity.Users;
import com.orderiFy.app.userModule.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private UserService service;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Users user) {
        try {
            Users registeredUser = service.register(user);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User created successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Registration failed"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Users user, HttpSession session) {
        try {
            String token = service.verify(user);

            Map<String, String> response = new HashMap<>();
            session.setAttribute("username", user.getUsername());
            response.put("token", token);
            response.put("message", "Logged in successfully");

            return ResponseEntity.ok(response);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }
    }


}
