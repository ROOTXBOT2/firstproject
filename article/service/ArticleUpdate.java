package com.firstproject.article.service;

import com.firstproject.article.entity.Articles;
import com.firstproject.article.repository.ArticlesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author rua
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class ArticleUpdate {
    private final ArticlesRepository articlesRepository;

    @Transactional
    public void EditArticle(Long articleId, String title, String content, String sessionUsername) {
        Articles article = articlesRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (!article.getAuthor().equals(sessionUsername)) {
            throw new SecurityException("작성자만 수정할 수 있습니다.");
        }
        article.update(title, content); // JPA의 변경 감지 기능 사용 (Dirty Checking)
    }
}
