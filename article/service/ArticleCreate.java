package com.firstproject.article.service;

import com.firstproject.article.dto.ArticlesCreateForm;
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
public class ArticleCreate {
    private final ArticlesRepository articlesRepository;

    public void CreateArticle(ArticlesCreateForm form, String author) {
        Articles articles = new Articles(null, form.getTitle(), form.getContent(),author);
        try {
            Articles saved = articlesRepository.save(articles);
        } catch (Exception e) {
            // 저장 실패 시 로그 남기기
            log.error("글 저장 중 오류 발생", e);
        }
    }

}
