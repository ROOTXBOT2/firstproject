package com.firstproject.article.repository;

import com.firstproject.article.entity.Articles;
import com.firstproject.article.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author rua
 */
public interface CommentsRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByArticle(Articles article);
}
