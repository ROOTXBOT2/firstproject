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
public class ArticlesReadOneResponse {
    Long articleId;
    String title;
    String content;
    String author;

    public static ArticlesReadOneResponse fromEntity(Articles article) {
        return new ArticlesReadOneResponse(
                article.getArticleId(),
                article.getTitle(),
                article.getContent(),
                article.getUser().getNickname()
        );
    }
    // 게시글 내용 수정용 메서드
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
