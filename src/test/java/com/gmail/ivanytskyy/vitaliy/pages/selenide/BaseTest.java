package com.gmail.ivanytskyy.vitaliy.pages.selenide;

import com.codeborne.selenide.Configuration;
import com.gmail.ivanytskyy.vitaliy.utils.TestProperties;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import java.io.IOException;
import static com.codeborne.selenide.Selenide.open;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 27/07/2023
 */
public class BaseTest {
    @BeforeClass
    @Parameters({"browser"})
    public void beforeClass(String browser) throws IOException {
        Configuration.baseUrl = TestProperties.getInstance().getProperty("base_url");
        Configuration.browser = browser;
    }
    protected HomePage openApp(){
        open(Configuration.baseUrl);
        return new HomePage();
    }
}