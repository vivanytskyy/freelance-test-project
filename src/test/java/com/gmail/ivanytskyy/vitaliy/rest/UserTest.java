package com.gmail.ivanytskyy.vitaliy.rest;

import com.gmail.ivanytskyy.vitaliy.rest.controllers.AuthController;
import com.gmail.ivanytskyy.vitaliy.rest.entities.authorization.TokenResponseWrapper;
import com.gmail.ivanytskyy.vitaliy.rest.entities.authorization.UserCredentialsWrapper;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 18/07/2023
 */
public class UserTest extends BaseTest{

    @Test(priority = 10, enabled = false)
    public void signUpTest() throws IOException {
        UserCredentialsWrapper permit = new UserCredentialsWrapper();
        permit.setUsername("PetroPetrovPpppppp");
        permit.setPassword("123456789");
        permit.setConfirmPassword("123456789");
        AuthController authController = new AuthController();
        authController.signUp(permit);
    }
    @Test(priority = 20)
    public void signInTest() throws IOException {
        UserCredentialsWrapper permit = new UserCredentialsWrapper();
        permit.setUsername("PetroPetrovP");
        permit.setPassword("123456789");
        AuthController authController = new AuthController();
        TokenResponseWrapper tokenWrapper = authController.signIn(permit);
        System.out.println(tokenWrapper.getToken());
    }
}