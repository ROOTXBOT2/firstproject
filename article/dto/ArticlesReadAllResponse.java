package com.firstproject.article.dto;

import com.firstproject.article.entity.Articles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author rua
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticlesReadAllResponse {
    Long articleId;
    String title;
    String author;

    public static ArticlesReadAllResponse fromEntity(Articles article) {
        return new ArticlesReadAllResponse(
                article.getArticleId(),
                article.getTitle(),
                article.getUser().getNickname()
        );
    }
}
