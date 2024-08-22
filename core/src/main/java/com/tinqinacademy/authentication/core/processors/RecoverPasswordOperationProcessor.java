package com.tinqinacademy.authentication.core.processors;

import com.tinqinacademy.authentication.api.exceptions.ErrorMapper;
import com.tinqinacademy.authentication.api.exceptions.Errors;
import com.tinqinacademy.authentication.api.exceptions.customExceptions.UserDoesNotExist;
import com.tinqinacademy.authentication.api.operation.recoverPassword.RecoverPasswordInput;
import com.tinqinacademy.authentication.api.operation.recoverPassword.RecoverPasswordOperation;
import com.tinqinacademy.authentication.api.operation.recoverPassword.RecoverPasswordOutput;
import com.tinqinacademy.authentication.core.processors.base.BaseProcessor;
import com.tinqinacademy.authentication.core.utils.EmailService;
import com.tinqinacademy.authentication.core.utils.RandomPasswordGenerator;
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
public class RecoverPasswordOperationProcessor extends BaseProcessor implements RecoverPasswordOperation {
    private final UserRepository userRepository;
    private final RandomPasswordGenerator randomPasswordGenerator;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ErrorMapper errorMapper;

    public RecoverPasswordOperationProcessor(ConversionService conversionService, Validator validator, UserRepository userRepository, RandomPasswordGenerator randomPasswordGenerator, PasswordEncoder passwordEncoder, EmailService emailService, ErrorMapper errorMapper) {
        super(conversionService, validator);
        this.userRepository = userRepository;
        this.randomPasswordGenerator = randomPasswordGenerator;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.errorMapper = errorMapper;
    }

    @Override
    public Either<Errors, RecoverPasswordOutput> process(RecoverPasswordInput input) {
        return Try.of(() -> {
                    log.info("Start recover password {}",input);
                    validate(input);
                    User user = getUserIfExists(input);
                    String randomPassword = randomPasswordGenerator.generateRandomPassword();
                    String encodedPassword = passwordEncoder.encode(randomPassword);
                    User built = buildUserWithNewPassword(user, encodedPassword);
                    userRepository.save(built);
                    emailService.sendEmailWithNewPassword(built.getFirstname(), built.getEmail(), encodedPassword);
                    RecoverPasswordOutput output = buildOutput();
                    log.info("End recover password {}",output);
                    return output;
                }).toEither()
                .mapLeft(throwable -> errorMapper.mapError(throwable));
    }

    private RecoverPasswordOutput buildOutput() {
        RecoverPasswordOutput output = RecoverPasswordOutput
                .builder()
                .message("Successfully recovered password")
                .build();
        return output;
    }

    private User buildUserWithNewPassword(User user, String encodedPassword) {
        User built = user.builder()
                .password(encodedPassword)
                .build();
        return built;
    }

    private User getUserIfExists(RecoverPasswordInput input) throws UserDoesNotExist {
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UserDoesNotExist());
        return user;
    }
}
