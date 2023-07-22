package com.gmail.ivanytskyy.vitaliy.rest;

import com.gmail.ivanytskyy.vitaliy.utils.UserAuthorizationService;
import org.testng.annotations.BeforeSuite;

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
}