package com.gmail.ivanytskyy.vitaliy.pages.selenide;

import com.gmail.ivanytskyy.vitaliy.utils.TestDataPrepareService;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 01/08/2023
 */
public class ProfileTest extends BaseTest{
    private static final String EXPECTED_PAGE_TITLE = "Profile";
    private static final String EXPECTED_INITIAL_JOB_SECTION_TITLE = "You don't have jobs. Wanna create new?";
    private static final String PATH_TO_IMAGE;
    static {
        PATH_TO_IMAGE = "src" + File.separator
                + "test" + File.separator
                + "resources" + File.separator + "bulldog.jpg";
    }

    @Test(description = "Open profile test. Positive case", priority = 10)
    public void openProfileTest(){
        ProfilePage profilePage = openApp()
                .openLoginPage()
                .loginPositiveCase(getUsername(), getPassword())
                .openUserPanel()
                .clickProfileButton();
        String resultPageTitle = profilePage.getPageTitle();
        String resultJobSectionTitle = profilePage.getJobItemsSectionTitle();
        String resultUsername = profilePage.getUsername();
        Assert.assertEquals(resultPageTitle, EXPECTED_PAGE_TITLE, "Page title is wrong");
        Assert.assertEquals(resultJobSectionTitle, EXPECTED_INITIAL_JOB_SECTION_TITLE,
                "Initial job section title is wrong");
        Assert.assertEquals(resultUsername, getUsername(), "Posted username is wrong");
    }
    @Test(description = "Test upload a file", priority = 20)
    public void fileUploadTest() throws IOException {
        File file = new File(PATH_TO_IMAGE);
        String expectedImageAsString = TestDataPrepareService.encodeImageToBase64(file);
        String resultImageAsString = openApp()
                .openLoginPage()
                .loginPositiveCase(getUsername(), getPassword())
                .openUserPanel()
                .clickProfileButton()
                .selectFile(file)
                .uploadFile()
                .getImage();
        Assert.assertTrue(resultImageAsString.contains(expectedImageAsString),
                "Encoded images are dissimilar");
    }
}