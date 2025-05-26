package com.firstproject.auth.dto;

import com.firstproject.auth.entity.Articles;
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
public class ArticlesReadOneForm {
    Long articleId;
    String title;
    String author;
    String content;

    public static ArticlesReadOneForm fromEntity(Articles article) {
        return new ArticlesReadOneForm(
                article.getArticleId(),
                article.getTitle(),
                article.getContent(),
                article.getAuthor()
        );
    }
}
