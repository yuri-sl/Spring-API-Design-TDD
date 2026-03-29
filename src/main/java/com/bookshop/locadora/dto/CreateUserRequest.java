package com.bookshop.locadora.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.annotation.ApplicationScope;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApplicationScope
public class CreateUserRequest {
    private String username;
    private String password;
    private String role;
}