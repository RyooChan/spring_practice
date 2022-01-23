package com.board.board.service;

import com.board.board.domain.Role2;
import com.board.board.domain.User2;
import com.board.board.repository.UserRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository2 userRepository;

    private final PasswordEncoder passwordEncoder;

    public void save(User2 user){
        // password를 먼저 encoding해준다.
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        // 이후 유저의 정보들을 하나씩 set해준다.
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        Role2 role = new Role2();  // 일반유저 1L
        role.setId(1L);
        user.getRoles().add(role);

        userRepository.save(user);
    }

    public boolean checkEmailDup(String email){
        return userRepository.existsByUsername(email);
    }

    public boolean checkNicknameDup(String nickname){
        return userRepository.existsByNickname(nickname);
    }

}
