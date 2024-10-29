package com.oderiFy.app.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="userdetails")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "userName")
    private String userName;

    @Column(name = "userPassword")
    private String userPassword;

    @Column(name = "userRole")
    private String userRole;

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" +userPassword +'\''+
                ", userRole='" + userRole + '\'' +
                '}';
    }

}
