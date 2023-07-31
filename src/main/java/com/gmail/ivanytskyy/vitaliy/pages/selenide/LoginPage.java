package com.gmail.ivanytskyy.vitaliy.pages.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 27/07/2023
 */
public class LoginPage {
    private final SelenideElement title = $x("//app-login/div/h2");
    private final SelenideElement usernameInput = $("input[formcontrolname='username']");
    private final SelenideElement passwordInput = $("input[formcontrolname='password']");
    private final SelenideElement loginButton = $("div#controls button");
    private final SelenideElement registerLink = $("div#controls a");

    public LoginPage setUsername(String username){
        usernameInput.clear();
        usernameInput.sendKeys(username);
        return this;
    }
    public LoginPage setPassword(String password){
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }
    public MainPage clickLoginButtonPositiveCase(){
        loginButton
                .shouldBe(Condition.exist)
                .shouldBe(Condition.enabled)
                .shouldBe(Condition.visible)
                .click();
        title.shouldBe(Condition.disappear);
        return new MainPage();
    }
    public LoginPage clickLoginButtonNegativeCase(){
        loginButton
                .shouldBe(Condition.enabled)
                .click();
        return new LoginPage();
    }
    public MainPage loginPositiveCase(String username, String password){
        return setUsername(username)
                .setPassword(password)
                .clickLoginButtonPositiveCase();
    }
    public LoginPage loginNegativeCase(String username, String password){
        return setUsername(username)
                .setPassword(password)
                .clickLoginButtonNegativeCase();
    }
    public RegisterPage clickRegisterLink(){
        registerLink.click();
        return new RegisterPage();
    }
    public String getTitle(){
        return title.getText();
    }
}