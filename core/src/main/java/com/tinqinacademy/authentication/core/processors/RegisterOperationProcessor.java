package com.tinqinacademy.authentication.core.processors;

import com.tinqinacademy.authentication.api.exceptions.ErrorMapper;
import com.tinqinacademy.authentication.api.exceptions.Errors;
import com.tinqinacademy.authentication.api.exceptions.customExceptions.EmailAlreadyExists;
import com.tinqinacademy.authentication.api.exceptions.customExceptions.UsernameAlreadyExists;
import com.tinqinacademy.authentication.api.operation.register.RegisterInput;
import com.tinqinacademy.authentication.api.operation.register.RegisterOperation;
import com.tinqinacademy.authentication.api.operation.register.RegisterOutput;
import com.tinqinacademy.authentication.core.processors.base.BaseProcessor;
import com.tinqinacademy.authentication.core.utils.ConfirmationCodeGenerator;
import com.tinqinacademy.authentication.core.utils.EmailService;
import com.tinqinacademy.authentication.persistence.entities.ConfirmationCode;
import com.tinqinacademy.authentication.persistence.entities.User;
import com.tinqinacademy.authentication.persistence.repositories.ConfirmationCodeRepository;
import com.tinqinacademy.authentication.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterOperationProcessor extends BaseProcessor implements RegisterOperation {
    private final ErrorMapper errorMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationCodeGenerator confirmationCodeGenerator;
    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final EmailService emailService;

    public RegisterOperationProcessor(ConversionService conversionService, Validator validator, ErrorMapper errorMapper, UserRepository userRepository, PasswordEncoder passwordEncoder, ConfirmationCodeGenerator confirmationCodeGenerator, ConfirmationCodeRepository confirmationCodeRepository, EmailService emailService) {
        super(conversionService, validator);
        this.errorMapper = errorMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationCodeGenerator = confirmationCodeGenerator;
        this.confirmationCodeRepository = confirmationCodeRepository;
        this.emailService = emailService;
    }

    @Override
    public Either<Errors, RegisterOutput> process(RegisterInput input) {
        return Try.of(() -> {
                    validate(input);
                    checkForUsername(input);
                    checkForEmail(input);
                    User converted = conversionService.convert(input, User.class);
                    User built = buildConvertedUserWithPassword(input, converted);
                    User fromDatabase = userRepository.save(built);
                    String confirmationCode = confirmationCodeGenerator.generateConfirmationCode();
                    ConfirmationCode builtConfirmationCode = getConfirmationCode(confirmationCode, fromDatabase);
                    confirmationCodeRepository.save(builtConfirmationCode);
                    emailService.sendEmailForAccountActivation(fromDatabase.getUsername(), fromDatabase.getEmail(), confirmationCode);
                    RegisterOutput output = RegisterOutput.builder()
                            .id(fromDatabase.getUuid())
                            .build();
                    return output;
                }).toEither()
                .mapLeft(throwable -> errorMapper.mapError(throwable));
    }

    private User buildConvertedUserWithPassword(RegisterInput input, User converted) {
        User built = converted.builder()
                .password(passwordEncoder.encode(input.getPassword()))
                .build();
        return built;
    }

    private static ConfirmationCode getConfirmationCode(String confirmationCode, User fromDatabase) {
        ConfirmationCode builtConfirmationCode = ConfirmationCode.builder()
                .code(confirmationCode)
                .email(fromDatabase.getEmail())
                .build();
        return builtConfirmationCode;
    }

    private void checkForEmail(RegisterInput input) throws EmailAlreadyExists {
        Optional<User> byEmail = userRepository.findByEmail(input.getEmail());
        if (byEmail.isPresent()) throw new EmailAlreadyExists();
    }

    private void checkForUsername(RegisterInput input) throws UsernameAlreadyExists {
        Optional<User> byUsername = userRepository.findByUsername(input.getUsername());
        if (byUsername.isPresent()) throw new UsernameAlreadyExists();
    }
}
