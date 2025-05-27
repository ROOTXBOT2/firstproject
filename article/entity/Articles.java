package com.firstproject.article.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

    @Column(nullable = false)
    String author;
}
