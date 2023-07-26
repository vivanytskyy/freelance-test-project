package com.gmail.ivanytskyy.vitaliy.rest;

import static com.gmail.ivanytskyy.vitaliy.utils.HttpResponseCodeRanges.HTTP_400th;
import static com.gmail.ivanytskyy.vitaliy.utils.TestDataPrepareService.*;
import com.github.javafaker.Faker;
import com.gmail.ivanytskyy.vitaliy.rest.controllers.JobController;
import com.gmail.ivanytskyy.vitaliy.rest.entities.Job;
import com.gmail.ivanytskyy.vitaliy.rest.exceptions.UnexpectedHttpStatusCodeException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 24/07/2023
 */
public class JobTest extends BaseTest{
    private static final String JOB_DELETED_SUCCESS_MESSAGE = "Job is deleted";

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws IOException {
        JobController jobController = new JobController();
        Job[] jobs = jobController.findAllJobsForUser(token);
        for (Job jobItem : jobs){
            jobController.deleteJobById(jobItem.getId(), token);
        }
    }
    @Test(description = "Create job. Positive case.", priority = 10)
    public void createJobTest() throws IOException {
        int code = new JobController().createJob(createRandomJob(), token);
        Assert.assertEquals(code, 200);
    }
    @Test(description = "Find job by id. Positive case.", priority = 20)
    public void findJobByIdTest() throws IOException {
        final Job job = createRandomJob();
        JobController jobController = new JobController();
        jobController.createJob(job, token);
        Job[] jobs = jobController.findAllJobsForUser(token);
        long jobId = Arrays.stream(jobs)
                .filter(n -> n.getTitle().equals(job.getTitle()))
                .filter(n -> n.getDescription().equals(job.getDescription()))
                .filter(n -> n.getPrice().equals(job.getPrice()))
                .map(Job::getId)
                .findFirst()
                .orElse(-1L);
        Job resultJob = jobController.findJobById(jobId, token);
        Assert.assertEquals(resultJob, job);
    }
    @Test(description = "Find all jobs for user. Positive case.", priority = 30)
    public void findAllJobsForUserTest() throws IOException {
        JobController jobController = new JobController();
        int numberOfJobs = 5;
        List<Job> jobs = new ArrayList<>();
        for(int i = 0; i < numberOfJobs; i++){
            Job job = createRandomJob();
            jobs.add(job);
            jobController.createJob(job, token);
        }
        List<Job> resultJobs = Arrays.asList(jobController.findAllJobsForUser(token));
        for(Job job : jobs){
            Assert.assertTrue(resultJobs.contains(job));
        }
    }
    @Test(description = "Find all jobs. Positive case.", priority = 40)
    public void findAllJobsTest() throws IOException {
        JobController jobController = new JobController();
        int numberOfJobs = 5;
        List<Job> jobs = new ArrayList<>();
        for(int i = 0; i < numberOfJobs; i++){
            Job job = createRandomJob();
            jobs.add(job);
            jobController.createJob(job, token);
        }
        List<Job> resultJobs = Arrays.asList(jobController.findAllJobs(token));
        Assert.assertTrue(resultJobs.size() > jobs.size());
    }
    @Test(description = "Delete job by id. Positive case.", priority = 50)
    public void deleteJobByIdTest() throws IOException {
        final Job job = createRandomJob();
        JobController jobController = new JobController();
        jobController.createJob(job, token);
        Job[] jobs = jobController.findAllJobsForUser(token);
        long jobId = Arrays.stream(jobs)
                .filter(n -> n.getTitle().equals(job.getTitle()))
                .filter(n -> n.getDescription().equals(job.getDescription()))
                .filter(n -> n.getPrice().equals(job.getPrice()))
                .map(Job::getId)
                .findFirst()
                .orElse(-1L);
        String resultMessage = jobController.deleteJobById(jobId, token);
        Assert.assertEquals(resultMessage, JOB_DELETED_SUCCESS_MESSAGE);
    }
    @Test(description = "Create job by invalid token. Negative case.", priority = 60,
            expectedExceptions = UnexpectedHttpStatusCodeException.class)
    public void createJobByInvalidTokenTest() throws IOException {
        JobController jobController = new JobController();
        String validToken = token;
        String invalidToken = validToken.substring(0, validToken.length() - 1);
        jobController.createJob(createRandomJob(), invalidToken);
    }
    @Test(description = "Find job by id with invalid token. Negative case.", priority = 70,
            expectedExceptions = UnexpectedHttpStatusCodeException.class)
    public void findJobByIdWithInvalidTokenTest() throws IOException {
        String validToken = token;
        String invalidToken = validToken.substring(0, validToken.length() - 1);
        final Job job = createRandomJob();
        JobController jobController = new JobController();
        jobController.createJob(job, validToken);
        Job[] jobs = jobController.findAllJobsForUser(validToken);
        long jobId = Arrays.stream(jobs)
                .filter(n -> n.getTitle().equals(job.getTitle()))
                .filter(n -> n.getDescription().equals(job.getDescription()))
                .filter(n -> n.getPrice().equals(job.getPrice()))
                .map(Job::getId)
                .findFirst()
                .orElse(-1L);
        jobController.findJobById(jobId, invalidToken);
    }
    @Test(description = "Find all jobs for user with invalid token. Negative case.", priority = 80,
            expectedExceptions = UnexpectedHttpStatusCodeException.class)
    public void findAllJobsForUserWithInvalidTokenTest() throws IOException {
        String validToken = token;
        String invalidToken = validToken.substring(0, validToken.length() - 1);
        JobController jobController = new JobController();
        int numberOfJobs = 5;
        for(int i = 0; i < numberOfJobs; i++){
            Job job = createRandomJob();
            jobController.createJob(job, validToken);
        }
        jobController.findAllJobsForUser(invalidToken);
    }
    @Test(description = "Find all jobs with invalid token. Negative case.", priority = 90,
            expectedExceptions = UnexpectedHttpStatusCodeException.class)
    public void findAllJobsWithInvalidTokenTest() throws IOException {
        String validToken = token;
        String invalidToken = validToken.substring(0, validToken.length() - 1);
        JobController jobController = new JobController();
        int numberOfJobs = 5;
        for(int i = 0; i < numberOfJobs; i++){
            Job job = createRandomJob();
            jobController.createJob(job, validToken);
        }
        jobController.findAllJobs(invalidToken);
    }
    @Test(description = "Delete job by id with invalid token. Negative case.", priority = 100,
            expectedExceptions = UnexpectedHttpStatusCodeException.class)
    public void deleteJobByIdWithInvalidTokenTest() throws IOException {
        String validToken = token;
        String invalidToken = validToken.substring(0, validToken.length() - 1);
        final Job job = createRandomJob();
        JobController jobController = new JobController();
        jobController.createJob(job, validToken);
        Job[] jobs = jobController.findAllJobsForUser(validToken);
        long jobId = Arrays.stream(jobs)
                .filter(n -> n.getTitle().equals(job.getTitle()))
                .filter(n -> n.getDescription().equals(job.getDescription()))
                .filter(n -> n.getPrice().equals(job.getPrice()))
                .map(Job::getId)
                .findFirst()
                .orElse(-1L);
        jobController.deleteJobById(jobId, invalidToken);
    }
    @Test(description = "Create job (title = null). Negative case.", priority = 110)
    public void createJobInvalidTitleAsNullTest() throws IOException {
        final String description = new Faker().job().field();
        final Double price = genPrice(1000);
        JSONObject invalidJson = new JSONObject();
        invalidJson.put("id", 15L)
                .put("title", JSONObject.NULL)
                .put("description", description)
                .put("price", price)
                .put("user", "fake-user")
                .put("noOfComments", 5);
        int statusCode = new JobController().createJobNegativeCase(invalidJson.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Create job (description = null). Negative case.", priority = 120)
    public void createJobInvalidDescriptionAsNullTest() throws IOException {
        final String title = new Faker().job().title();
        final Double price = genPrice(1000);
        JSONObject invalidJson = new JSONObject();
        invalidJson.put("id", 15L)
                .put("title", title)
                .put("description", JSONObject.NULL)
                .put("price", price)
                .put("user", "fake-user")
                .put("noOfComments", 5);
        int statusCode = new JobController().createJobNegativeCase(invalidJson.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Create job (price = null). Negative case.", priority = 130)
    public void createJobInvalidPriceAsNullTest() throws IOException {
        final String title = new Faker().job().title();
        final String description = new Faker().job().field();
        JSONObject invalidJson = new JSONObject();
        invalidJson.put("id", 15L)
                .put("title", title)
                .put("description", description)
                .put("price", JSONObject.NULL)
                .put("user", "fake-user")
                .put("noOfComments", 5);
        int statusCode = new JobController().createJobNegativeCase(invalidJson.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Create job (title = true). Negative case.", priority = 140)
    public void createJobInvalidTitleAsBooleanTest() throws IOException {
        final String description = new Faker().job().field();
        final Double price = genPrice(1000);
        JSONObject invalidJson = new JSONObject();
        invalidJson.put("id", 15L)
                .put("title", true)
                .put("description", description)
                .put("price", price)
                .put("user", "fake-user")
                .put("noOfComments", 5);
        int statusCode = new JobController().createJobNegativeCase(invalidJson.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Create job (description = false). Negative case.", priority = 150)
    public void createJobInvalidDescriptionAsBooleanTest() throws IOException {
        final String title = new Faker().job().title();
        final Double price = genPrice(1000);
        JSONObject invalidJson = new JSONObject();
        invalidJson.put("id", 15L)
                .put("title", title)
                .put("description", false)
                .put("price", price)
                .put("user", "fake-user")
                .put("noOfComments", 5);
        int statusCode = new JobController().createJobNegativeCase(invalidJson.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Create job (price = true). Negative case.", priority = 160)
    public void createJobInvalidPriceAsBooleanTest() throws IOException {
        final String title = new Faker().job().title();
        final String description = new Faker().job().field();
        JSONObject invalidJson = new JSONObject();
        invalidJson.put("id", 15L)
                .put("title", title)
                .put("description", description)
                .put("price", true)
                .put("user", "fake-user")
                .put("noOfComments", 5);
        int statusCode = new JobController().createJobNegativeCase(invalidJson.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Create job (title is number). Negative case.", priority = 170)
    public void createJobInvalidTitleAsNumberTest() throws IOException {
        final String description = new Faker().job().field();
        final Double price = genPrice(1000);
        JSONObject invalidJson = new JSONObject();
        invalidJson.put("id", 15L)
                .put("title", 16)
                .put("description", description)
                .put("price", price)
                .put("user", "fake-user")
                .put("noOfComments", 5);
        int statusCode = new JobController().createJobNegativeCase(invalidJson.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Create job (description is number). Negative case.", priority = 180)
    public void createJobInvalidDescriptionAsNumberTest() throws IOException {
        final String title = new Faker().job().title();
        final Double price = genPrice(1000);
        JSONObject invalidJson = new JSONObject();
        invalidJson.put("id", 15L)
                .put("title", title)
                .put("description", 36)
                .put("price", price)
                .put("user", "fake-user")
                .put("noOfComments", 5);
        int statusCode = new JobController().createJobNegativeCase(invalidJson.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Create job (price is number as string). Negative case.", priority = 190)
    public void createJobInvalidPriceIsNumberAsStringTest() throws IOException {
        final String title = new Faker().job().title();
        final String description = new Faker().job().field();
        JSONObject invalidJson = new JSONObject();
        invalidJson.put("id", 15L)
                .put("title", title)
                .put("description", description)
                .put("price", "111")
                .put("user", "fake-user")
                .put("noOfComments", 5);
        int statusCode = new JobController().createJobNegativeCase(invalidJson.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Create job (price is string). Negative case.", priority = 200)
    public void createJobInvalidPriceAsStringTest() throws IOException {
        final String title = new Faker().job().title();
        final String description = new Faker().job().field();
        JSONObject invalidJson = new JSONObject();
        invalidJson.put("id", 15L)
                .put("title", title)
                .put("description", description)
                .put("price", "string")
                .put("user", "fake-user")
                .put("noOfComments", 5);
        int statusCode = new JobController().createJobNegativeCase(invalidJson.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Create job (price is negative). Negative case.", priority = 210)
    public void createJobInvalidPriceIsNegativeTest() throws IOException {
        final String title = new Faker().job().title();
        final String description = new Faker().job().field();
        JSONObject invalidJson = new JSONObject();
        invalidJson.put("id", 15L)
                .put("title", title)
                .put("description", description)
                .put("price", -25)
                .put("user", "fake-user")
                .put("noOfComments", 5);
        int statusCode = new JobController().createJobNegativeCase(invalidJson.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Create job (title is empty string). Negative case.", priority = 220)
    public void createJobInvalidTitleAsEmptyStringTest() throws IOException {
        final String description = new Faker().job().field();
        final Double price = genPrice(1000);
        JSONObject invalidJson = new JSONObject();
        invalidJson.put("id", 15L)
                .put("title", "")
                .put("description", description)
                .put("price", price)
                .put("user", "fake-user")
                .put("noOfComments", 5);
        int statusCode = new JobController().createJobNegativeCase(invalidJson.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Create job (description is empty string). Negative case.", priority = 230)
    public void createJobInvalidDescriptionAsEmptyStringTest() throws IOException {
        final String title = new Faker().job().title();
        final Double price = genPrice(1000);
        JSONObject invalidJson = new JSONObject();
        invalidJson.put("id", 15L)
                .put("title", title)
                .put("description", "")
                .put("price", price)
                .put("user", "fake-user")
                .put("noOfComments", 5);
        int statusCode = new JobController().createJobNegativeCase(invalidJson.toString(), token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Get job by invalid id (id is negative number). Negative case.", priority = 240)
    public void findJodByInvalidIdAsNegativeNumberTest() throws IOException {
        JobController controller = new JobController();
        int statusCode = controller.findJobByIdNegativeCase("-1", token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Get job by invalid id (id is 0). Negative case.", priority = 250)
    public void findJobByInvalidIdAsZeroTest() throws IOException {
        JobController controller = new JobController();
        int statusCode = controller.findJobByIdNegativeCase("0", token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
    @Test(description = "Get job by id (job doesn't exist). Negative case.", priority = 260)
    public void findJobByInvalidIdJobDoesNotExistTest() throws IOException {
        JobController controller = new JobController();
        int statusCode = controller.findJobByIdNegativeCase(Long.MAX_VALUE + "", token);
        Assert.assertTrue(HTTP_400th.inRange(statusCode),
                "Incorrect HTTP response status code " + statusCode);
    }
}