package com.gmail.ivanytskyy.vitaliy.rest.controllers;

import com.gmail.ivanytskyy.vitaliy.rest.entities.authorization.TokenResponseWrapper;
import com.gmail.ivanytskyy.vitaliy.rest.entities.authorization.UserCredentialsWrapper;
import com.gmail.ivanytskyy.vitaliy.rest.exceptions.UnexpectedHttpStatusCodeException;
import com.google.gson.Gson;
import okhttp3.*;
import java.io.IOException;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 18/07/2023
 */
public class AuthController extends BaseController{
    private static final String BASE_URL = "https://freelance.lsrv.in.ua/api/auth";

    public void signUp(UserCredentialsWrapper permit) throws IOException {
        String addToPathString = "/signup";
        System.out.println(permit);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(gson.toJson(permit), mediaType);
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .post(requestBody)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() != 200){
                //Write to log file
                System.out.println(response);
                throw new UnexpectedHttpStatusCodeException(response.code());
            }
        }
    }
    public TokenResponseWrapper signIn(UserCredentialsWrapper permit) throws IOException {
        String addToPathString = "/signin";
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(gson.toJson(permit), mediaType);
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .post(requestBody)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() != 200){
                //Write to log file
                System.out.println(response);
                throw new UnexpectedHttpStatusCodeException(response.code());
            }
            assert response.body() != null;
            return gson.fromJson(response.body().string(), TokenResponseWrapper.class);
        }
    }
    public int checkSignInResponseCode(UserCredentialsWrapper permit) throws IOException {
        String addToPathString = "/signin";
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(gson.toJson(permit), mediaType);
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .post(requestBody)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            return response.code();
        }
    }
}