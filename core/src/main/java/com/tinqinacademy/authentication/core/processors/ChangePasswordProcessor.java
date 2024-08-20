package com.tinqinacademy.authentication.core.processors;

import com.tinqinacademy.authentication.api.exceptions.ErrorMapper;
import com.tinqinacademy.authentication.api.exceptions.Errors;
import com.tinqinacademy.authentication.api.exceptions.customExceptions.PasswordDoesNotMatchException;
import com.tinqinacademy.authentication.api.exceptions.customExceptions.TheNewPasswordShouldNotBeTheSame;
import com.tinqinacademy.authentication.api.exceptions.customExceptions.UserDoesNotExist;
import com.tinqinacademy.authentication.api.operation.changePassword.ChangePasswordInput;
import com.tinqinacademy.authentication.api.operation.changePassword.ChangePasswordOperation;
import com.tinqinacademy.authentication.api.operation.changePassword.ChangePasswordOutput;
import com.tinqinacademy.authentication.core.processors.base.BaseProcessor;
import com.tinqinacademy.authentication.persistence.entities.User;
import com.tinqinacademy.authentication.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChangePasswordProcessor extends BaseProcessor implements ChangePasswordOperation {
    private final ErrorMapper errorMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public ChangePasswordProcessor(ConversionService conversionService, Validator validator, ErrorMapper errorMapper, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        super(conversionService, validator);
        this.errorMapper = errorMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Either<Errors, ChangePasswordOutput> process(ChangePasswordInput input) {
        return Try.of(() -> {
                    validate(input);
                    User user = getUser(input);
                    checkThePasswords(input, user);
                    User built = buildTheUserWithTheNewPassword(input, user);
                    userRepository.save(built);
                    ChangePasswordOutput output = getChangePasswordOutput();
                    return output;
                }).toEither()
                .mapLeft(errorMapper::mapError);
    }

    private ChangePasswordOutput getChangePasswordOutput() {
        ChangePasswordOutput output = ChangePasswordOutput.builder()
                .message("Successfully changed password!")
                .build();
        return output;
    }

    private User buildTheUserWithTheNewPassword(ChangePasswordInput input, User user) {
        User built = user.toBuilder()
                .password(passwordEncoder.encode(input.getNewPassword()))
                .build();
        return built;
    }

    private void checkThePasswords(ChangePasswordInput input, User user) throws PasswordDoesNotMatchException, TheNewPasswordShouldNotBeTheSame {
        if (!passwordEncoder.matches(input.getOldPassword(),user.getPassword())) {
            throw new PasswordDoesNotMatchException();
        }
        checkIfTheNewPasswordIsTheSameAsTheOldOne(input, user);
    }

    private void checkIfTheNewPasswordIsTheSameAsTheOldOne(ChangePasswordInput input, User user) throws TheNewPasswordShouldNotBeTheSame {
        if(passwordEncoder.matches(input.getNewPassword(), user.getPassword())){
            throw new TheNewPasswordShouldNotBeTheSame();
        }
    }

    private User getUser(ChangePasswordInput input) throws UserDoesNotExist {
        User user = userRepository.findByEmail(input.getEmail()).orElseThrow(() -> new UserDoesNotExist());
        return user;
    }
}
