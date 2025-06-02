package com.firstproject.article.dto;

import java.time.LocalDateTime;

/**
 * @author rua
 */

public interface ArticleCommentReadForm {
    Long getCommentId();
    String getContent();
    String getAuthor();
    LocalDateTime getCreatedAt();
}