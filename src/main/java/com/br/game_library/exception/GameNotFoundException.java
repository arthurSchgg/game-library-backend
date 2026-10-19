package com.br.game_library.exception;

/**
 * Throws an exception if the selected game is not found
 */
public class GameNotFoundException extends RuntimeException {

    /**
     * Exception method whole throw the exception game not found
     *
     * @param message Message error
     */
    public GameNotFoundException(String message) {
        super(message);
    }
}
