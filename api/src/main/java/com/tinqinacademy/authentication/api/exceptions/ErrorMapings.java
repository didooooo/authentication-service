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
    private void fillMap(){
       exceptionToError.put(BlankStringException.class, new BlankStringException(HttpStatus.BAD_REQUEST,"Blank String",HttpStatus.BAD_REQUEST.value()));
       exceptionToError.put(HttpMessageNotWritableException.class, new InvalidHttpMessageNotWritable(HttpStatus.BAD_REQUEST,"Http Message Not Writable Exception",HttpStatus.BAD_REQUEST.value()));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        exceptionToError = new HashMap<>();
        fillMap();
    }
}
