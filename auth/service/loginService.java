package com.firstproject.auth.service;
import com.firstproject.auth.dto.LoginForm;
import com.firstproject.auth.entity.Users;
import com.firstproject.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * @author rua
 */
@Service
@RequiredArgsConstructor
public class loginService {
    private final UserRepository userRepository;

    private String id;
    private String password;
    LoginForm loginForm;

    public Boolean login(LoginForm loginForm) {
        Users users = new Users(null, loginForm.getId(), loginForm.getPassword());

        Optional<Users> optionalUser = userRepository.findById(users.getId());

        if (optionalUser.isEmpty()) {
            return false; // ID 존재하지 않음
        }
        Users user = optionalUser.get();
        return user.getPassword().equals(loginForm.getPassword());
    }
}
