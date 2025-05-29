package com.firstproject.article.dto;

import com.firstproject.article.entity.Articles;
import lombok.*;

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
    @Setter
    boolean isAuthor;

    public static ArticlesReadOneForm fromEntity(Articles article) {
        return new ArticlesReadOneForm(
                article.getArticleId(),
                article.getTitle(),
                article.getContent(),
                article.getAuthor(),
                false
        );
    }

    // 게시글 내용 수정용 메서드
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
