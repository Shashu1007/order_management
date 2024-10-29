package com.oderiFy.app.repository;

import com.oderiFy.app.model.UserPrincipals;
import com.oderiFy.app.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {


    Users findByUserName(String userName);
}
