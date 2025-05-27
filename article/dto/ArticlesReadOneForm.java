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
public class ArticlesReadOneForm {
    Long articleId;
    String title;
    String content;
    String author;

    public static ArticlesReadOneForm fromEntity(Articles article) {
        return new ArticlesReadOneForm(
                article.getArticleId(),
                article.getTitle(),
                article.getContent(),
                article.getAuthor()
        );
    }
    // 게시글 내용 수정용 메서드
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
