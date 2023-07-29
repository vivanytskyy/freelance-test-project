package com.gmail.ivanytskyy.vitaliy.pages.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.components.JobCardByAll;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.components.UserFloatingPanel;
import java.util.ArrayList;
import java.util.List;
import static com.codeborne.selenide.Selenide.$;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 27/07/2023
 */
public class MainPage {
    private final SelenideElement userPanelButton = $("button[mattooltip='Profile']");
    private final SelenideElement userProfileFloatingPanel = $("#cdk-overlay-0");
    private final SelenideElement userFullName = $("h3");

    public UserFloatingPanel openUserPanel(){
        userPanelButton.shouldBe(Condition.enabled).click();
        return new UserFloatingPanel();
    }
    public String getUserFullName(){
        return userFullName.getText();
    }
    public JobCardByAll getJobItemCard(int index) {
        return new JobCardByAll($("app-index>div>mat-card", index - 1));
    }
    public List<JobCardByAll> getJobItems(int length){
        List<JobCardByAll> jobItems = new ArrayList<>();
        for (int i = 0; i < length; i++){
            jobItems.add(getJobItemCard(i + 1));
        }
        return jobItems;
    }
}