package com.gmail.ivanytskyy.vitaliy.pages.selenide;

import com.github.javafaker.Faker;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.components.JobCardByAll;
import com.gmail.ivanytskyy.vitaliy.pages.selenide.components.JobCardByMe;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.function.Function;
import static com.gmail.ivanytskyy.vitaliy.utils.TestDataPrepareService.genPrice;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 27/07/2023
 */
public class UseCasesTest extends BaseTest{

    @Test(description = "Use case 1. Update profile. Positive test.", priority = 10, enabled = true)
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
    @Test(description = "Use case 2. Create job item. Positive test.", priority = 20, enabled = true)
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
    @Test(description = "Use case 3. Leave comment item. Positive test.", priority = 30, enabled = true)
    public void leaveCommentTest(){
        //Create job
        String preparedTitle = new Faker().job().title();
        String preparedDescription = new Faker().job().field();
        double preparedPrice = genPrice(1000);
        ProfilePage profilePage = openApp()
                .openLoginPage()
                .loginPositiveCase(getUsername(), getPassword())
                .openUserPanel()
                .clickProfileButton()
                .openJobForm()
                .addNewJob(preparedTitle, preparedDescription, preparedPrice);
        String userFullName = profilePage.getUserFullName();
        //Look over the job ad at MainPage
        MainPage mainPage = profilePage.closeProfile();
        JobCardByAll jobCardByAll = mainPage.getJobItemCard(1);
        String resultTitle = jobCardByAll.getTitle();
        String resultDescription = jobCardByAll.getDescription();
        double resultPrice = jobCardByAll.getPrice();
        int resultNumberOfComments = jobCardByAll.getCommentsNumber();
        String resultPostedBy = jobCardByAll.getPostedBy();
        Assert.assertEquals(resultTitle, preparedTitle);
        Assert.assertEquals(resultDescription, preparedDescription);
        Assert.assertEquals(resultPrice, preparedPrice);
        Assert.assertEquals(resultNumberOfComments, 0);
        Assert.assertTrue(resultPostedBy.contains(userFullName));
        //Look over the details of chosen job ad
        JobDetailsPage jobDetailsPage = jobCardByAll.clickViewInfoButton();
        String resultTitleInDetails = jobDetailsPage.getTitle();
        String resultDescriptionInDetails = jobDetailsPage.getDescription();
        double resultPriceInDetails = jobDetailsPage.getPrice();
        String resultPostedByInDetails = jobDetailsPage.getPostedBy();
        Assert.assertEquals(resultTitleInDetails, preparedTitle);
        Assert.assertEquals(resultDescriptionInDetails, preparedDescription);
        Assert.assertEquals(resultPriceInDetails, preparedPrice);
        Assert.assertTrue(resultPostedByInDetails.contains(userFullName));
        //Create a comment
        String commentText = new Faker().dune().quote();
        String leavedComment = jobDetailsPage
                .leaveComment(commentText)
                .getLeavedComment(1)
                .getCommentText();
        Assert.assertEquals(leavedComment, commentText);
        //Delete the job item and log out
        jobDetailsPage
                .openUserPanel()
                .clickProfileButton()
                .getJobItemCard(1)
                .clickRemoveButton()
                .openUserPanel()
                .clickLogoutButton();
    }
    @Test(description = "Use case 4. Check number of own jobs and number of comments to them. Positive test.",
            priority = 40)
    public void checkNumberOfOwnJobItemsAndNumberOfCommentsToThemTest(){
        //Create several jobs
        int numberOfJobs = 3;
        ProfilePage profilePage = openApp()
                .openLoginPage()
                .loginPositiveCase(getUsername(), getPassword())
                .openUserPanel()
                .clickProfileButton();
        for (int i = 0; i < numberOfJobs; i++){
            String title = new Faker().job().title();
            String description = new Faker().job().field();
            double price = genPrice(1000);
            profilePage
                    .openJobForm()
                    .addNewJob(title, description, price);
            JobCardByMe postedJob = profilePage.getJobItemCard(1);
            Assert.assertEquals(postedJob.getTitle(), title);
            Assert.assertEquals(postedJob.getDescription(), description);
            Assert.assertEquals(postedJob.getPrice(), price);
            Assert.assertEquals(postedJob.getCommentsNumber(), 0);
        }
        //Add Comments to own jobs (for even jobs add 1 comments, for odd jobs add 2 comments)
        Function<Integer, Integer> getNumberOfComments = n -> n % 2 == 0 ? 1 : 2;
        MainPage mainPage = profilePage.closeProfile();
        for(int i = 0; i < numberOfJobs; i++){
            int index = i + 1;
            int numberOfComments = getNumberOfComments.apply(index);
            while (numberOfComments != 0){
                String commentText = new Faker().dune().quote();
                mainPage
                        .getJobItemCard(index)
                        .clickViewInfoButton()
                        .leaveComment(commentText)
                        .closeJobDetails();
                numberOfComments--;
            }
        }
        //Check number of jobs
        profilePage = mainPage
                .openUserPanel()
                .clickProfileButton();
        int resultNumberOfJobs = Integer.parseInt(profilePage
                .getJobItemsSectionTitle()
                .replaceAll("[^0-9]", ""));
        Assert.assertEquals(resultNumberOfJobs, numberOfJobs);
        //Check number of comments
        int index = 1;
        for(int i = 0; i < numberOfJobs; i++){
            int expectedNumberOfComments = getNumberOfComments.apply(index);
            int resultNumberOfComments = profilePage
                    .getJobItemCard(index)
                    .getCommentsNumber();
            Assert.assertEquals(resultNumberOfComments, expectedNumberOfComments);
            index++;
        }
        //Delete all job items;
        for(int i = 0; i < numberOfJobs; i++){
            profilePage.getJobItemCard(1).clickRemoveButton();
        }
        //Log out
        profilePage
                .openUserPanel()
                .clickLogoutButton();
    }
}