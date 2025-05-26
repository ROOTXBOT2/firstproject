package com.firstproject.auth.service;

import com.firstproject.auth.dto.ArticlesReadForm;
import com.firstproject.auth.entity.Articles;
import com.firstproject.auth.repository.ArticlesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rua
 */
@Service
@RequiredArgsConstructor
public class ArticleRead {
    private final ArticlesRepository articlesRepository;

    public List<ArticlesReadForm> getArticles() {
        List<Articles> articles = (List<Articles>) articlesRepository.findAll();

        return articles.stream()
                .map(ArticlesReadForm::fromEntity)
                .collect(Collectors.toList());
    }

}
