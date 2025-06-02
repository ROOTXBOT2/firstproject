package com.firstproject.article.dto;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * @author rua
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCommentCreateForm {
    @NotBlank(message = "댓글 내용은 반드시 입력해야 합니다.")
    private String content;
}