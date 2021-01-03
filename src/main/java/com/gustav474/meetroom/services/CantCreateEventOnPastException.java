package com.gustav474.meetroom.services;

public class CantCreateEventOnPastException extends Exception{
    public CantCreateEventOnPastException (String errorMessage) {
        super (errorMessage);
    }
}


