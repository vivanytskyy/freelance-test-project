package com.gmail.ivanytskyy.vitaliy.pages.selenide;

import com.github.javafaker.Faker;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.components.JobCardByAll;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.components.JobCardByMe;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.gmail.ivanytskyy.vitaliy.utils.TestDataPrepareService.genPrice;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 27/07/2023
 */
public class UseCasesTest extends BaseTest{
    @Test(description = "Use case 1. Update profile. Positive test.", priority = 10)
    public void updateProfileTest(){
        Faker faker = new Faker();
        String newName = faker.name().firstName();
        String newLastName = faker.name().lastName();
        String newUserFullName = String.format("%s %s", newName, newLastName);
        ProfilePage profilePage = openApp()
                .openLoginPage()
                .loginPositiveCase(getUsername(), getPassword())
                .openUserPanel()
                .clickProfileButton();
        String correctedUserFullName = profilePage
                .openEditProfilePopup()
                .updateUserData(newName, newLastName)
                .getUserFullName();
        Assert.assertEquals(correctedUserFullName, newUserFullName);
        profilePage
                .openUserPanel()
                .clickLogoutButton();
    }
    @Test(description = "Use case 2. Create job item. Positive test.", priority = 20)
    public void createJobItemTest(){
        String title = new Faker().job().title();
        String description = new Faker().job().field();
        double price = genPrice(1000);
        ProfilePage profilePage = openApp()
                .openLoginPage()
                .loginPositiveCase(getUsername(), getPassword())
                .openUserPanel()
                .clickProfileButton()
                .openJobForm()
                .addNewJob(title, description, price);
        JobCardByMe postedJob = profilePage.getJobItemCard(1);
        Assert.assertEquals(postedJob.getTitle(), title);
        Assert.assertEquals(postedJob.getDescription(), description);
        Assert.assertEquals(postedJob.getPrice(), price);
        Assert.assertEquals(postedJob.getCommentsNumber(), 0);
        postedJob
                .clickRemoveButton()
                .openUserPanel()
                .clickLogoutButton();
    }
    @Test(description = "Use case 3. Leave comment item. Positive test.", priority = 30)
    public void leaveCommentTest(){
        String commentText = new Faker().dune().quote();
        System.out.println(commentText);
        MainPage mainPage = openApp()
                .openLoginPage()
                .loginPositiveCase(getUsername(), getPassword());
        JobCardByAll jobCardByAll = mainPage.getJobItemCard(1);
        JobDetailsPage jobDetailsPage = jobCardByAll.clickViewInfoButton();
        System.out.println("title: " + jobDetailsPage.getTitle());
        System.out.println("description: " + jobDetailsPage.getDescription());
        System.out.println("price: " + jobDetailsPage.getPrice());
        System.out.println("posted by: " + jobDetailsPage.getPostedBy());
        String leavedComment = jobDetailsPage
                .leaveComment(commentText)
                .getLeavedComment(1)
                .getCommentText();
        Assert.assertEquals(leavedComment, commentText);
        jobDetailsPage
                .closeJobDetails()
                .openUserPanel()
                .clickLogoutButton();
    }
}