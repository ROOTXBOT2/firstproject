package com.firstproject.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
 /**
 * @author rua
 */
@Getter

@AllArgsConstructor
@NoArgsConstructor
public class ArticleCommentResponse {
    private Long commentId;
    private String comment;
    private String author;

}