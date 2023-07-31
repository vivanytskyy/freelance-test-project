package com.gmail.ivanytskyy.vitaliy.pages.selenide.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.ProfilePage;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 29/07/2023
 */
public class JobCardByMe extends JobCard{

    public JobCardByMe(SelenideElement container) {
        super(container);
    }
    public ProfilePage clickRemoveButton(){
        container.$("mat-card-actions button")
                .shouldBe(Condition.exist)
                .shouldBe(Condition.enabled)
                .click();
        return new ProfilePage();
    }
    @Override
    public int getCommentsNumber(){
        String commentsNumberAsString = container.$("mat-card-subtitle:nth-child(1)")
                .getText()
                .split(" ")[1];
        return Integer.parseInt(commentsNumberAsString);
    }
}