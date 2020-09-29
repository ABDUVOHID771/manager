package com.example.springmanager.dao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderNumber;

    @Column(unique = true, nullable = false, updatable = false, length = 16)
    private UUID uuid;

    @PrePersist
    public void generateUUID() {
        this.uuid = UUID.fromString(UUID.randomUUID().toString().substring(2, 36));
    }

    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @CreatedDate
    private Date receivedTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "serving_time")
    private Date servingTime;

    @Column(name = "ready_time")
    private Date readyTime;

    @NotNull(message = "Directions can not be empty")
    @Enumerated(EnumType.STRING)
    private Directions directions;

    @NotNull(message = "Departments can not be empty")
    @Enumerated(EnumType.STRING)
    private Departments departments;

    @Column(name = "desk")
    private String desk;

}
