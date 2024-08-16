package com.tinqinacademy.authentication.core.config;

import com.tinqinacademy.authentication.persistence.entities.User;
import com.tinqinacademy.authentication.persistence.enums.Role;
import com.tinqinacademy.authentication.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ApplicationUserDetailsProcessor implements UserDetailsService {
    private final UserRepository userRepository;
    public ApplicationUserDetailsProcessor(UserRepository userRepository1) {
        this.userRepository = userRepository1;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(foundUser.getUsername(),
                foundUser.getPassword(),
                Set.of(new SimpleGrantedAuthority(Role.USER.toString())));
    }


}
