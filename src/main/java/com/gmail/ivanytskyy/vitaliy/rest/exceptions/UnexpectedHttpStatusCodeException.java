package com.gmail.ivanytskyy.vitaliy.rest.exceptions;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 18/07/2023
 */
public class UnexpectedHttpStatusCodeException extends RuntimeException{
    public UnexpectedHttpStatusCodeException(int statusCode) {
        super("Unexpected code " + statusCode);
    }
}