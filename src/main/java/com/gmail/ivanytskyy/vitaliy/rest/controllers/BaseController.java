package com.gmail.ivanytskyy.vitaliy.rest.controllers;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 18/07/2023
 */
public abstract class BaseController {
    protected final OkHttpClient httpClient;
    protected final MediaType mediaType;
    public BaseController(){
        this.httpClient = new OkHttpClient();
        this.mediaType = MediaType.get("application/json; charset=utf-8");
    }
}