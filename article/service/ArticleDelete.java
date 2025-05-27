package com.firstproject.article.service;

import com.firstproject.article.dto.ArticlesReadOneForm;
import com.firstproject.article.entity.Articles;
import com.firstproject.article.repository.ArticlesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * @author rua
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class ArticleDelete {
    private final ArticlesRepository articlesRepository;

    public void DeleteArticle(ArticlesReadOneForm form) {
        Articles article = new Articles(form.getArticleId(),form.getTitle(), form.getContent(),form.getAuthor());
        try {
            articlesRepository.delete(article);
        } catch (Exception e) {
                // 저장 실패 시 로그 남기기
                log.error("글 삭제 중 오류 발생", e);
            }
    }
}
