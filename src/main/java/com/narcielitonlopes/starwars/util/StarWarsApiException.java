package com.narcielitonlopes.starwars.util;

public class StarWarsApiException extends Exception {

    public StarWarsApiException(String message) {
        super(message);
    }

    public StarWarsApiException(String message, Throwable tr) {
        super(message, tr);
    }
}
