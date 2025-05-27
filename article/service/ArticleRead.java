package com.firstproject.article.service;

import com.firstproject.article.dto.ArticlesReadForm;
import com.firstproject.article.dto.ArticlesReadOneForm;
import com.firstproject.article.entity.Articles;
import com.firstproject.article.repository.ArticlesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Optional<ArticlesReadOneForm> getArticleById(Long articleId) {
        Optional<Articles> articleOptional = articlesRepository.findById(articleId);
        return articleOptional.map(ArticlesReadOneForm::fromEntity);
    }
}
