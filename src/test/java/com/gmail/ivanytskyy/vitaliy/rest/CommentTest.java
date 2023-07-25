package com.gmail.ivanytskyy.vitaliy.rest;

import com.github.javafaker.Faker;
import com.gmail.ivanytskyy.vitaliy.rest.controllers.CommentController;
import com.gmail.ivanytskyy.vitaliy.rest.controllers.JobController;
import com.gmail.ivanytskyy.vitaliy.rest.controllers.UserController;
import com.gmail.ivanytskyy.vitaliy.rest.entities.Comment;
import com.gmail.ivanytskyy.vitaliy.rest.entities.Job;
import com.gmail.ivanytskyy.vitaliy.rest.entities.User;
import com.gmail.ivanytskyy.vitaliy.utils.TokenHolder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.gmail.ivanytskyy.vitaliy.utils.TestDataPrepareService.genPrice;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 25/07/2023
 */
public class CommentTest extends BaseTest{
    private User user;
    private Long jobId;
    private String token;

    @BeforeMethod
    public void setUp() throws IOException {
        this.token = TokenHolder.getInstance().getToken();
        this.user = new UserController().getUser(token);
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
        String localToken = token;
        Long localJobId = jobId;
        token = null;
        jobId = null;
        user = null;
        JobController jobController = new JobController();
        jobController.deleteJobById(localJobId, localToken);
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
}