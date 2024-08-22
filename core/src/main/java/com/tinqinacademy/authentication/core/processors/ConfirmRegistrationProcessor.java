package com.tinqinacademy.authentication.core.processors;

import com.tinqinacademy.authentication.api.exceptions.ErrorMapper;
import com.tinqinacademy.authentication.api.exceptions.Errors;
import com.tinqinacademy.authentication.api.exceptions.customExceptions.ConfirmationCodeDoesNotExists;
import com.tinqinacademy.authentication.api.exceptions.customExceptions.EmailNotFoundException;
import com.tinqinacademy.authentication.api.operation.confirmRegistration.ConfirmRegistrationInput;
import com.tinqinacademy.authentication.api.operation.confirmRegistration.ConfirmRegistrationOperation;
import com.tinqinacademy.authentication.api.operation.confirmRegistration.ConfirmRegistrationOutput;
import com.tinqinacademy.authentication.core.processors.base.BaseProcessor;
import com.tinqinacademy.authentication.persistence.entities.ConfirmationCode;
import com.tinqinacademy.authentication.persistence.entities.User;
import com.tinqinacademy.authentication.persistence.repositories.ConfirmationCodeRepository;
import com.tinqinacademy.authentication.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j

public class ConfirmRegistrationProcessor extends BaseProcessor implements ConfirmRegistrationOperation {

    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final UserRepository userRepository;
    private final ErrorMapper errorMapper;

    public ConfirmRegistrationProcessor(ConversionService conversionService, Validator validator, ConfirmationCodeRepository confirmationCodeRepository, UserRepository userRepository, ErrorMapper errorMapper) {
        super(conversionService, validator);
        this.confirmationCodeRepository = confirmationCodeRepository;
        this.userRepository = userRepository;
        this.errorMapper = errorMapper;
    }

    @Override
    public Either<Errors, ConfirmRegistrationOutput> process(ConfirmRegistrationInput input) {
        return Try.of(() -> {

                    log.info("Start confirm registration {}",input);

                    validate(input);
                    ConfirmationCode confirmationCode = getConfirmationCode(input);
                    User user = getUserByEmail(confirmationCode);
                    User built = user.toBuilder()
                            .isConfirmed(true)
                            .build();
                    userRepository.save(built);
                    confirmationCodeRepository.delete(confirmationCode);
                    ConfirmRegistrationOutput output = getConfirmRegistrationOutput();

                    log.info("End confirm registration {}",output);

                    return output;
                }).toEither()
                .mapLeft(throwable -> errorMapper.mapError(throwable));
    }

    private static ConfirmRegistrationOutput getConfirmRegistrationOutput() {
        ConfirmRegistrationOutput output = ConfirmRegistrationOutput.builder()
                .message("Confirmation is successful")
                .build();
        return output;
    }

    private User getUserByEmail(ConfirmationCode confirmationCode) throws EmailNotFoundException {
        User user = userRepository.findByEmail(confirmationCode.getEmail()).
                orElseThrow(() -> new EmailNotFoundException());
        return user;
    }

    private ConfirmationCode getConfirmationCode(ConfirmRegistrationInput input) throws ConfirmationCodeDoesNotExists {
        ConfirmationCode confirmationCode = confirmationCodeRepository.findByCode(input.getConfirmationCode())
                .orElseThrow(() -> new ConfirmationCodeDoesNotExists());
        return confirmationCode;
    }
}
