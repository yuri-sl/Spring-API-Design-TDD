package com.bookshop.locadora.service;

import com.bookshop.locadora.dto.CreateUserRequest;
import com.bookshop.locadora.entity.User;
import com.bookshop.locadora.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User criarUsuarioBanco(CreateUserRequest createUserRequest){
        User user = User.builder()
                .username(createUserRequest.getUsername())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .role("USER")
                .build();
        return userRepository.save(user);
    }
}
