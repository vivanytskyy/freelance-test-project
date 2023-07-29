package com.gmail.ivanytskyy.vitaliy.pages.selenide.components;

import com.codeborne.selenide.SelenideElement;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 29/07/2023
 */
public abstract class JobCard {
    protected final SelenideElement container;
    public JobCard(SelenideElement container){
        this.container = container;
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
    public abstract int getCommentsNumber();
}