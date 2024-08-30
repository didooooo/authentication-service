package com.tinqinacademy.authentication.core.processors;

import com.tinqinacademy.authentication.api.exceptions.ErrorMapper;
import com.tinqinacademy.authentication.api.exceptions.Errors;
import com.tinqinacademy.authentication.api.exceptions.customExceptions.*;
import com.tinqinacademy.authentication.api.operation.confirmRegistration.ConfirmRegistrationInput;
import com.tinqinacademy.authentication.api.operation.demoteAdmin.DemoteAdminInput;
import com.tinqinacademy.authentication.api.operation.demoteAdmin.DemoteAdminOperation;
import com.tinqinacademy.authentication.api.operation.demoteAdmin.DemoteAdminOutput;
import com.tinqinacademy.authentication.api.operation.promoteUserToAdmin.PromoteUserToAdminInput;
import com.tinqinacademy.authentication.api.operation.promoteUserToAdmin.PromoteUserToAdminOutput;
import com.tinqinacademy.authentication.core.processors.base.BaseProcessor;
import com.tinqinacademy.authentication.persistence.entities.ConfirmationCode;
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
public class DemoteAdminProcessor extends BaseProcessor implements DemoteAdminOperation {
    private final ErrorMapper errorMapper;
    private final UserRepository userRepository;

    public DemoteAdminProcessor(ConversionService conversionService, Validator validator, ErrorMapper errorMapper, UserRepository userRepository) {
        super(conversionService, validator);
        this.errorMapper = errorMapper;
        this.userRepository = userRepository;
    }

    @Override
    public Either<Errors, DemoteAdminOutput> process(DemoteAdminInput input) {
        return Try.of(() -> {

                    log.info("Start demote admin {}",input);
                    validate(input);
                    checkIfSomebodyIsTryingToCrashTheSystem(input);
                    User user = userRepository.findById(input.getUserId()).orElseThrow(() -> new UserDoesNotExist());
                    checkTheRole(user);
                    User built = user.toBuilder()
                            .role(Role.USER)
                            .build();
                    userRepository.save(built);

                    DemoteAdminOutput output = getDemoteAdminOutput();
                    log.info("End demote admin {}",output);

                    return output;
                }).toEither()
                .mapLeft(errorMapper::mapError);
    }

    private  DemoteAdminOutput getDemoteAdminOutput() {
        DemoteAdminOutput output = DemoteAdminOutput.builder()
                .message("Successfully demoted user!")
                .build();
        return output;
    }

    private void checkTheRole(User user) throws UserIsNotAdminException {
        if (user.getRole().equals(Role.USER)) throw new UserIsNotAdminException();
    }

    private void checkIfSomebodyIsTryingToCrashTheSystem(DemoteAdminInput input) throws CannotDemoteYourselfException {
        if (input.getUserId().equals(input.getUserContextId())) {
            throw new CannotDemoteYourselfException();
        }
    }

}
