package com.tinqinacademy.authentication.rest.config;

import com.tinqinacademy.authentication.persistence.entities.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Setter
@Component
@RequestScope
public class UserContext {
    private User currUser;
    private String jwtToken;
}

