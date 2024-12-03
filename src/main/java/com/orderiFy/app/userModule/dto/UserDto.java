package com.orderiFy.app.userModule.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.Transient;

@ToString
@Builder
@Data
public class UserDto {


    private Long userId;
    private String username;
    private String userPassword;
    private String user;
    private String userStatus;
    private String is_deleted;




}
