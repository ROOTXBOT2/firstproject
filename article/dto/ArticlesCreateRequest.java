package com.firstproject.article.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author rua
 */
@Getter
@Setter
public class ArticlesCreateRequest {
    private String title;
    private String content;
}
