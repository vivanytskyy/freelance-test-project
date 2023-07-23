package com.gmail.ivanytskyy.vitaliy.unit;

import com.gmail.ivanytskyy.vitaliy.utils.PasswordGenerateService;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 23/07/2023
 */
public class PasswordGenerateServiceTest {
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String PUNCTUATION_SYMBOLS = "!@#$%&*()_+-=[]|,./?><";
    private PasswordGenerateService.Builder serviceBuilder;
    @BeforeMethod
    public void setUp(){
        this.serviceBuilder = new PasswordGenerateService.Builder();
    }
    @Test(description = "Generate the password. Positive case")
    public void generatePasswordTest(){
        String password = serviceBuilder
                .useLowerCaseLetters(true)
                .useUpperCaseLetters(true)
                .useDigits(true)
                .usePunctuationSymbols(true)
                .build()
                .generatePassword(20);
        Assert.assertEquals(password.length(), 20, "Password length incorrect");
        boolean isLowerCaseExist = isSymbolExist(password, LOWERCASE_LETTERS);
        boolean isUpperCaseExist = isSymbolExist(password, UPPERCASE_LETTERS);
        boolean isDigitExist = isSymbolExist(password, DIGITS);
        boolean isPunctuationSymbolExist = isSymbolExist(password, PUNCTUATION_SYMBOLS);
        Assert.assertTrue(isLowerCaseExist
                && isUpperCaseExist
                && isDigitExist
                && isPunctuationSymbolExist);
    }
    @Test(description = "Generate the password (only lowercase letters). Positive case.")
    public void generatePasswordOnlyLowercaseLettersTest(){
        String password = serviceBuilder
                .useLowerCaseLetters(true)
                .useUpperCaseLetters(false)
                .useDigits(false)
                .usePunctuationSymbols(false)
                .build()
                .generatePassword(5);
        Assert.assertEquals(password.length(), 5, "Password length incorrect");
        boolean isLowerCaseExist = isSymbolExist(password, LOWERCASE_LETTERS);
        boolean isUpperCaseExist = isSymbolExist(password, UPPERCASE_LETTERS);
        boolean isDigitExist = isSymbolExist(password, DIGITS);
        boolean isPunctuationSymbolExist = isSymbolExist(password, PUNCTUATION_SYMBOLS);
        Assert.assertTrue(isLowerCaseExist &&
                !(isUpperCaseExist || isDigitExist || isPunctuationSymbolExist));
    }
    @Test(description = "Generate the password (only uppercase letters). Positive case.")
    public void generatePasswordOnlyUppercaseLettersTest(){
        String password = serviceBuilder
                .useLowerCaseLetters(false)
                .useUpperCaseLetters(true)
                .useDigits(false)
                .usePunctuationSymbols(false)
                .build()
                .generatePassword(7);
        Assert.assertEquals(password.length(), 7, "Password length incorrect");
        boolean isLowerCaseExist = isSymbolExist(password, LOWERCASE_LETTERS);
        boolean isUpperCaseExist = isSymbolExist(password, UPPERCASE_LETTERS);
        boolean isDigitExist = isSymbolExist(password, DIGITS);
        boolean isPunctuationSymbolExist = isSymbolExist(password, PUNCTUATION_SYMBOLS);
        Assert.assertTrue(isUpperCaseExist &&
                !(isLowerCaseExist || isDigitExist || isPunctuationSymbolExist));
    }
    @Test(description = "Generate the password (only digits). Positive case.")
    public void generatePasswordOnlyDigitsTest(){
        String password = serviceBuilder
                .useLowerCaseLetters(false)
                .useUpperCaseLetters(false)
                .useDigits(true)
                .usePunctuationSymbols(false)
                .build()
                .generatePassword(10);
        Assert.assertEquals(password.length(), 10, "Password length incorrect");
        boolean isLowerCaseExist = isSymbolExist(password, LOWERCASE_LETTERS);
        boolean isUpperCaseExist = isSymbolExist(password, UPPERCASE_LETTERS);
        boolean isDigitExist = isSymbolExist(password, DIGITS);
        boolean isPunctuationSymbolExist = isSymbolExist(password, PUNCTUATION_SYMBOLS);
        Assert.assertTrue(isDigitExist &&
                !(isUpperCaseExist || isLowerCaseExist || isPunctuationSymbolExist));
    }
    @Test(description = "Generate the password (only punctuation symbols). Positive case.")
    public void generatePasswordOnlyPunctuationSymbolsTest(){
        String password = serviceBuilder
                .useLowerCaseLetters(false)
                .useUpperCaseLetters(false)
                .useDigits(false)
                .usePunctuationSymbols(true)
                .build()
                .generatePassword(15);
        Assert.assertEquals(password.length(), 15, "Password length incorrect");
        boolean isLowerCaseExist = isSymbolExist(password, LOWERCASE_LETTERS);
        boolean isUpperCaseExist = isSymbolExist(password, UPPERCASE_LETTERS);
        boolean isDigitExist = isSymbolExist(password, DIGITS);
        boolean isPunctuationSymbolExist = isSymbolExist(password, PUNCTUATION_SYMBOLS);
        Assert.assertTrue(isPunctuationSymbolExist &&
                !(isLowerCaseExist || isUpperCaseExist || isDigitExist));
    }
    @Test(description = "Generate the password (min length = 4). Positive case")
    public void generatePasswordMinLengthValueTest(){
        String password = serviceBuilder
                .useLowerCaseLetters(true)
                .useUpperCaseLetters(true)
                .useDigits(true)
                .usePunctuationSymbols(true)
                .build()
                .generatePassword(4);
        Assert.assertEquals(password.length(), 4, "Password length incorrect");
        boolean isLowerCaseExist = isSymbolExist(password, LOWERCASE_LETTERS);
        boolean isUpperCaseExist = isSymbolExist(password, UPPERCASE_LETTERS);
        boolean isDigitExist = isSymbolExist(password, DIGITS);
        boolean isPunctuationSymbolExist = isSymbolExist(password, PUNCTUATION_SYMBOLS);
        Assert.assertTrue(isLowerCaseExist
                && isUpperCaseExist
                && isDigitExist
                && isPunctuationSymbolExist);
    }
    @Test(description = "Generate the password (max length = 100). Positive case")
    public void generatePasswordMaxLengthValueTest(){
        String password = serviceBuilder
                .useLowerCaseLetters(true)
                .useUpperCaseLetters(true)
                .useDigits(true)
                .usePunctuationSymbols(true)
                .build()
                .generatePassword(100);
        Assert.assertEquals(password.length(), 100, "Password length incorrect");
        boolean isLowerCaseExist = isSymbolExist(password, LOWERCASE_LETTERS);
        boolean isUpperCaseExist = isSymbolExist(password, UPPERCASE_LETTERS);
        boolean isDigitExist = isSymbolExist(password, DIGITS);
        boolean isPunctuationSymbolExist = isSymbolExist(password, PUNCTUATION_SYMBOLS);
        Assert.assertTrue(isLowerCaseExist
                && isUpperCaseExist
                && isDigitExist
                && isPunctuationSymbolExist);
    }
    @Test(description = "Generate the password (length less then min value). Negative case",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Incorrect length value: 3")
    public void generatePasswordLengthLessThenMinValueNegativeTest(){
        serviceBuilder
                .useLowerCaseLetters(true)
                .useUpperCaseLetters(true)
                .useDigits(true)
                .usePunctuationSymbols(true)
                .build()
                .generatePassword(3);
    }
    @Test(description = "Generate the password (length more then max value). Negative case",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Incorrect length value: 101")
    public void generatePasswordLengthMoreThenMaxValueNegativeTest(){
        serviceBuilder
                .useLowerCaseLetters(true)
                .useUpperCaseLetters(true)
                .useDigits(true)
                .usePunctuationSymbols(true)
                .build()
                .generatePassword(101);
    }
    @Test(description = "Generate the password (options for builder wasn't chosen). Negative case",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Object wasn't built correctly. Add the necessary options")
    public void generatePasswordDidNotChooseAnyOptionsInBuilderNegativeTest(){
        serviceBuilder
                .useLowerCaseLetters(false)
                .useUpperCaseLetters(false)
                .useDigits(false)
                .usePunctuationSymbols(false)
                .build()
                .generatePassword(8);
    }
    @AfterMethod
    public void tearDown(){
        serviceBuilder = null;
    }
    private boolean isSymbolExist(String password, String symbols){
        char[] passwordAsChars = password.toCharArray();
        for (char symbol : passwordAsChars) {
            if(symbols.indexOf(symbol) != -1) return true;
        }
        return false;
    }
}
