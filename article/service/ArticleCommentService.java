package com.firstproject.article.service;

import com.firstproject.article.dto.ArticleCommentCreateForm;
import com.firstproject.article.dto.ArticleCommentReadForm;
import com.firstproject.article.entity.ArticleComment;
import com.firstproject.article.entity.Articles;
import com.firstproject.article.repository.ArticleCommentRepository;
import com.firstproject.article.repository.ArticlesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * @author rua
 */
@Service
@RequiredArgsConstructor
public class ArticleCommentService {
    private final ArticleCommentRepository commentRepository;
    private final ArticlesRepository articlesRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentReadForm> getComments(Long articleId) {
        // 게시글이 없는 경우 404 처리
        if (!articlesRepository.existsById(articleId)) {
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다. id=" + articleId);
        }
        return commentRepository.findAllByArticle_ArticleIdOrderByCreatedAtAsc(articleId);
    }

    @Transactional
    public Long addComment(Long articleId, ArticleCommentCreateForm form, String author) {
        Articles article = articlesRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. id=" + articleId));

        ArticleComment comment = ArticleComment.of(article, form.getContent(), author);
        return commentRepository.save(comment).getCommentId();
    }

    @Transactional
    public void deleteComment(Long commentId, String author) {
        //ArticleComment comment = commentRepository.findByCommentIdAndAuthor(commentId, author);

        commentRepository.findAllByArticle_ArticleIdOrderByCreatedAtAsc(commentId);
        //commentRepository.delete(comment);
    }
}