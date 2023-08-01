package com.gmail.ivanytskyy.vitaliy.pages.selenide;

import com.github.javafaker.Faker;
import com.gmail.ivanytskyy.vitaliy.utils.PasswordGenerateService;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 01/08/2023
 */
public class LoginTest extends BaseTest{

    @Test(description = "Login. Positive test", priority = 10)
    public void loginTest(){
        ProfilePage profilePage = openApp()
                .openLoginPage()
                .loginPositiveCase(getUsername(), getPassword())
                .openUserPanel()
                .clickProfileButton();
        String resultUsername = profilePage.getUsername();
        profilePage
                .openUserPanel()
                .clickLogoutButton();
        Assert.assertEquals(resultUsername, getUsername());
    }
    @Test(description = "Check link from LoginPage to RegisterPage. Positive test", priority = 20)
    public void linkFromLoginPageToRegisterPageTest(){
        String expectedTitle = "Register";
        String resultTitle = openApp()
                .openLoginPage()
                .clickRegisterLink()
                .getPageTitle();
        Assert.assertEquals(expectedTitle, resultTitle);
    }
    @Test(description = "Login (invalid username). Negative test", priority = 30)
    public void loginInvalidUsernameTest(){
        String invalidUsername = new Faker().name().username();
        String expectedTitle = "Login";
        String resultTitle = openApp()
                .openLoginPage()
                .loginNegativeCase(invalidUsername, getPassword())
                .getTitle();
        Assert.assertEquals(resultTitle, expectedTitle);
    }
    @Test(description = "Login (invalid password). Negative test", priority = 40)
    public void loginInvalidPasswordTest(){
        String expectedTitle = "Login";
        String invalidPassword = new PasswordGenerateService.Builder()
                .useLowerCaseLetters(true)
                .useUpperCaseLetters(true)
                .useDigits(true)
                .usePunctuationSymbols(true)
                .build().generatePassword(7);
        String resultTitle = openApp()
                .openLoginPage()
                .loginNegativeCase(getUsername(), invalidPassword)
                .getTitle();
        Assert.assertEquals(resultTitle, expectedTitle);
    }
}