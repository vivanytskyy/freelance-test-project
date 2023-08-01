package com.gmail.ivanytskyy.vitaliy.rest;

import com.gmail.ivanytskyy.vitaliy.rest.controllers.ImageController;
import com.gmail.ivanytskyy.vitaliy.rest.controllers.UserController;
import com.gmail.ivanytskyy.vitaliy.rest.entities.Profile;
import com.gmail.ivanytskyy.vitaliy.rest.entities.User;
import com.gmail.ivanytskyy.vitaliy.rest.exceptions.UnexpectedHttpStatusCodeException;
import com.gmail.ivanytskyy.vitaliy.utils.TestDataPrepareService;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.*;
import static com.gmail.ivanytskyy.vitaliy.utils.HttpResponseCodeRanges.*;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 25/07/2023
 */
public class ImageTest extends BaseTest{
    private User user;
    private static final String IMAGE_UPLOADED_SUCCESS_MESSAGE = "User image was uploaded";
    private static final String PATH_TO_IMAGE;
    static {
        PATH_TO_IMAGE = "src" + File.separator
                + "test" + File.separator
                + "resources" + File.separator + "bulldog.jpg";
    }

    @BeforeMethod
    public void setUp() throws IOException {
        user = new UserController().getUser(token);
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        user = null;
    }
    @Test(description = "Upload file. Positive case.", priority = 10)
    public void uploadFileTest() throws IOException {
        File file = new File(PATH_TO_IMAGE);
        ImageController imageController = new ImageController();
        String resultMessage = imageController.uploadFile(file, token);
        Assert.assertEquals(resultMessage, IMAGE_UPLOADED_SUCCESS_MESSAGE,
                "The success message wasn't gotten");
    }
    @Test(description = "Find profile by user id. Positive case.", priority = 20)
    public void findProfileByUserIdTest() throws IOException {
        File file = new File(PATH_TO_IMAGE);
        String imageAsString = TestDataPrepareService.encodeImageToBase64(file);
        ImageController imageController = new ImageController();
        Profile profile = imageController.findProfileByUserId(user.getId(), token);
        String resultImageAsString = profile.getImage();
        Assert.assertEquals(resultImageAsString, imageAsString, "Different encoded images");
    }
    @Test(description = "Find profile. Positive case.", priority = 30)
    public void findProfileTest() throws IOException {
        File file = new File(PATH_TO_IMAGE);
        String imageAsString = TestDataPrepareService.encodeImageToBase64(file);
        ImageController imageController = new ImageController();
        Profile profile = imageController.findProfile(token);
        String resultImageAsString = profile.getImage();
        Assert.assertEquals(resultImageAsString, imageAsString, "Different encoded images");
    }
    @Test(description = "Upload file with invalid token. Negative case.", priority = 40,
            expectedExceptions = UnexpectedHttpStatusCodeException.class)
    public void  uploadFileWithInvalidTokenTest() throws IOException {
        File file = new File(PATH_TO_IMAGE);
        ImageController imageController = new ImageController();
        String validToken = token;
        String invalidToken = validToken.substring(0, validToken.length() - 1);
        imageController.uploadFile(file, invalidToken);
    }
    @Test(description = "Find profile by user id with invalid token. Negative case.", priority = 50,
            expectedExceptions = UnexpectedHttpStatusCodeException.class)
    public void  findProfileByUserIdWithInvalidTokenTest() throws IOException {
        ImageController imageController = new ImageController();
        String validToken = token;
        String invalidToken = validToken.substring(0, validToken.length() - 1);
        imageController.findProfileByUserId(user.getId(), invalidToken);
    }
    @Test(description = "Find profile with invalid token. Negative case.", priority = 60,
            expectedExceptions = UnexpectedHttpStatusCodeException.class)
    public void  findProfileWithInvalidTokenTest() throws IOException {
        ImageController imageController = new ImageController();
        String validToken = token;
        String invalidToken = validToken.substring(0, validToken.length() - 1);
        imageController.findProfile(invalidToken);
    }
    @Test(description = "Find profile with invalid userId (negative number). Negative case.", priority = 70)
    public void findProfileWithInvalidUserIdAsNegativeNumberTest() throws IOException {
        ImageController imageController = new ImageController();
        int statusCode = imageController.findProfileByUserIdNegativeCase("-1", token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code. Expected 400th but got " + statusCode);
    }
    @Test(description = "Find profile with invalid userId (userId = 0). Negative case.", priority = 80)
    public void findProfileWithInvalidUserIdAsZeroTest() throws IOException {
        ImageController imageController = new ImageController();
        int statusCode = imageController.findProfileByUserIdNegativeCase("0", token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code. Expected 400th but got " + statusCode);
    }
    @Test(description = "Find profile with invalid userId (user doesn't exist). Negative case.", priority = 90)
    public void findProfileWithInvalidUserIdDoesNotExistTest() throws IOException {
        ImageController imageController = new ImageController();
        int statusCode = imageController.findProfileByUserIdNegativeCase(Long.MAX_VALUE + "", token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code. Expected 400th but got " + statusCode);
    }
    @Test(description = "Upload file (mistake in file name). Negative case.", priority = 100)
    public void uploadFileWithInvalidFileNameTest() throws IOException {
        String invalidFileName = "invalidFile.jpg";
        String invalidPath = "src" + File.separator
                + "test" + File.separator
                + "resources" + File.separator + invalidFileName;
        ImageController imageController = new ImageController();
        int statusCode = imageController.uploadFileNegativeCase(invalidPath, token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code. Expected 400th but got " + statusCode);
    }
}