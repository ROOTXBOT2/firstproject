package com.firstproject.article.repository;

import com.firstproject.article.entity.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author rua
 */
@Repository
public interface ArticlesRepository extends JpaRepository<Articles, Long> {
}
