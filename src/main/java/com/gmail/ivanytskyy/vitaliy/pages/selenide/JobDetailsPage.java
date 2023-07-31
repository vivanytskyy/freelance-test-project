package com.gmail.ivanytskyy.vitaliy.pages.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.components.CommentCard;
import java.util.ArrayList;
import java.util.List;
import static com.codeborne.selenide.Selenide.*;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 29/07/2023
 */
public class JobDetailsPage extends BasePageRegisteredUser{
    private final SelenideElement title = $("div.mat-card-header-text>.mat-card-title");
    private final SelenideElement description = $(".mat-card-content p");
    private final SelenideElement price = $(".mat-card-subtitle.price");
    private final SelenideElement postedBy = $("div.mat-card-header-text>.mat-card-subtitle");
    private final SelenideElement commentInput = $("textarea[formcontrolname='message']");
    private final SelenideElement leaveCommentButton =
            $x("//span[text()=' Leave comment ']/parent::button");
    private final SelenideElement closeJobDetailsButton = $("button[routerlink='/main']");

    public String getTitle(){
        return title.getText();
    }
    public String getDescription(){
        return description.getText();
    }
    public String getPostedBy(){
        return postedBy.getText();
    }
    public double getPrice(){
        String priceAsString = price
                .getText()
                .split(" ")[1];
        return Double.parseDouble(priceAsString);
    }
    public MainPage closeJobDetails(){
        closeJobDetailsButton.click();
        return new MainPage();
    }
    public JobDetailsPage leaveComment(String comment){
        commentInput.clear();
        commentInput.sendKeys(comment);
        leaveCommentButton
                .shouldBe(Condition.exist)
                .shouldBe(Condition.enabled)
                .click();
        return new JobDetailsPage();
    }
    public CommentCard getLeavedComment(int index){
        return new CommentCard(
                $$x("//div[@class='comments']/div/mat-card").get(index - 1));
    }
    public List<CommentCard> getLeavedComments(int length){
        List<CommentCard> commentItems = new ArrayList<>();
        for (int i = 0; i < length; i++){
            commentItems.add(getLeavedComment(i + 1));
        }
        return commentItems;
    }
}