package com.gmail.ivanytskyy.vitaliy.pages.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.components.AddJobForm;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.components.EditUserProfilePopup;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.components.JobCardByMe;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.components.UserFloatingPanel;
import java.util.ArrayList;
import java.util.List;
import static com.codeborne.selenide.Selenide.*;

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
    private final SelenideElement openJobFormButton = $("button[routerlink='/profile/add-job']");
    private final SelenideElement profileImage = $("img.profile-image");
    private final SelenideElement imageDownloadButton =
            $x("//span[text()='Pick File'] /parent::button");
    private final SelenideElement imageInput =
            $x("//img[@class='profile-image'] /parent::div/input");
    private final SelenideElement jobItemsBlockTitle = $x("//app-my-jobs//h2");
    private final SelenideElement userPanelButton = $("button[mattooltip='Profile']");


    public String getPageTitle(){
        return profileTitle.getText();
    }
    public String getUsername(){
        return username.getText();
    }
    public String getUserFullName(){
        return userFullName.getText();
    }
    public EditUserProfilePopup openEditProfilePopup(){
        editInfoButton.click();
        return new EditUserProfilePopup();
    }
    public String getJobItemsBlockTitle(){
        return jobItemsBlockTitle.getText();
    }
    public AddJobForm openJobForm(){
        openJobFormButton.click();
        return new AddJobForm();
    }
    public JobCardByMe getJobItemCard(int index) {
        return new JobCardByMe($("app-my-jobs>div>mat-card", index - 1));
    }
    public List<JobCardByMe> getJobItems(int length){
        List<JobCardByMe> jobItems = new ArrayList<>();
        for (int i = 0; i < length; i++){
            jobItems.add(getJobItemCard(i + 1));
        }
        return jobItems;
    }
    public UserFloatingPanel openUserPanel(){
        userPanelButton.shouldBe(Condition.enabled).click();
        return new UserFloatingPanel();
    }
}