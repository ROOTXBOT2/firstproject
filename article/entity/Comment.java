package com.firstproject.article.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author rua
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue
    Long CommentId;

    @ManyToOne(optional=false)
    @JoinColumn(name = "article_id", referencedColumnName = "articleId", nullable = false)
    private Articles article;

    @Column(nullable = false, length = 500)
    String comment;

    @Column(nullable = false)
    String author;
}
