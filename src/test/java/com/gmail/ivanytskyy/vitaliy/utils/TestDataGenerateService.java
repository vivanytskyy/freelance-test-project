package com.gmail.ivanytskyy.vitaliy.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 25/07/2023
 */
public class TestDataGenerateService {
    private static final DecimalFormat DECIMAL_FORMAT;
    static {
        DECIMAL_FORMAT = new DecimalFormat("0.00");
        DECIMAL_FORMAT.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));
    }
    public static double genPrice(int maxValue){
        if(maxValue < 1)
            throw new IllegalArgumentException("Incorrect input values");
        Random random = new Random();
        String randomPrice = DECIMAL_FORMAT.format(random.nextDouble() * maxValue);
        return Double.parseDouble(randomPrice);
    }
}