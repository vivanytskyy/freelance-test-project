package com.gmail.ivanytskyy.vitaliy.rest;

import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;
import com.github.javafaker.Faker;
import com.gmail.ivanytskyy.vitaliy.rest.entities.Job;
import com.gmail.ivanytskyy.vitaliy.utils.TokenHolder;
import com.gmail.ivanytskyy.vitaliy.utils.UserAuthorizationService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import static com.gmail.ivanytskyy.vitaliy.utils.TestDataPrepareService.genPrice;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 20/07/2023
 */
@Listeners({ExtentITestListenerClassAdapter.class})
public class BaseTest {
    protected String token;
    static {
        System.setProperty("extent.reporter.html.start", "true");
        System.setProperty("extent.reporter.html.out", "target/extentReport/RestExtentHtml.html");
    }

    @BeforeSuite
    public void authorizeUser(){
        UserAuthorizationService.authorize();
    }
    @BeforeClass
    public void beforeClass(){
        this.token = TokenHolder.getInstance().getToken();
    }
    @AfterClass
    public void afterClass(){
        this.token = null;
    }
    protected Job createRandomJob(){
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