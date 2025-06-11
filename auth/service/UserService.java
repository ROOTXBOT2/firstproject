package com.firstproject.auth.service;

import com.firstproject.auth.dto.LoginRequestDto;
import com.firstproject.auth.dto.SignupRequestDto;
import com.firstproject.auth.entity.Users;
import com.firstproject.auth.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author rua
 */

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;

    public boolean idCheck(String id){
        return usersRepository.existsById(id);
    }

    @Transactional
    public boolean register(SignupRequestDto form) {
        Users user = Users.builder()
                .id(form.getId())
                .nickname(form.getNickname())
                .password(form.getPassword())
                .build();
        // 중복 ID 체크
        if (usersRepository.existsById(user.getId())) {
            return false;
        }
        //중복 아니면 DB 저장
        usersRepository.save(user);
        return true;
    }

    /** 로그인 인증 로직 **/
    public Users authenticate(LoginRequestDto req) {
        // 1) 아이디로 유저 조회
        Users user = usersRepository.findById(req.getId())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));
        // 2) 비밀번호 매칭
        if (req.getPassword().equals(user.getPassword())) {
            return user;
        }
        throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
    }
}
