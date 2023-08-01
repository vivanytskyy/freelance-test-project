package com.gmail.ivanytskyy.vitaliy.pages.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 27/07/2023
 */
public class RegisterPage {
    private final SelenideElement title = $x("//app-register/div/h2");
    private final SelenideElement loginLink = $x("//a[@href='/login']");
    private final SelenideElement usernameInput = $("input[formcontrolname='username']");
    private final SelenideElement nameInput = $("input[formcontrolname='name']");
    private final SelenideElement lastNameInput = $("input[formcontrolname='lastname']");
    private final SelenideElement passwordInput = $("input[formcontrolname='password']");
    private final SelenideElement confirmPasswordInput = $("input[formcontrolname='confirmPassword']");
    private final SelenideElement registerButton = $("div#controls button");

    public RegisterPage setUsername(String username){
        usernameInput.clear();
        usernameInput.sendKeys(username);
        return this;
    }
    public RegisterPage setName(String name){
        nameInput.clear();
        nameInput.sendKeys(name);
        return this;
    }
    public RegisterPage setLastName(String lastName){
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
        return this;
    }
    public RegisterPage setPassword(String password){
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }
    public RegisterPage setConfirmPassword(String password){
        confirmPasswordInput.clear();
        confirmPasswordInput.sendKeys(password);
        return this;
    }
    public LoginPage clickRegisterButtonPositiveCase(){
        registerButton
                .shouldBe(Condition.exist)
                .shouldBe(Condition.enabled)
                .shouldBe(Condition.visible)
                .click();
        title.shouldBe(Condition.disappear);
        return new LoginPage();
    }
    public RegisterPage clickRegisterButtonNegativeCase(){
        registerButton
                .shouldBe(Condition.exist)
                .shouldBe(Condition.enabled)
                .shouldBe(Condition.visible)
                .click();
        return new RegisterPage();
    }
    public LoginPage registerPositiveCase(String username, String name, String lastName, String password){
        return setUsername(username)
                .setName(name)
                .setLastName(lastName)
                .setPassword(password)
                .setConfirmPassword(password)
                .clickRegisterButtonPositiveCase();
    }
    public RegisterPage registerNegativeCase(String username, String name, String lastName, String password){
        return setUsername(username)
                .setName(name)
                .setLastName(lastName)
                .setPassword(password)
                .setConfirmPassword(password)
                .clickRegisterButtonNegativeCase();
    }
    public LoginPage clickLoginLink(){
        loginLink.click();
        return new LoginPage();
    }
    public String getPageTitle(){
        return title.getText();
    }
}