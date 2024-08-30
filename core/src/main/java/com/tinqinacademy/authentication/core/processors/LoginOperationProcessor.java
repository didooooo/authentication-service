package com.tinqinacademy.authentication.core.processors;

import com.tinqinacademy.authentication.api.exceptions.ErrorMapper;
import com.tinqinacademy.authentication.api.exceptions.Errors;
import com.tinqinacademy.authentication.api.exceptions.customExceptions.AccountIsNotConfirmedException;
import com.tinqinacademy.authentication.api.exceptions.customExceptions.PasswordDoesNotMatchException;
import com.tinqinacademy.authentication.api.exceptions.customExceptions.UserDoesNotExist;
import com.tinqinacademy.authentication.api.operation.login.LoginInput;
import com.tinqinacademy.authentication.api.operation.login.LoginOperation;
import com.tinqinacademy.authentication.api.operation.login.LoginOutput;
import com.tinqinacademy.authentication.core.processors.base.BaseProcessor;
import com.tinqinacademy.authentication.core.security.JwtService;
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
public class LoginOperationProcessor extends BaseProcessor implements LoginOperation {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ErrorMapper errorMapper;

    public LoginOperationProcessor(ConversionService conversionService, Validator validator, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, ErrorMapper errorMapper){// JwtService jwtService, ErrorMapper errorMapper) {
        super(conversionService, validator);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        //  this.jwtService = jwtService;
        this.errorMapper = errorMapper;
    }

    @Override
    public Either<Errors, LoginOutput> process(LoginInput input) {
        return Try.of(() -> {
                    log.info("Start login {}",input);
                    validate(input);
                    User user = getUserIfExists(input);
                    checkForPassword(input, user);
                    checkForIfThisAccountIsConfirmed(user);
                    String generatedToken = jwtService.generateToken(user);
                    LoginOutput output = LoginOutput
                            .builder()
                            .jwt(generatedToken)
                            .build();
                    log.info("End login {}",output);
                    return output;
                }).toEither()
                .mapLeft(throwable -> errorMapper.mapError(throwable));
    }

    private LoginOutput getLoginOutput() {
        LoginOutput output = LoginOutput
                .builder()
                //.jwt(generatedToken)
                .build();
        return output;
    }

    private void checkForIfThisAccountIsConfirmed(User user) throws AccountIsNotConfirmedException {
        if (!user.isConfirmed())
            throw new AccountIsNotConfirmedException();
    }

    private void checkForPassword(LoginInput input, User user) throws PasswordDoesNotMatchException {
        if (!passwordEncoder.matches(input.getPassword(), user.getPassword())) {
            throw new PasswordDoesNotMatchException();
        }
    }

    private User getUserIfExists(LoginInput input) throws UserDoesNotExist {
        User user = userRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> new UserDoesNotExist());
        return user;
    }
}
