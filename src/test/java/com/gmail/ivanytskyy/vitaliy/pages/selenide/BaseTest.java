package com.gmail.ivanytskyy.vitaliy.pages.selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.gmail.ivanytskyy.vitaliy.listeners.UISelenideExtentReportsListener;
import com.gmail.ivanytskyy.vitaliy.utils.CredentialPropertiesSupplier;
import com.gmail.ivanytskyy.vitaliy.utils.TestProperties;
import com.gmail.ivanytskyy.vitaliy.utils.TokenHolder;
import com.gmail.ivanytskyy.vitaliy.utils.UserAuthorizationService;
import org.testng.annotations.*;
import java.io.IOException;
import static com.codeborne.selenide.Selenide.open;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 27/07/2023
 */
@Listeners({UISelenideExtentReportsListener.class})
public class BaseTest {
    protected String token;
    private static final String USERNAME;
    private static final String PASSWORD;
    static {
        USERNAME = CredentialPropertiesSupplier.getInstance().getProperty("username");
        PASSWORD = CredentialPropertiesSupplier.getInstance().getProperty("password");
    }
    @BeforeTest
    public void authorizeUser(){
        UserAuthorizationService.authorize();
    }
    @BeforeClass
    @Parameters({"browser"})
    public void beforeClass(String browser) throws IOException {
        Configuration.baseUrl = TestProperties.getInstance().getProperty("base_url");
        Configuration.browser = browser;
        Configuration.timeout = 8000;
        this.token = TokenHolder.getInstance().getToken();
    }
    @AfterClass
    public void afterClass(){
        this.token = null;
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown() throws IOException {
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.sessionStorage().clear();
    }
    protected HomePage openApp(){
        open(Configuration.baseUrl);
        return new HomePage();
    }
    protected String getUsername(){
        return USERNAME;
    }
    protected String getPassword(){
        return PASSWORD;
    }
}