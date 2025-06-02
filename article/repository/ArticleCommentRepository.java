package com.firstproject.article.repository;

import com.firstproject.article.dto.ArticleCommentReadForm;
import com.firstproject.article.entity.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author rua
 */
@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
    List<ArticleCommentReadForm> findAllByArticle_ArticleIdOrderByCreatedAtAsc(Long articleId);
    Optional<ArticleComment> findByCommentIdAndAuthor(Long commentId, String author);
}
