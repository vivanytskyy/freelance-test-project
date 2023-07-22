package com.gmail.ivanytskyy.vitaliy.rest.entities.authorization;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 20/07/2023
 */
@Getter
@Setter
@ToString
public class TokenResponseWrapper {
    private boolean success;
    private String token;
}