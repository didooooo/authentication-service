package com.tinqinacademy.authentication.core.utils;

import com.tinqinacademy.authentication.persistence.repositories.ConfirmationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfirmationCodeGenerator {
    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final int LENGTH = 12;

    public String generateConfirmationCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String randomCode = RandomStringUtils.random(LENGTH, characters);
        while (confirmationCodeRepository.existsByCode(randomCode)) {
            randomCode = RandomStringUtils.random(LENGTH, characters);
        }
        return randomCode;
    }
}
