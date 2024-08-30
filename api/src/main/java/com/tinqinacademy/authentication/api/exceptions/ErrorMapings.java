package com.tinqinacademy.authentication.api.exceptions;

import com.tinqinacademy.authentication.api.exceptions.customExceptions.*;
import lombok.Getter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class ErrorMapings implements ApplicationRunner {
    public Map<Class<? extends Throwable>, Errors> exceptionToError;

    private void fillMap() {
        exceptionToError.put(AccountIsNotConfirmedException.class,
                new AccountIsNotConfirmedException(HttpStatus.BAD_REQUEST, "Account is not confirmed", HttpStatus.BAD_REQUEST.value()));

        exceptionToError.put(BlankStringException.class,
                new BlankStringException(HttpStatus.BAD_REQUEST, "Blank string provided", HttpStatus.BAD_REQUEST.value()));

        exceptionToError.put(CannotCreateNewPasswordException.class,
                new CannotCreateNewPasswordException(HttpStatus.BAD_REQUEST, "Cannot create new password", HttpStatus.BAD_REQUEST.value()));

        exceptionToError.put(CannotDemoteYourselfException.class,
                new CannotDemoteYourselfException(HttpStatus.BAD_REQUEST, "Cannot demote yourself", HttpStatus.BAD_REQUEST.value()));

        exceptionToError.put(CannotPromoteYourselfException.class,
                new CannotPromoteYourselfException(HttpStatus.BAD_REQUEST, "Cannot promote yourself", HttpStatus.BAD_REQUEST.value()));

        exceptionToError.put(ConfirmationCodeDoesNotExists.class,
                new ConfirmationCodeDoesNotExists(HttpStatus.BAD_REQUEST, "Confirmation code does not exist", HttpStatus.BAD_REQUEST.value()));

        exceptionToError.put(EmailAlreadyExists.class,
                new EmailAlreadyExists(HttpStatus.BAD_REQUEST, "Email already exists", HttpStatus.BAD_REQUEST.value()));

        exceptionToError.put(EmailException.class,
                new EmailException(HttpStatus.BAD_REQUEST, "Email exception occurred", HttpStatus.BAD_REQUEST.value()));

        exceptionToError.put(EmailNotFoundException.class,
                new EmailNotFoundException(HttpStatus.BAD_REQUEST, "Email not found", HttpStatus.BAD_REQUEST.value()));

        exceptionToError.put(InvalidHttpMessageNotWritable.class,
                new InvalidHttpMessageNotWritable(HttpStatus.BAD_REQUEST, "Http Message Not Writable Exception", HttpStatus.BAD_REQUEST.value()));

        exceptionToError.put(InvalidJwtException.class,
                new InvalidJwtException(HttpStatus.BAD_REQUEST, "Invalid JWT token", HttpStatus.BAD_REQUEST.value()));
        exceptionToError.put(PasswordDoesNotMatchException.class,
                new PasswordDoesNotMatchException(HttpStatus.BAD_REQUEST, "Password does not match", HttpStatus.BAD_REQUEST.value()));

        exceptionToError.put(TheNewPasswordShouldNotBeTheSame.class,
                new TheNewPasswordShouldNotBeTheSame(HttpStatus.BAD_REQUEST, "New password should not be the same as the old one", HttpStatus.BAD_REQUEST.value()));

        exceptionToError.put(UserDoesNotExist.class,
                new UserDoesNotExist(HttpStatus.BAD_REQUEST, "User does not exist", HttpStatus.BAD_REQUEST.value()));

        exceptionToError.put(UserIsAlreadyAdminException.class,
                new UserIsAlreadyAdminException(HttpStatus.BAD_REQUEST, "User is already admin", HttpStatus.BAD_REQUEST.value()));

        exceptionToError.put(UserIsNotAdminException.class,
                new UserIsNotAdminException(HttpStatus.BAD_REQUEST, "User is not an admin", HttpStatus.BAD_REQUEST.value()));

        exceptionToError.put(UserIsNotConfirmed.class,
                new UserIsNotConfirmed(HttpStatus.BAD_REQUEST, "User is not confirmed", HttpStatus.BAD_REQUEST.value()));

        exceptionToError.put(UsernameAlreadyExists.class,
                new UsernameAlreadyExists(HttpStatus.BAD_REQUEST, "Username already exists", HttpStatus.BAD_REQUEST.value()));

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        exceptionToError = new HashMap<>();
        fillMap();
    }
}
