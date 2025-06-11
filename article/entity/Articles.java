package com.firstproject.article.entity;

import com.firstproject.auth.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author rua
 */
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Articles {
    @Id
    @GeneratedValue
    Long articleId;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String content;

    @ManyToOne(optional = false)  // N : 1 관계 (다수의 Article → 하나의 User)
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private Users user;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
