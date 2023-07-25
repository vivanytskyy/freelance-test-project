package com.gmail.ivanytskyy.vitaliy.unit;

import static com.gmail.ivanytskyy.vitaliy.utils.TestDataPrepareService.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 25/07/2023
 */
public class TestDataPrepareServiceTest {

    @Test(description = "Generate the price. Positive case")
    public void genPriceTest(){
        int maxValue = 1000;
        double price = genPrice(maxValue);
        Assert.assertTrue(price >= 0.00 && price < maxValue);
        int decimalPartLength = String.valueOf(price).split("\\.")[1].length();
        Assert.assertTrue(decimalPartLength <= 2);
    }
    @Test(description = "Generate the price (maxValue = 1). Positive case")
    public void genPriceMinValueTest(){
        int maxValue = 1;
        double price = genPrice(maxValue);
        Assert.assertTrue(price >= 0.00 && price < maxValue);
        int decimalPartLength = String.valueOf(price).split("\\.")[1].length();
        Assert.assertTrue(decimalPartLength <= 2);
    }
    @Test(description = "Generate the price (maxValue = Integer.MAX_VALUE). Positive case")
    public void genPriceMaxValueTest(){
        int maxValue = Integer.MAX_VALUE;
        double price = genPrice(maxValue);
        Assert.assertTrue(price >= 0.00 && price < maxValue);
    }
    @Test(description = "Generate the price (invalid maxValue < 1). Negative case",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Incorrect input values")
    public void genPriceMaxValueLessOneTest(){
        int maxValue = 0;
        genPrice(maxValue);
    }
    @Test(description = "Generate the price (invalid maxValue < 0). Negative case",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Incorrect input values")
    public void genPriceMaxValueLessZeroTest(){
        int maxValue = -1;
        genPrice(maxValue);
    }
}