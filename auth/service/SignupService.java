package com.firstproject.auth.service;

import com.firstproject.auth.dto.SignupForm;
import com.firstproject.auth.entity.Users;
import com.firstproject.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author rua
 */

@Service
@RequiredArgsConstructor
public class SignupService {
    private final UserRepository userRepository;

    public boolean processSignup(SignupForm form) {
        Users users = new Users(null,form.getId(),form.getPassword());

        // 중복 ID 체크
        if (userRepository.existsById(users.getId())) {
            return false;
        }
        //중복 아니면 DB 저장
        userRepository.save(users);
        return true;
    }
}
