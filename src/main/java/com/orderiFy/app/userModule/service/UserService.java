package com.orderiFy.app.userModule.service;

import com.orderiFy.app.exception.InvalidCredentialsException;
import com.orderiFy.app.authService.JWTService;
import com.orderiFy.app.userModule.entity.Users;
import com.orderiFy.app.userModule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JWTService jwtService;


    @Autowired
    AuthenticationManager authManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public Users register(Users user){
        user.setUsername(user.getUsername());
        user.setUserPassword(encoder.encode(user.getUserPassword()));
        return userRepo.save(user);

    }

    public List<Users> listAll(){
        return userRepo.findAll();
    }


    public String verify(Users user) {
        try {
            // Authenticate the user with provided username and password
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getUserPassword())
            );

            // If authentication is successful, generate and return the JWT token
            if (auth.isAuthenticated()) {
                return jwtService.generateToken(user.getUsername());
            }
        } catch (BadCredentialsException e) {
            // Throw custom exception for invalid credentials
            throw new InvalidCredentialsException("Invalid username or password", e);
        } catch (Exception e) {
            // Log other exceptions and rethrow as a generic exception or handle accordingly
            e.printStackTrace();
            throw new RuntimeException("Authentication failed due to an unexpected error.", e);
        }

        // If we reach here, something went wrong
        throw new InvalidCredentialsException("Authentication failed for an unknown reason.");
    }





}
