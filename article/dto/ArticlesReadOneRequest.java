package com.firstproject.article.dto;

import lombok.*;

/**
 * @author rua
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticlesReadOneRequest {
    Long articleId;
    String title;
    String content;
    String author;
}