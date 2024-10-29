package com.oderiFy.app.service;

import com.oderiFy.app.model.UserPrincipals;
import com.oderiFy.app.model.Users;
import com.oderiFy.app.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{

        Users user = repo.findByUserName(userName);

        if(user == null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("user not Found");
        }


        return new UserPrincipals(user);

    }




}
