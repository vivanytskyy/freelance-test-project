package com.gmail.ivanytskyy.vitaliy.rest.entities.authorization;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 18/07/2023
 */
@Getter
@Setter
@ToString
@Builder
public class UserCredentialsWrapper {
    private String username;
    private String password;
    private String confirmPassword;
}