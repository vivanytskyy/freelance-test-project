package com.gmail.ivanytskyy.vitaliy.pages.selenide.components;

import com.codeborne.selenide.SelenideElement;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.ProfilePage;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 29/07/2023
 */
public class JobItemCard {
    private final SelenideElement container;
    public JobItemCard(SelenideElement container){
        this.container = container;
    }
    public ProfilePage clickRemoveButton(){
        container.$("mat-card-actions button").click();
        return new ProfilePage();
    }
    public String getTitle(){
        return container.$("div.mat-card-header-text>.mat-card-title").getText();
    }
    public String getDescription(){
        return container.$(".mat-card-content p").getText();
    }
    public double getPrice(){
        String priceAsString = container.$(".mat-card-subtitle.price")
                .getText()
                .split(" ")[1];
        return Double.parseDouble(priceAsString);
    }
    public int getCommentsNumber(){
        String commentsNumberAsString = container.$("mat-card-subtitle:nth-child(1)")
                .getText()
                .split(" ")[1];
        return Integer.parseInt(commentsNumberAsString);
    }
}