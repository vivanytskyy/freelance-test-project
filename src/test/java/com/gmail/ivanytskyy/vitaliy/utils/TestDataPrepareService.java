package com.gmail.ivanytskyy.vitaliy.utils;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Base64;
import java.util.Locale;
import java.util.Random;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 25/07/2023
 */
public class TestDataPrepareService {
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
    public static String encodeImageToBase64(File file) throws IOException {
        int bufferLength = 2048;
        byte[] buffer = new byte[bufferLength];
        byte[] data;
        FileInputStream stream = new FileInputStream(file.getAbsolutePath());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int readLength;
        while ((readLength = stream.read(buffer, 0, bufferLength)) != -1) {
            out.write(buffer, 0, readLength);
        }
        data = out.toByteArray();
        String imageString = Base64.getEncoder().encodeToString(data);
        out.close();
        stream.close();
        return imageString;
    }
}