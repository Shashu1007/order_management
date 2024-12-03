package com.orderiFy.app.userModule.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orderiFy.app.enums.Enums;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name", nullable = false, unique = true)
    private String username;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private Enums.UserRole role;



    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false)
    private Enums.UserStatus userStatus;


    @Column(name="is_deleted")
    private Boolean isDeleted;


    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by", nullable = false)
    private int createdBy;

    @Column(name = "updated_by")
    private int updatedBy;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.role == null) {
            this.role = Enums.UserRole.USER; // Default to USER if not set
        }
        if (this.userStatus == null) {
            this.userStatus = Enums.UserStatus.INACTIVE; // Default to INACTIVE if not set
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();


    }
}

