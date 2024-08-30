package com.tinqinacademy.authentication.rest.interceptors;

import com.tinqinacademy.authentication.api.mappings.URLMapping;
import com.tinqinacademy.authentication.core.security.JwtService;
import com.tinqinacademy.authentication.persistence.entities.User;
import com.tinqinacademy.authentication.persistence.enums.Role;
import com.tinqinacademy.authentication.rest.config.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Interceptor implements HandlerInterceptor {
    private final JwtService jwtService;
    private final UserContext userContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        String jwtToken = header.substring(7);
        Optional<User> user = jwtService.validateJwtAndReturnUser(jwtToken);
        if (user.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        if (request.getRequestURI().equals(URLMapping.PROMOTE) ||
                request.getRequestURI().equals(URLMapping.DEMOTE)) {
            if (!user.get().getRole().toString().equals(Role.ADMIN.toString())) {
                response.sendError(403, HttpStatus.FORBIDDEN.getReasonPhrase());
                return false;
            }
        }
        userContext.setCurrUser(user.get());
        userContext.setJwtToken(jwtToken);
        return true;
    }
}
