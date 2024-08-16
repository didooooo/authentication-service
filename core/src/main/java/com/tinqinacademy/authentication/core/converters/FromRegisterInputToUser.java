package com.tinqinacademy.authentication.core.converters;

import com.tinqinacademy.authentication.api.operation.register.RegisterInput;
import com.tinqinacademy.authentication.persistence.entities.User;
import com.tinqinacademy.authentication.persistence.enums.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FromRegisterInputToUser implements Converter<RegisterInput, User> {
    @Override
    public User convert(RegisterInput source) {
        return User.builder()
                .firstname(source.getFirstName())
                .lastname(source.getLastName())
                .role(Role.USER)
                .isConfirmed(false)
                .phoneNumber(source.getPhoneNumber())
                .birthdate(source.getBirthdate())
                .username(source.getUsername())
                .build();
    }
}
