package com.gmail.ivanytskyy.vitaliy.pages.selenide;

import com.codeborne.selenide.SelenideElement;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.components.EditUserProfilePopup;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 27/07/2023
 */
public class ProfilePage {
    private final SelenideElement profileTitle = $("h1");
    private final SelenideElement username = $(".col h2");
    private final SelenideElement userFullName = $(".col h3");
    private final SelenideElement editInfoButton =
            $x("//span[text()='Edit Info'] /parent::button");
    private final SelenideElement closeProfileButton =
            $x("//span[text()='Close profile'] /parent::button");
    private final SelenideElement addJobButton = $("button[routerlink='/profile/add-job']");
    private final SelenideElement profileImage = $("img.profile-image");
    private final SelenideElement imageDownloadButton =
            $x("//span[text()='Pick File'] /parent::button");
    private final SelenideElement imageInput =
            $x("//img[@class='profile-image'] /parent::div/input");

    public EditUserProfilePopup clickEditInfoButton(){
        editInfoButton.click();
        return new EditUserProfilePopup();
    }
    public String getPageTitle(){
        return profileTitle.getText();
    }
    public String getUsername(){
        return username.getText();
    }
    public String getUserFullName(){
        return userFullName.getText();
    }
}