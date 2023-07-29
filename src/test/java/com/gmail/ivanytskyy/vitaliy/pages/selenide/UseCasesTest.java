package com.gmail.ivanytskyy.vitaliy.pages.selenide;

import com.github.javafaker.Faker;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.components.JobItemCard;
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
        JobItemCard postedJob = profilePage.getJobItemCard(1);
        Assert.assertEquals(postedJob.getTitle(), title);
        Assert.assertEquals(postedJob.getDescription(), description);
        Assert.assertEquals(postedJob.getPrice(), price);
        Assert.assertEquals(postedJob.getCommentsNumber(), 0);
        postedJob
                .clickRemoveButton()
                .openUserPanel()
                .clickLogoutButton();
    }
}