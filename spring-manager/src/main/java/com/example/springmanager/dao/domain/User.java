package com.example.springmanager.dao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false, length = 16)
    private UUID uuid;

    @Email(message = "Should be as a email@gmail.com")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Transient
    private String confirmPassword;

    @NotBlank(message = "Username is required")
    @Column(unique = true)
    private String username;

    private String authorities;

    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @CreatedDate
    private Date createdDate;

    @Column(name = "desk")
    @NotBlank(message = "Staff desk number is required")
    private String desk;

    @Column(name = "direction")
    @NotNull(message = "User directions can not be empty")
    @Enumerated(EnumType.STRING)
    private Directions direction;

    @PrePersist
    public void generateUUID() {
        this.uuid = UUID.fromString(UUID.randomUUID().toString().substring(2, 36));
    }

}
