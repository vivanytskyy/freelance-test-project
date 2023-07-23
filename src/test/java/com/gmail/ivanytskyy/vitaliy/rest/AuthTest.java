package com.gmail.ivanytskyy.vitaliy.rest;

import com.github.javafaker.Faker;
import com.gmail.ivanytskyy.vitaliy.rest.controllers.AuthController;
import com.gmail.ivanytskyy.vitaliy.rest.controllers.UserController;
import com.gmail.ivanytskyy.vitaliy.rest.entities.User;
import com.gmail.ivanytskyy.vitaliy.rest.entities.authorization.TokenResponseWrapper;
import com.gmail.ivanytskyy.vitaliy.rest.entities.authorization.UserCredentialsWrapper;
import com.gmail.ivanytskyy.vitaliy.rest.exceptions.UnexpectedHttpStatusCodeException;
import com.gmail.ivanytskyy.vitaliy.utils.CredentialPropertiesSupplier;
import com.gmail.ivanytskyy.vitaliy.utils.PasswordGenerateService;
import com.gmail.ivanytskyy.vitaliy.utils.TokenHolder;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 22/07/2023
 */
public class AuthTest extends BaseTest{

    @Test(description = "Create user. Positive case", priority = 10)
    public void signUpTest() throws IOException {
        Faker faker = new Faker();
        String username = faker.name().username();
        String password = new PasswordGenerateService.Builder()
                .useLowerCaseLetters(true)
                .useUpperCaseLetters(true)
                .useDigits(true)
                .usePunctuationSymbols(true)
                .build().generatePassword(15);
        UserCredentialsWrapper permit = UserCredentialsWrapper.builder()
                .username(username)
                .password(password)
                .confirmPassword(password)
                .build();
        AuthController authController = new AuthController();
        authController.signUp(permit);
        String token = authController.signIn(permit).getToken();
        UserController userController = new UserController();
        User resultUser = userController.getUser(token);
        Assert.assertEquals(resultUser.getUsername(), username);
    }
    @Test(description = "Sign in. Positive case", priority = 20)
    public void signInTest() throws IOException {
        String defaultUsername = CredentialPropertiesSupplier.getInstance().getProperty("username");
        String defaultPassword = CredentialPropertiesSupplier.getInstance().getProperty("password");
        String defaultToken = TokenHolder.getInstance().getToken();
        UserCredentialsWrapper permit = UserCredentialsWrapper.builder()
                .username(defaultUsername)
                .password(defaultPassword)
                .confirmPassword(defaultPassword)
                .build();
        AuthController authController = new AuthController();
        TokenResponseWrapper tokenWrapper = authController.signIn(permit);
        String resultToken = tokenWrapper.getToken();
        Assert.assertNotEquals(resultToken, defaultToken);
        UserController userController = new UserController();
        User resultUser = userController.getUser(resultToken);
        Assert.assertEquals(resultUser.getUsername(), defaultUsername);
    }
    @Test(description = "Create user (min password length = 8). Positive case", priority = 30)
    public void signUpPasswordMinValueTest() throws IOException {
        Faker faker = new Faker();
        String username = faker.name().username();
        String password = new PasswordGenerateService.Builder()
                .useLowerCaseLetters(true)
                .useUpperCaseLetters(true)
                .useDigits(true)
                .usePunctuationSymbols(true)
                .build().generatePassword(8);
        UserCredentialsWrapper permit = UserCredentialsWrapper.builder()
                .username(username)
                .password(password)
                .confirmPassword(password)
                .build();
        AuthController authController = new AuthController();
        authController.signUp(permit);
        String token = authController.signIn(permit).getToken();
        UserController userController = new UserController();
        User resultUser = userController.getUser(token);
        Assert.assertEquals(resultUser.getUsername(), username);
    }
    @Test(description = "Create user (password length = 100). Positive case", priority = 40)
    public void signUpPasswordBigValueTest() throws IOException {
        Faker faker = new Faker();
        String username = faker.name().username();
        String password = new PasswordGenerateService.Builder()
                .useLowerCaseLetters(true)
                .useUpperCaseLetters(true)
                .useDigits(true)
                .usePunctuationSymbols(true)
                .build().generatePassword(100);
        UserCredentialsWrapper permit = UserCredentialsWrapper.builder()
                .username(username)
                .password(password)
                .confirmPassword(password)
                .build();
        AuthController authController = new AuthController();
        authController.signUp(permit);
        String token = authController.signIn(permit).getToken();
        UserController userController = new UserController();
        User resultUser = userController.getUser(token);
        Assert.assertEquals(resultUser.getUsername(), username);
    }
    @Test(description = "Create user (password length less then min value). Negative case", priority = 50,
            expectedExceptions = UnexpectedHttpStatusCodeException.class)
    public void signUpPasswordLessMinValueNegativeTest() throws IOException {
        Faker faker = new Faker();
        String username = faker.name().username();
        String password = new PasswordGenerateService.Builder()
                .useLowerCaseLetters(true)
                .useUpperCaseLetters(true)
                .useDigits(true)
                .usePunctuationSymbols(true)
                .build().generatePassword(7);
        UserCredentialsWrapper permit = UserCredentialsWrapper.builder()
                .username(username)
                .password(password)
                .confirmPassword(password)
                .build();
        AuthController authController = new AuthController();
        authController.signUp(permit);
    }
}