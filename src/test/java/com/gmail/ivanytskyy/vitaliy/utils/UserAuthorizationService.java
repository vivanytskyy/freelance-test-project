package com.gmail.ivanytskyy.vitaliy.utils;

import com.gmail.ivanytskyy.vitaliy.rest.controllers.AuthController;
import com.gmail.ivanytskyy.vitaliy.rest.entities.authorization.UserCredentialsWrapper;
import java.io.IOException;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 22/07/2023
 */
public class UserAuthorizationService {
    public static void authorize(){
        String username = CredentialPropertiesSupplier.getInstance().getProperty("username");
        String password = CredentialPropertiesSupplier.getInstance().getProperty("password");
        UserCredentialsWrapper permit = UserCredentialsWrapper.builder()
                .username(username)
                .password(password)
                .confirmPassword(password)
                .build();
        permit.setUsername(username);
        permit.setPassword(password);
        permit.setConfirmPassword(password);
        AuthController authController = new AuthController();
        try {
            if(authController.checkSignInResponseCode(permit) != 200){
                authController.signUp(permit);
            }
            TokenHolder.getInstance().setToken(authController.signIn(permit).getToken());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}