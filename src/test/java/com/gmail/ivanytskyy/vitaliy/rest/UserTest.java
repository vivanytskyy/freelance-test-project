package com.gmail.ivanytskyy.vitaliy.rest;

import static com.gmail.ivanytskyy.vitaliy.utils.HttpResponseCodeRanges.*;
import com.github.javafaker.Faker;
import com.gmail.ivanytskyy.vitaliy.rest.controllers.UserController;
import com.gmail.ivanytskyy.vitaliy.rest.entities.User;
import com.gmail.ivanytskyy.vitaliy.rest.exceptions.UnexpectedHttpStatusCodeException;
import com.gmail.ivanytskyy.vitaliy.utils.CredentialPropertiesSupplier;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 18/07/2023
 */
public class UserTest extends BaseTest{

    @Test(description = "Get user by valid token. Positive case.", priority = 10)
    public void getUserByValidTokenTest() throws IOException {
        String defaultUsername = CredentialPropertiesSupplier.getInstance().getProperty("username");
        UserController controller = new UserController();
        User user = controller.getUser(token);
        Assert.assertEquals(user.getUsername(), defaultUsername);
    }
    @Test(description = "Get user by valid id. Positive case.", priority = 20)
    public void findUserByValidIdTest() throws IOException {
        UserController controller = new UserController();
        User defaulUser = controller.getUser(token);
        User resultUser = controller.findUserById(defaulUser.getId(), token);
        Assert.assertEquals(resultUser, defaulUser);
    }
    @Test(description = "Update user. Positive case.", priority = 30)
    public void updateUserTest() throws IOException {
        UserController controller = new UserController();
        User defaulUser = controller.getUser(token);
        Faker faker = new Faker();
        defaulUser.setName(faker.name().firstName());
        defaulUser.setLastname(faker.name().lastName());
        controller.updateUser(defaulUser, token);
        Assert.assertEquals(controller.getUser(token), defaulUser);
    }
    @Test(description = "Get user by invalid token. Negative case.", priority = 40,
            expectedExceptions = UnexpectedHttpStatusCodeException.class)
    public void getUserByInvalidTokenTest() throws IOException {
        UserController controller = new UserController();
        String validToken = token;
        String invalidToken = validToken.substring(0, validToken.length() - 1);
        controller.getUser(invalidToken);
    }
    @Test(description = "Get user by invalid id (id is negative number). Negative case.", priority = 50)
    public void findUserByInvalidIdAsNegativeNumberTest() throws IOException {
        UserController controller = new UserController();
        int statusCode = controller.findUserByIdNegativeCase("-1", token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Get user by invalid id (id is 0). Negative case.", priority = 60)
    public void findUserByInvalidIdAsZeroTest() throws IOException {
        UserController controller = new UserController();
        int statusCode = controller.findUserByIdNegativeCase("0", token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Get user by id (user doesn't exist). Negative case.", priority = 70)
    public void findUserByIdUserDoesNotExistTest() throws IOException {
        UserController controller = new UserController();
        int statusCode = controller.findUserByIdNegativeCase(Long.MAX_VALUE + "", token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Get user by invalid id (id is null). Negative case.", priority = 80)
    public void findUserByInvalidIdAsNullTest() throws IOException {
        UserController controller = new UserController();
        int statusCode = controller.findUserByIdNegativeCase("null", token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Get user by invalid id (id is true). Negative case.", priority = 90)
    public void findUserByInvalidIdAsTrueTest() throws IOException {
        UserController controller = new UserController();
        int statusCode = controller.findUserByIdNegativeCase("true", token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Get user by invalid id (id is NaN). Negative case.", priority = 100)
    public void findUserByInvalidIdAsNaNTest() throws IOException {
        UserController controller = new UserController();
        int statusCode = controller.findUserByIdNegativeCase("NaN", token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Get user by invalid id (id is float number as string). Negative case.", priority = 110)
    public void findUserByInvalidIdAsFloatNumberTest() throws IOException {
        UserController controller = new UserController();
        User defaulUser = controller.getUser(token);
        int statusCode = controller.findUserByIdNegativeCase(
                defaulUser.getId() + ".0", token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Update user (name = null). Negative case.", priority = 120)
    public void updateUserInvalidNameAsNullTest() throws IOException {
        UserController controller = new UserController();
        User defaulUser = controller.getUser(token);
        String lastname = new Faker().name().lastName();
        JSONObject json = new JSONObject();
        json.put("id", defaulUser.getId())
                .put("username", defaulUser.getUsername())
                .put("name", JSONObject.NULL)
                .put("lastname", lastname);
        int statusCode = controller.updateUserNegativeCase(json.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Update user (lastname = null). Negative case.", priority = 130)
    public void updateUserInvalidLastNameAsNullTest() throws IOException {
        UserController controller = new UserController();
        User defaulUser = controller.getUser(token);
        String name = new Faker().name().firstName();
        JSONObject json = new JSONObject();
        json.put("id", defaulUser.getId())
                .put("username", defaulUser.getUsername())
                .put("name", name)
                .put("lastname", JSONObject.NULL);
        int statusCode = controller.updateUserNegativeCase(json.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Update user (name = false). Negative case.", priority = 140)
    public void updateUserInvalidNameAsBooleanTest() throws IOException {
        UserController controller = new UserController();
        User defaulUser = controller.getUser(token);
        String lastname = new Faker().name().lastName();
        JSONObject json = new JSONObject();
        json.put("id", defaulUser.getId())
                .put("username", defaulUser.getUsername())
                .put("name", false)
                .put("lastname", lastname);
        int statusCode = controller.updateUserNegativeCase(json.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Update user (lastname = true). Negative case.", priority = 150)
    public void updateUserInvalidLastNameAsBooleanTest() throws IOException {
        UserController controller = new UserController();
        User defaulUser = controller.getUser(token);
        String name = new Faker().name().firstName();
        JSONObject json = new JSONObject();
        json.put("id", defaulUser.getId())
                .put("username", defaulUser.getUsername())
                .put("name", name)
                .put("lastname", true);
        int statusCode = controller.updateUserNegativeCase(json.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Update user (name is number). Negative case.", priority = 160)
    public void updateUserInvalidNameAsNumberTest() throws IOException {
        UserController controller = new UserController();
        User defaulUser = controller.getUser(token);
        String lastname = new Faker().name().lastName();
        JSONObject json = new JSONObject();
        json.put("id", defaulUser.getId())
                .put("username", defaulUser.getUsername())
                .put("name", 15)
                .put("lastname", lastname);
        int statusCode = controller.updateUserNegativeCase(json.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Update user (lastname is number). Negative case.", priority = 170)
    public void updateUserInvalidLastNameAsNumberTest() throws IOException {
        UserController controller = new UserController();
        User defaulUser = controller.getUser(token);
        String name = new Faker().name().firstName();
        JSONObject json = new JSONObject();
        json.put("id", defaulUser.getId())
                .put("username", defaulUser.getUsername())
                .put("name", name)
                .put("lastname", 15);
        int statusCode = controller.updateUserNegativeCase(json.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Update user (name is empty string). Negative case.", priority = 180)
    public void updateUserInvalidNameAsEmptyStringTest() throws IOException {
        UserController controller = new UserController();
        User defaulUser = controller.getUser(token);
        String lastname = new Faker().name().lastName();
        JSONObject json = new JSONObject();
        json.put("id", defaulUser.getId())
                .put("username", defaulUser.getUsername())
                .put("name", "")
                .put("lastname", lastname);
        int statusCode = controller.updateUserNegativeCase(json.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Update user (lastname is empty string). Negative case.", priority = 190)
    public void updateUserInvalidLastNameAsEmptyStringTest() throws IOException {
        UserController controller = new UserController();
        User defaulUser = controller.getUser(token);
        String name = new Faker().name().firstName();
        JSONObject json = new JSONObject();
        json.put("id", defaulUser.getId())
                .put("username", defaulUser.getUsername())
                .put("name", name)
                .put("lastname", "");
        int statusCode = controller.updateUserNegativeCase(json.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
}