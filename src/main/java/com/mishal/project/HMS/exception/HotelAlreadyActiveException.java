package com.mishal.project.HMS.exception;

public class HotelAlreadyActiveException extends RuntimeException {
    public HotelAlreadyActiveException(String message) {
        super(message);
    }
}
