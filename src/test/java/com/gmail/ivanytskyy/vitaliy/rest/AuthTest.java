package com.gmail.ivanytskyy.vitaliy.rest;

import com.gmail.ivanytskyy.vitaliy.rest.controllers.AuthController;
import com.gmail.ivanytskyy.vitaliy.rest.entities.authorization.TokenResponseWrapper;
import com.gmail.ivanytskyy.vitaliy.rest.entities.authorization.UserCredentialsWrapper;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 22/07/2023
 */
public class AuthTest extends BaseTest{
    @Test(priority = 10, enabled = false)
    public void signUpTest() throws IOException {
        UserCredentialsWrapper permit = UserCredentialsWrapper.builder()
                .username("PetroPetrovPpppppp")
                .password("123456789")
                .confirmPassword("123456789")
                .build();
        AuthController authController = new AuthController();
        authController.signUp(permit);
    }
    @Test(priority = 20, enabled = false)
    public void signInTest() throws IOException {
        UserCredentialsWrapper permit = UserCredentialsWrapper.builder()
                .username("PetroPetrovP")
                .password("123456789")
                .confirmPassword("123456789")
                .build();
        AuthController authController = new AuthController();
        TokenResponseWrapper tokenWrapper = authController.signIn(permit);
        System.out.println(tokenWrapper.getToken());
    }
}
