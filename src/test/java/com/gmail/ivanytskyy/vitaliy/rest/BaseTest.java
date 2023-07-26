package com.gmail.ivanytskyy.vitaliy.rest;

import com.github.javafaker.Faker;
import com.gmail.ivanytskyy.vitaliy.rest.entities.Job;
import com.gmail.ivanytskyy.vitaliy.utils.UserAuthorizationService;
import org.testng.annotations.BeforeSuite;

import static com.gmail.ivanytskyy.vitaliy.utils.TestDataPrepareService.genPrice;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 20/07/2023
 */
public class BaseTest {
    @BeforeSuite
    public void authorizeUser(){
        UserAuthorizationService.authorize();
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