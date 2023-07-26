package com.gmail.ivanytskyy.vitaliy.rest;

import com.github.javafaker.Faker;
import com.gmail.ivanytskyy.vitaliy.rest.controllers.CommentController;
import com.gmail.ivanytskyy.vitaliy.rest.controllers.JobController;
import com.gmail.ivanytskyy.vitaliy.rest.entities.Comment;
import com.gmail.ivanytskyy.vitaliy.rest.entities.Job;
import com.gmail.ivanytskyy.vitaliy.rest.exceptions.UnexpectedHttpStatusCodeException;
import com.gmail.ivanytskyy.vitaliy.utils.TokenHolder;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.gmail.ivanytskyy.vitaliy.utils.HttpResponseCodeRanges.*;
import static com.gmail.ivanytskyy.vitaliy.utils.TestDataPrepareService.genPrice;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 25/07/2023
 */
public class CommentTest extends BaseTest{
    private Long jobId;
    private String token;

    @BeforeMethod
    public void setUp() throws IOException {
        this.token = TokenHolder.getInstance().getToken();
        final Job job = Job.builder()
                .title(new Faker().job().title())
                .description(new Faker().job().field())
                .price(genPrice(1000))
                .build();
        JobController jobController = new JobController();
        jobController.createJob(job, token);
        Job[] jobs = jobController.findAllJobsForUser(token);
        this.jobId = Arrays.stream(jobs)
                .filter(n -> n.getTitle().equals(job.getTitle()))
                .filter(n -> n.getDescription().equals(job.getDescription()))
                .filter(n -> n.getPrice().equals(job.getPrice()))
                .map(Job::getId)
                .findFirst()
                .orElse(-1L);
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown() throws IOException {
        JobController jobController = new JobController();
        jobController.deleteJobById(jobId, token);
    }
    @Test(description = "Create comment. Positive case", priority = 10)
    public void createCommentTest() throws IOException {
        String message = new Faker().book().title();
        Comment comment = Comment.builder()
                .message(message)
                .build();
        CommentController controller = new CommentController();
        int code = controller.createComment(comment, jobId, token);
        Assert.assertEquals(code, 200);
    }
    @Test(description = "Find all comments. Positive case", priority = 20)
    public void findAllCommentsByJobIdTest() throws IOException {
        CommentController controller = new CommentController();
        int numberOfComments = 5;
        List<Comment> comments = new ArrayList<>();
        for (int i = 0; i < numberOfComments; i++){
            Comment comment = Comment.builder().message(new Faker().book().title()).build();
            comments.add(comment);
            controller.createComment(comment, jobId, token);
        }
        List<Comment> resultComments = Arrays.asList(controller.findAllCommentsByJobId(jobId, token));
        Assert.assertEquals(resultComments.size(), comments.size());
        for (Comment comment : comments){
            Assert.assertTrue(resultComments.contains(comment));
        }
    }
    @Test(description = "Create comment with invalid token. Negative case.", priority = 30,
            expectedExceptions = UnexpectedHttpStatusCodeException.class)
    public void createCommentWithInvalidTokenTest() throws IOException {
        String message = new Faker().book().title();
        Comment comment = Comment.builder()
                .message(message)
                .build();
        CommentController controller = new CommentController();
        String validToken = token;
        String invalidToken = validToken.substring(0, validToken.length() - 1);
        controller.createComment(comment, jobId, invalidToken);
    }
    @Test(description = "Find all comments with invalid token. Negative case.", priority = 40,
            expectedExceptions = UnexpectedHttpStatusCodeException.class)
    public void findAllCommentsByJobIdWithInvalidTokenTest() throws IOException {
        CommentController controller = new CommentController();
        int numberOfComments = 5;
        for (int i = 0; i < numberOfComments; i++){
            Comment comment = Comment.builder().message(new Faker().book().title()).build();
            controller.createComment(comment, jobId, token);
        }
        String validToken = token;
        String invalidToken = validToken.substring(0, validToken.length() - 1);
        controller.findAllCommentsByJobId(jobId, invalidToken);
    }
    @Test(description = "Create comment with invalid jobId (negative number). Negative case.", priority = 50)
    public void createCommentWithInvalidJobIdAsNegativeNumberTest() throws IOException {
        JSONObject json = new JSONObject()
                .put("id", 15)
                .put("message", new Faker().book().title())
                .put("username", new Faker().name().username());
        int statusCode = new CommentController()
                .createCommentNegativeCase(json.toString(), "-1", token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Create comment with invalid jobId (jobId = 0). Negative case.", priority = 60)
    public void createCommentWithInvalidJobIdAsZeroTest() throws IOException {
        JSONObject json = new JSONObject()
                .put("id", 15)
                .put("message", new Faker().book().title())
                .put("username", new Faker().name().username());
        int statusCode = new CommentController()
                .createCommentNegativeCase(json.toString(), "0", token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Create comment with invalid jobId (job doesn't exist). Negative case.", priority = 70)
    public void createCommentWithInvalidJobIdDoesNotExistTest() throws IOException {
        JSONObject json = new JSONObject()
                .put("id", 15)
                .put("message", new Faker().book().title())
                .put("username", new Faker().name().username());
        int statusCode = new CommentController()
                .createCommentNegativeCase(json.toString(), Long.MAX_VALUE + "", token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Find all comments with invalid jobId (negative number). Negative case.", priority = 80)
    public void findAllCommentsByInvalidJobIdAsNegativeNumberTest() throws IOException {
        int statusCode = new CommentController()
                .findAllCommentsByJobIdNegativeCase("-1", token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Find all comments with invalid jobId (jobId = 0). Negative case.", priority = 90)
    public void findAllCommentsByInvalidJobIdAsZeroTest() throws IOException {
        int statusCode = new CommentController()
                .findAllCommentsByJobIdNegativeCase("0", token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Find all comments with invalid jobId (job doesn't exist). Negative case.",
            priority = 100)
    public void findAllCommentsByInvalidJobIdDoesNotExistTest() throws IOException {
        int statusCode = new CommentController()
                .findAllCommentsByJobIdNegativeCase(Long.MAX_VALUE + "", token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Create comment (message = null). Negative case.", priority = 110)
    public void createCommentInvalidMessageAsNullTest() throws IOException {
        JSONObject json = new JSONObject()
                .put("id", 15)
                .put("message", JSONObject.NULL)
                .put("username", new Faker().name().username());
        int statusCode = new CommentController()
                .createCommentNegativeCase(json.toString(), String.valueOf(jobId), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Create comment (message = true). Negative case.", priority = 120)
    public void createCommentInvalidMessageAsBooleanTest() throws IOException {
        JSONObject json = new JSONObject()
                .put("id", 15)
                .put("message", true)
                .put("username", new Faker().name().username());
        int statusCode = new CommentController()
                .createCommentNegativeCase(json.toString(), String.valueOf(jobId), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Create comment (message = number). Negative case.", priority = 130)
    public void createCommentInvalidMessageAsNumberTest() throws IOException {
        JSONObject json = new JSONObject()
                .put("id", 15)
                .put("message", 25)
                .put("username", new Faker().name().username());
        int statusCode = new CommentController()
                .createCommentNegativeCase(json.toString(), String.valueOf(jobId), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Create comment (message is empty string). Negative case.", priority = 140)
    public void createCommentInvalidMessageAsEmptyStringTest() throws IOException {
        JSONObject json = new JSONObject()
                .put("id", 15)
                .put("message", "")
                .put("username", new Faker().name().username());
        int statusCode = new CommentController()
                .createCommentNegativeCase(json.toString(), String.valueOf(jobId), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
}