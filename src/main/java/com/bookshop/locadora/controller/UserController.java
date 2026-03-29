package com.bookshop.locadora.controller;

import com.bookshop.locadora.dto.CreateUserRequest;
import com.bookshop.locadora.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.ApplicationScope;

@RequestMapping("/user")
@AllArgsConstructor
@ApplicationScope
@RestController
public class UserController {

    final UserService userService;

    @PostMapping("/create")
    public ResponseEntity criarusuario(@RequestBody CreateUserRequest createUserRequest){
        try{
            return ResponseEntity.ok(userService.criarUsuarioBanco(createUserRequest));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }
}
