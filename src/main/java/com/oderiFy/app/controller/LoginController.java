package com.oderiFy.app.controller;

import com.oderiFy.app.exception.InvalidCredentialsException;
import com.oderiFy.app.model.Users;
import com.oderiFy.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService service;

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
    public ResponseEntity<String> login(@RequestBody Users user) {
        logger.info("Attempting to login user: {}", user.getUserName());
        try {
            String token = service.verify(user);
            return ResponseEntity.ok(token);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
