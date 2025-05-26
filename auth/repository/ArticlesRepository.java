package com.firstproject.auth.repository;

import com.firstproject.auth.entity.Articles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author rua
 */
@Repository
public interface ArticlesRepository extends CrudRepository<Articles, Long> {

}
