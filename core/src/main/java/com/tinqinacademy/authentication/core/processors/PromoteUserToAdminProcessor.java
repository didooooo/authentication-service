package com.tinqinacademy.authentication.core.processors;

import com.tinqinacademy.authentication.api.exceptions.ErrorMapper;
import com.tinqinacademy.authentication.api.exceptions.Errors;
import com.tinqinacademy.authentication.api.exceptions.customExceptions.CannotPromoteYourselfException;
import com.tinqinacademy.authentication.api.exceptions.customExceptions.UserDoesNotExist;
import com.tinqinacademy.authentication.api.exceptions.customExceptions.UserIsAlreadyAdminException;
import com.tinqinacademy.authentication.api.operation.promoteUserToAdmin.PromoteUserToAdminInput;
import com.tinqinacademy.authentication.api.operation.promoteUserToAdmin.PromoteUserToAdminOperation;
import com.tinqinacademy.authentication.api.operation.promoteUserToAdmin.PromoteUserToAdminOutput;
import com.tinqinacademy.authentication.core.processors.base.BaseProcessor;
import com.tinqinacademy.authentication.persistence.entities.User;
import com.tinqinacademy.authentication.persistence.enums.Role;
import com.tinqinacademy.authentication.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PromoteUserToAdminProcessor extends BaseProcessor implements PromoteUserToAdminOperation {
    private final UserRepository userRepository;
    private final ErrorMapper errorMapper;

    public PromoteUserToAdminProcessor(ConversionService conversionService, Validator validator, UserRepository userRepository, ErrorMapper errorMapper) {
        super(conversionService, validator);
        this.userRepository = userRepository;
        this.errorMapper = errorMapper;
    }

    @Override
    public Either<Errors, PromoteUserToAdminOutput> process(PromoteUserToAdminInput input) {
        return Try.of(() -> {
                    log.info("Start promote user to admin {}",input);
                    validate(input);
                    checkIfSomebodyIsTryingToCrashTheSystem(input);
                    User user = userRepository.findById(input.getUserId()).orElseThrow(() -> new UserDoesNotExist());
                    checkTheRole(user);
                    user.setRole(Role.ADMIN);
                    userRepository.save(user);
<<<<<<< Updated upstream
                    PromoteUserToAdminOutput output = PromoteUserToAdminOutput.builder()
                            .message("Successfully promoted user!")
                            .build();
=======
                    PromoteUserToAdminOutput output = getPromoteUserToAdminOutput();
                    log.info("End promote user to admin {}",output);
>>>>>>> Stashed changes
                    return output;
                }).toEither()
                .mapLeft(throwable -> errorMapper.mapError(throwable));
    }

    private void checkTheRole(User user) throws UserIsAlreadyAdminException {
        if (user.getRole().equals(Role.ADMIN)) throw new UserIsAlreadyAdminException();
    }

    private void checkIfSomebodyIsTryingToCrashTheSystem(PromoteUserToAdminInput input) throws CannotPromoteYourselfException {
        if (input.getUserId().equals(input.getUserContextId())) {
            throw new CannotPromoteYourselfException();
        }
    }
}
