package com.firstproject.article.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author rua
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ArticleComment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_id", nullable = false)
    private Articles article;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    public static ArticleComment of(Articles article, String content, String author) {
        return ArticleComment.builder()
                .article(article)
                .content(content)
                .author(author)
                .build();
    }
}
