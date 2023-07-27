package com.gmail.ivanytskyy.vitaliy.pages.selenide;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 27/07/2023
 */
public class HomePage {
    private final SelenideElement loginPage = $("a[href='/login']");
    private final SelenideElement registerPage = $("a[href='/register']");

    public LoginPage openLoginPage(){
        loginPage.click();
        return new LoginPage();
    }
    public RegisterPage openRegisterPage(){
        registerPage.click();
        return new RegisterPage();
    }
}