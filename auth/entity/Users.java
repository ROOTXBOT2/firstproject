package com.firstproject.auth.entity;

import jakarta.persistence.*;
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

    // 생성 시 자동으로 시간 설정
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 엔티티 생성 직전에 시간 설정
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
