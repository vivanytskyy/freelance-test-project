package com.gmail.ivanytskyy.vitaliy.pages.selenide.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.HomePage;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.ProfilePage;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 27/07/2023
 */
public class UserFloatingPanel {
    private final SelenideElement profileButton = $("button[routerLink='/profile']");
    private final SelenideElement logoutButton = $x("//button[text()='Logout']");

    public ProfilePage clickProfileButton(){
        profileButton
                .shouldBe(Condition.enabled)
                .click();
        return new ProfilePage();
    }
    public HomePage clickLogoutButton(){
        logoutButton
                .shouldBe(Condition.enabled)
                .click();
        return new HomePage();
    }
}