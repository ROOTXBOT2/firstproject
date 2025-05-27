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
public class ArticlesReadForm {
    Long articleId;
    String title;
    String author;

    public static ArticlesReadForm fromEntity(Articles article) {
        return new ArticlesReadForm(
                article.getArticleId(),
                article.getTitle(),
                article.getAuthor()
        );
    }
}
