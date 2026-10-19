package com.br.game_library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception class global which works the same way a try catch global
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception method that handles and returns the class {@link GameNotFoundException}
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

    /**
     * Exception method that handles the exceptions generics
     *
     * @param ex Uses as a search term for the exception
     * @return The {@link ProblemDetail} with {@link HttpStatus} and message error
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail handlerGenericException(Exception ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected internal error occurred. Please try again later.");
    }

    /**
     * Exception method that handles validation exceptions
     *
     * @param ex Uses as a search term for the exception
     * @return The {@link ProblemDetail} with {@link HttpStatus} and message error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "One or more fields are invalid.");
    }
}
