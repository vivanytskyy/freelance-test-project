package com.gmail.ivanytskyy.vitaliy.utils;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 22/07/2023
 */
public final class TokenHolder {
    private static TokenHolder instance = null;
    private String token;
    private TokenHolder(){}
    public static TokenHolder getInstance(){
        if(instance == null){
            instance = new TokenHolder();
        }
        return instance;
    }
    public void setToken(String token){
        getInstance().token = token;
    }
    public String getToken(){
        return getInstance().token;
    }
}