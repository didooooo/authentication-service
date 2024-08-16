package com.tinqinacademy.authentication.core.utils;

import com.tinqinacademy.authentication.api.exceptions.customExceptions.CannotCreateNewPasswordException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class RandomPasswordGenerator {
    private final int LENGTH = 10;

    public String generateRandomPassword() throws CannotCreateNewPasswordException {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%^&+=_*~!)(./:;?{}|`',-";
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(regex);
        int counter = 0;
        while (counter < 50) {
            counter++;
            String generatedPassword = RandomStringUtils.random(LENGTH, characters);
            Matcher matcher = pattern.matcher(generatedPassword);
            if (matcher.matches()) {
                return generatedPassword;
            }
        }
        throw new CannotCreateNewPasswordException();
    }
}
