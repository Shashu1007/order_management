package com.orderiFy.app.userModule.repository;

import com.orderiFy.app.userModule.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {


    Users findByUsername(String username);

}