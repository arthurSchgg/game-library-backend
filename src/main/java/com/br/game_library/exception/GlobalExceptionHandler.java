package com.br.game_library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception classs global which works the same way a try catch global
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception method that maps and returns the class {@link GameNotFoundException}
     * with the {@link ProblemDetail}
     *
     * @param game Uses as a search term for the exception
     * @return The {@link ProblemDetail} with {@link HttpStatus} and message error
     */
    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail handlerGameNotFoundException(GameNotFoundException game) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, game.getMessage());
    }
}
