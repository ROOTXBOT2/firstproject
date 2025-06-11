package com.firstproject.article.service;

import com.firstproject.article.dto.ArticlesCreateRequest;
import com.firstproject.article.dto.ArticlesReadAllResponse;
import com.firstproject.article.dto.ArticlesReadOneRequest;
import com.firstproject.article.dto.ArticlesReadOneResponse;
import com.firstproject.article.entity.Articles;
import com.firstproject.article.repository.ArticlesRepository;
import com.firstproject.auth.entity.Users;
import com.firstproject.auth.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author rua
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class ArticleService {
    private final ArticlesRepository articlesRepository;
    private final UsersRepository usersRepository;

    @Transactional
    // 새 글 작성
    public void createArticle(ArticlesCreateRequest form, Long userId)  {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Articles articles = new Articles(null, form.getTitle(), form.getContent(),user);
        try {
            articlesRepository.save(articles);
        } catch (Exception e) {
            // 저장 실패 시 로그 남기기
            log.error("글 저장 중 오류 발생", e);
        }
    }

    @Transactional
    public boolean deleteArticle(Long articleId,Long userId) {
        Articles article = articlesRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        if (!article.getUser().getUserId().equals(userId)) {
            throw new SecurityException("작성자만 삭제할 수 있습니다.");
        }
        articlesRepository.deleteById(articleId);
        return true;
    }

    @Transactional(readOnly = true)
    public List<ArticlesReadAllResponse> getAllArticles() {
        List<Articles> articles = (List<Articles>) articlesRepository.findAll();

        return articles.stream()
                .map(ArticlesReadAllResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // 글 하나 조회 (Optional<DTO>)
    @Transactional(readOnly = true)
    public Optional<ArticlesReadOneResponse> GetOneAsDto(Long articleId) {
        return articlesRepository.findById(articleId)
                .map(ArticlesReadOneResponse::fromEntity);
    }

    @Transactional
    public void updateArticle(Long articleId, String title, String content, Long userId) {
        Articles article = articlesRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (!article.getUser().getUserId().equals(userId)) {
            throw new SecurityException("작성자만 수정할 수 있습니다.");
        }
        article.update(title, content); // JPA의 변경 감지 기능 사용 (Dirty Checking)
    }
}
