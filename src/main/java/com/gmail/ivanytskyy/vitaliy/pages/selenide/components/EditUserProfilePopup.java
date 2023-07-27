package com.gmail.ivanytskyy.vitaliy.pages.selenide.components;

import com.codeborne.selenide.SelenideElement;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.ProfilePage;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 27/07/2023
 */
public class EditUserProfilePopup {
    private final SelenideElement popupTitle = $(".cdk-overlay-pane h2");
    private final SelenideElement nameInput = $x("//input[@formcontrolname='name']");
    private final SelenideElement lastNameInput = $x("//input[@formcontrolname='lastname']");
    private final SelenideElement cancelButton = $x("//span[text()=' Cancel ']/parent::button");
    private final SelenideElement updateButton = $x("//span[text()=' Update ']/parent::button");
    public EditUserProfilePopup setName(String name){
        nameInput.clear();
        nameInput.sendKeys(name);
        return this;
    }
    public EditUserProfilePopup setLastName(String lastName){
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
        return this;
    }
    public ProfilePage clickUpdateButton(){
        updateButton.click();
        return new ProfilePage();
    }
    public ProfilePage clickCancelButton(){
        cancelButton.click();
        return new ProfilePage();
    }
    public ProfilePage updateUserData(String name, String lastName){
        return setName(name).setLastName(lastName).clickUpdateButton();
    }
    public String getTitle(){
        return popupTitle.getText();
    }
}