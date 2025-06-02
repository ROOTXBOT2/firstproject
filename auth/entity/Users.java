package com.firstproject.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author rua
 */

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue
    Long userId;

    @Column(unique = true, nullable = false)
    String id;

    @Column(unique = false, nullable = false, length = 20)
    String nickname;

    @Column(unique = false, nullable = false)
    String password;

    @Column
    LocalDateTime createdAt = LocalDateTime.now();; //가입 시각
}
