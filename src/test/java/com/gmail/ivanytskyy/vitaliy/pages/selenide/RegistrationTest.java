package com.gmail.ivanytskyy.vitaliy.pages.selenide;

import com.github.javafaker.Faker;
import com.gmail.ivanytskyy.vitaliy.utils.PasswordGenerateService;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 31/07/2023
 */
public class RegistrationTest extends BaseTest{
    private RandomUser randomUser;

    @BeforeMethod
    public void setUp() {
       this.randomUser = new RandomUser();
    }
    @AfterMethod
    public void tearDown(){
        this.randomUser = null;
    }
    @Test(description = "Registration. Positive test", priority = 10)
    public void registrationTest(){
        ProfilePage profilePage = openApp()
                .openRegisterPage()
                .registerPositiveCase(
                        randomUser.username, randomUser.name, randomUser.lastName, randomUser.password)
                .loginPositiveCase(randomUser.username, randomUser.password)
                .openUserPanel().clickProfileButton();
        String resultUsername = profilePage.getUsername();
        profilePage.openUserPanel().clickLogoutButton();
        Assert.assertEquals(resultUsername, randomUser.username, "Unexpected username");
    }
    @Test(description = "Registration (min length of password). Positive test", priority = 20)
    public void registrationPasswordMinLengthTest(){
        String password = new PasswordGenerateService.Builder()
                .useLowerCaseLetters(true)
                .useUpperCaseLetters(true)
                .useDigits(true)
                .usePunctuationSymbols(true)
                .build().generatePassword(8);
        ProfilePage profilePage = openApp()
                .openRegisterPage()
                .registerPositiveCase(
                        randomUser.username, randomUser.name, randomUser.lastName, password)
                .loginPositiveCase(randomUser.username, password)
                .openUserPanel().clickProfileButton();
        String resultUsername = profilePage.getUsername();
        profilePage.openUserPanel().clickLogoutButton();
        Assert.assertEquals(resultUsername, randomUser.username, "Unexpected username");
    }
    @Test(description = "Registration (password length less min value). Negative test", priority = 30)
    public void registrationPasswordLessMinLengthNegativeTest(){
        String expectedTitle = "Login";
        String password = new PasswordGenerateService.Builder()
                .useLowerCaseLetters(true)
                .useUpperCaseLetters(true)
                .useDigits(true)
                .usePunctuationSymbols(true)
                .build().generatePassword(7);
        LoginPage loginPage = openApp()
                .openRegisterPage()
                .registerNegativeCase(
                        randomUser.username, randomUser.name, randomUser.lastName, password)
                .clickLoginLink()
                .loginNegativeCase(randomUser.username, password);
        String resultTitle = loginPage.getTitle();
        Assert.assertEquals(resultTitle, expectedTitle, "Unexpected title");
    }

    private static class RandomUser {
        private final String username;
        private final String name;
        private final String lastName;
        private final String password;
        public RandomUser(){
            Faker faker = new Faker();
            this.username = faker.name().username();
            this.name = faker.name().name();
            this.lastName = faker.name().lastName();
            this.password = new PasswordGenerateService.Builder()
                    .useLowerCaseLetters(true)
                    .useUpperCaseLetters(true)
                    .useDigits(true)
                    .usePunctuationSymbols(true)
                    .build().generatePassword(15);
        }
    }
}