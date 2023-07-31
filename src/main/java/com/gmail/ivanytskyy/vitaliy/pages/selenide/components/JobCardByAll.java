package com.gmail.ivanytskyy.vitaliy.pages.selenide.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.JobDetailsPage;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 29/07/2023
 */
public class JobCardByAll extends JobCard{

    public JobCardByAll(SelenideElement container) {
        super(container);
    }
    public JobDetailsPage clickViewInfoButton(){
        container.$("mat-card-actions button")
                .shouldBe(Condition.exist)
                .shouldBe(Condition.enabled)
                .click();
        return new JobDetailsPage();
    }
    public String getPostedBy(){
        return container.$("div.mat-card-header-text>.mat-card-subtitle").getText();
    }
    @Override
    public int getCommentsNumber(){
        String commentsNumberAsString = container.$("mat-card>:nth-child(2)")
                .getText()
                .split(" ")[1];
        return Integer.parseInt(commentsNumberAsString);
    }
}