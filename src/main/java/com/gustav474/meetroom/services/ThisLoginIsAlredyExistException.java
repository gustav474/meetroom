package com.gustav474.meetroom.services;

public class ThisLoginIsAlredyExistException extends Exception {
    public ThisLoginIsAlredyExistException(String message) {
        super(message);
    }
}
