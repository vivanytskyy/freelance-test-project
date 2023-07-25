package com.gmail.ivanytskyy.vitaliy.rest;

import static com.gmail.ivanytskyy.vitaliy.utils.TestDataPrepareService.*;
import com.github.javafaker.Faker;
import com.gmail.ivanytskyy.vitaliy.rest.controllers.JobController;
import com.gmail.ivanytskyy.vitaliy.rest.controllers.UserController;
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

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 24/07/2023
 */
public class JobTest extends BaseTest{
    private User user;
    private String token;
    private static final String JOB_DELETED_SUCCESS_MESSAGE = "Job is deleted";
    @BeforeMethod
    public void setUp() throws IOException {
        token = TokenHolder.getInstance().getToken();
        user = new UserController().getUser(token);
    }
    @Test(description = "Create job. Positive case.", priority = 10)
    public void createJobTest() throws IOException {
        int code = new JobController().createJob(createRandomJob(), TokenHolder.getInstance().getToken());
        Assert.assertEquals(code, 200);
    }
    @Test(description = "Find job by id. Positive case.", priority = 20)
    public void findJobByIdTest() throws IOException {
        final Job job = createRandomJob();
        JobController jobController = new JobController();
        jobController.createJob(job, TokenHolder.getInstance().getToken());
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
        jobController.createJob(job, TokenHolder.getInstance().getToken());
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
    @AfterMethod(alwaysRun = true)
    public void tearDown() throws IOException {
        String localToken = token;
        token = null;
        user = null;
        JobController jobController = new JobController();
        Job[] jobs = jobController.findAllJobsForUser(localToken);
        for (Job jobItem : jobs){
            jobController.deleteJobById(jobItem.getId(), localToken);
        }
    }
    private Job createRandomJob(){
        final String title = new Faker().job().title();
        final String description = new Faker().job().field();
        final Double price = genPrice(1000);
        return Job.builder()
                .title(title)
                .description(description)
                .price(price)
                .build();
    }
}