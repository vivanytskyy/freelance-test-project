package com.gmail.ivanytskyy.vitaliy.pages.selenide;

import com.github.javafaker.Faker;
import com.gmail.ivanytskyy.vitaliy.utils.CredentialPropertiesSupplier;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 27/07/2023
 */
public class FirstTaskTest extends BaseTest{
    @Test(description = "Update profile. Positive test.", priority = 10)
    public void updateProfileTest(){
        String username = CredentialPropertiesSupplier.getInstance().getProperty("username");
        String password = CredentialPropertiesSupplier.getInstance().getProperty("password");
        Faker faker = new Faker();
        String newName = faker.name().firstName();
        String newLastName = faker.name().lastName();
        String newUserFullName = String.format("%s %s", newName, newLastName);
        String correctedUserFullName = openApp()
                .openLoginPage()
                .loginPositiveCase(username, password)
                .openUserPanel()
                .clickProfileButton()
                .clickEditInfoButton()
                .updateUserData(newName, newLastName)
                .getUserFullName();
        Assert.assertEquals(correctedUserFullName, newUserFullName);
    }
}