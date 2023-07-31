package com.gmail.ivanytskyy.vitaliy.pages.selenide.components;

import com.codeborne.selenide.SelenideElement;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 29/07/2023
 */
public class CommentCard {
    private final SelenideElement container;

    public CommentCard(SelenideElement container) {
        this.container = container;
    }
    public String getAddedBy(){
        return container.$x("./mat-card-subtitle[1]/span").getText();
    }
    public String getCommentText(){
        return container.$x("./mat-card-content/p").getText();
    }
    public String getLeavedTimeAgo(){
        return container.$x("./mat-card-subtitle[2]/span").getText();
    }
}