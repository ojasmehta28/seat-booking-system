package com.seatbooking.exception;

public class BookingException extends RuntimeException { // unchecked exception and can be thrown during the normal operation of the JVM

    public BookingException(String message) {
        super(message);
    }
}