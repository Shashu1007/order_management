package com.orderiFy.app.controller;

import com.orderiFy.app.exception.InvalidCredentialsException;
import com.orderiFy.app.model.Users;
import com.orderiFy.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController()
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/")
public class AuthenticationController {


    @Autowired
    private UserService service;

    @GetMapping("/home")
    public ResponseEntity<Map<String, String>> homepage() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Backend");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    public ResponseEntity<String> hi() {
        return ResponseEntity.ok("<h1>Hi.</h1>" + service.listAll().toString());
    }

    @PostMapping("/register")
    public ResponseEntity<Users> register(@RequestBody Users user) {
        Users registeredUser = service.register(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Users user) {
        try {
            String token = service.verify(user);

            // Create a response object as a Map
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "logged in successfully");

            return ResponseEntity.ok(response);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }
    }

}
