package com.gmail.ivanytskyy.vitaliy.pages.selenide.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.ProfilePage;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 28/07/2023
 */
public class AddJobForm {
    private final SelenideElement formTitle = $("app-add-job h2");
    private final SelenideElement titleInput = $("input[formcontrolname='title']");
    private final SelenideElement descriptionInput = $("textarea[formcontrolname='description']");
    private final SelenideElement priceInput = $("input[formcontrolname='price']");
    private final SelenideElement addJobButton = $x("//span[text()=' Create job ']/parent::button");
    private final SelenideElement backButton = $x("//button[@routerLink='/profile']");
    public AddJobForm setJobTitle(String title){
        titleInput.clear();
        titleInput.sendKeys(title);
        return this;
    }
    public AddJobForm setJobDescription(String description){
        descriptionInput.clear();
        descriptionInput.sendKeys(description);
        return this;
    }
    public AddJobForm setJobPrice(Double price){
        priceInput.clear();
        priceInput.sendKeys(String.valueOf(price));
        return this;
    }
    public ProfilePage clickAddJobButton(){
        addJobButton
                .shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new ProfilePage();
    }
    public ProfilePage addNewJob(String title, String description, Double price){
        return setJobTitle(title)
                .setJobDescription(description)
                .setJobPrice(price)
                .clickAddJobButton();
    }
    public ProfilePage clickBackButton(){
        backButton.click();
        return new ProfilePage();
    }
    public String getFormTitle(){
        return formTitle.getText();
    }
}