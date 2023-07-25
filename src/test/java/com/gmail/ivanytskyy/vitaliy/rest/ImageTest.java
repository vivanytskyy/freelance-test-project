package com.gmail.ivanytskyy.vitaliy.rest;

import com.gmail.ivanytskyy.vitaliy.rest.controllers.ImageController;
import com.gmail.ivanytskyy.vitaliy.rest.controllers.UserController;
import com.gmail.ivanytskyy.vitaliy.rest.entities.Profile;
import com.gmail.ivanytskyy.vitaliy.rest.entities.User;
import com.gmail.ivanytskyy.vitaliy.utils.TestDataPrepareService;
import com.gmail.ivanytskyy.vitaliy.utils.TokenHolder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.*;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 25/07/2023
 */
public class ImageTest extends BaseTest{
    private User user;
    private String token;
    private static final String IMAGE_UPLOADED_SUCCESS_MESSAGE = "User image was uploaded";
    private static final String PATH_TO_IMAGE;
    static {
        PATH_TO_IMAGE = "src" + File.separator
                + "test" + File.separator
                + "resources" + File.separator + "bulldog.jpg";
    }
    @BeforeMethod
    public void setUp() throws IOException {
        token = TokenHolder.getInstance().getToken();
        user = new UserController().getUser(token);
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        token = null;
        user = null;
    }
    @Test(description = "Upload file. Positive case.", priority = 10)
    public void uploadFileTest() throws IOException {
        File file = new File(PATH_TO_IMAGE);
        ImageController imageController = new ImageController();
        String resultMessage = imageController.uploadFile(file, token);
        Assert.assertEquals(resultMessage, IMAGE_UPLOADED_SUCCESS_MESSAGE);
    }
    @Test(description = "Find profile by user id. Positive case.", priority = 20)
    public void findProfileByUserIdTest() throws IOException {
        File file = new File(PATH_TO_IMAGE);
        String imageAsString = TestDataPrepareService.encodeImageToBase64(file);
        ImageController imageController = new ImageController();
        Profile profile = imageController.findProfileByUserId(user.getId(), token);
        String resultImageAsString = profile.getImage();
        Assert.assertEquals(resultImageAsString, imageAsString);
    }
    @Test(description = "Find profile. Positive case.", priority = 30)
    public void findProfileTest() throws IOException {
        File file = new File(PATH_TO_IMAGE);
        String imageAsString = TestDataPrepareService.encodeImageToBase64(file);
        ImageController imageController = new ImageController();
        Profile profile = imageController.findProfile(token);
        String resultImageAsString = profile.getImage();
        Assert.assertEquals(resultImageAsString, imageAsString);
    }
}