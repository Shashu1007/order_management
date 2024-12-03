package com.orderiFy.app.authController;

import com.orderiFy.app.exception.InvalidCredentialsException;
import com.orderiFy.app.userModule.entity.Users;
import com.orderiFy.app.userModule.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController()
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/")
public class AuthenticationController {

    @Autowired
    private UserService service;

 /*   @GetMapping("/home")
    public ResponseEntity<Map<String, String>> homepage() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Backend");
        return ResponseEntity.ok(response);
    }*/

  /*  @GetMapping("/test")
    public ResponseEntity<String> hi() {
        return ResponseEntity.ok("<h1>Hi.</h1>" + service.listAll().toString());
    }
*/
    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> register(@RequestBody Users user) {
        try {

            Map<String,String> response = new HashMap<>();
            Users registeredUser = service.register(user);
            response.put("User", "Created Successfully");
            return  ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error","Registration Failed"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Users user ,HttpSession session) {
        try {
            String token = service.verify(user);

            Map<String, String> response = new HashMap<>();
            session.setAttribute("username",user.getUsername());
            response.put("token", token);
            response.put("message", "logged in successfully");

            return ResponseEntity.ok(response);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }
    }
}
