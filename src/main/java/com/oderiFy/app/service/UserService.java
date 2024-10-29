package com.oderiFy.app.service;

import com.oderiFy.app.exception.InvalidCredentialsException;
import com.oderiFy.app.model.Users;
import com.oderiFy.app.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private JWTService jwtService;


    @Autowired
    AuthenticationManager authmanager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public Users register(Users user){
       user.setUserPassword(encoder.encode(user.getUserPassword()));
       return repo.save(user);

    }

    public List<Users> listAll(){
        return repo.findAll();
    }


    public String verify(Users user) {
        try {
            // Authenticate the user with provided username and password
            Authentication auth = authmanager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getUserPassword())
            );

            // If authentication is successful, generate and return the JWT token
            if (auth.isAuthenticated()) {
                return jwtService.generateToken(user.getUserName());
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
