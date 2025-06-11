package com.firstproject.auth.repository;

import com.firstproject.auth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author rua
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findById(String id); // id: 로그인용 ID
    boolean existsById(String id);      // 중복 체크용
}
