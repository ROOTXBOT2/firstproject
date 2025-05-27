package com.firstproject.article.repository;

import com.firstproject.article.entity.Articles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author rua
 */
@Repository
public interface ArticlesRepository extends CrudRepository<Articles, Long> {

}
