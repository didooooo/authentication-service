package com.tinqinacademy.authentication.api.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Errors extends Throwable{
    private HttpStatus status;
    private String message;
    private Integer statusCode;

}
