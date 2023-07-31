package com.gmail.ivanytskyy.vitaliy.pages.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.components.UserFloatingPanel;
import static com.codeborne.selenide.Selenide.$;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 30/07/2023
 */
public abstract class BasePageRegisteredUser {
    private final SelenideElement userPanelButton = $("button[mattooltip='Profile']");

    public UserFloatingPanel openUserPanel(){
        userPanelButton
                .shouldBe(Condition.exist)
                .shouldBe(Condition.enabled)
                .shouldBe(Condition.visible)
                .click();
        return new UserFloatingPanel();
    }
}