package com.gmail.ivanytskyy.vitaliy.utils;

import java.util.Random;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 23/07/2023
 */
public final class PasswordGenerateService {
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String PUNCTUATION_SYMBOLS = "!@#$%&*()_+-=[]|,./?><";
    private final boolean isLowerCaseLettersNecessary;
    private final boolean isUpperCaseLettersNecessary;
    private final boolean isDigitsNecessary;
    private final boolean isPunctuationSymbolsNecessary;
    private PasswordGenerateService(Builder builder){
        this.isLowerCaseLettersNecessary = builder.isLowerCaseLettersNecessary;
        this.isUpperCaseLettersNecessary = builder.isUpperCaseLettersNecessary;
        this.isDigitsNecessary = builder.isDigitsNecessary;
        this.isPunctuationSymbolsNecessary = builder.isPunctuationSymbolsNecessary;

    }
    public static class Builder{
        private boolean isLowerCaseLettersNecessary;
        private boolean isUpperCaseLettersNecessary;
        private boolean isDigitsNecessary;
        private boolean isPunctuationSymbolsNecessary;
        public Builder(){
            this.isLowerCaseLettersNecessary = false;
            this.isUpperCaseLettersNecessary = false;
            this.isDigitsNecessary = false;
            this.isPunctuationSymbolsNecessary = false;
        }
        public Builder useLowerCaseLetters(boolean useLowerCaseLetters){
            this.isLowerCaseLettersNecessary = useLowerCaseLetters;
            return this;
        }
        public Builder useUpperCaseLetters(boolean useUpperCaseLetters){
            this.isUpperCaseLettersNecessary = useUpperCaseLetters;
            return this;
        }
        public Builder useDigits(boolean useDigits){
            this.isDigitsNecessary = useDigits;
            return this;
        }
        public Builder usePunctuationSymbols(boolean usePunctuationSymbols){
            this.isPunctuationSymbolsNecessary = usePunctuationSymbols;
            return this;
        }
        public PasswordGenerateService build(){
            if (!(this.isLowerCaseLettersNecessary
                    || this.isUpperCaseLettersNecessary
                    || this.isDigitsNecessary
                    || this.isPunctuationSymbolsNecessary)){
                throw new IllegalArgumentException ("Object wasn't built correctly. Add the necessary options");
            }
            return new PasswordGenerateService(this);
        }
    }
    public String generatePassword(int length){
        if(length < 4 || length > 100)
            throw new IllegalArgumentException("Incorrect length value: " + length);
        StringBuilder sbPassword = new StringBuilder();
        sbPassword.append(generateBasePassword());
        int tail = length - sbPassword.length();
        if(tail == 0) return sbPassword.toString();
        StringBuilder sbBasket = new StringBuilder();
        if(isLowerCaseLettersNecessary) sbBasket.append(LOWERCASE_LETTERS);
        if(isUpperCaseLettersNecessary) sbBasket.append(UPPERCASE_LETTERS);
        if(isDigitsNecessary) sbBasket.append(DIGITS);
        if(isPunctuationSymbolsNecessary) sbBasket.append(PUNCTUATION_SYMBOLS);
        Random random = new Random();
        for(int i = 0; i < tail; i++){
            int charNumber = random.nextInt(0, sbBasket.length());
            sbPassword.append(sbBasket.charAt(charNumber));
        }
        return sbPassword.toString();
    }
    private char generateRandomChar(String symbols){
        if (symbols == null || symbols.isEmpty() || symbols.isBlank())
            throw new IllegalArgumentException("Incorrect input values");
        Random random = new Random();
        return symbols.charAt(random.nextInt(0, symbols.length()));
    }
    private String generateBasePassword(){
        StringBuilder sb = new StringBuilder();
        if(isLowerCaseLettersNecessary) sb.append(generateRandomChar(LOWERCASE_LETTERS));
        if(isUpperCaseLettersNecessary) sb.append(generateRandomChar(UPPERCASE_LETTERS));
        if(isDigitsNecessary) sb.append(generateRandomChar(DIGITS));
        if(isPunctuationSymbolsNecessary) sb.append(generateRandomChar(PUNCTUATION_SYMBOLS));
        return sb.toString();
    }
}