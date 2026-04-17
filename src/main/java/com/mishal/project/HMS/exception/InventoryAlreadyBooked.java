package com.mishal.project.HMS.exception;

public class InventoryAlreadyBooked extends RuntimeException {
    public InventoryAlreadyBooked(String message) {
        super(message);
    }
}
