package com.gmail.ivanytskyy.vitaliy.rest.controllers;

import com.gmail.ivanytskyy.vitaliy.rest.entities.User;
import com.gmail.ivanytskyy.vitaliy.rest.exceptions.UnexpectedHttpStatusCodeException;
import com.google.gson.Gson;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 18/07/2023
 */
public class UserController extends BaseController{
    private static final String BASE_URL = "https://freelance.lsrv.in.ua/api/user";

    public void updateUser(User user) throws IOException {
        String addToPathString = "/update";
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(gson.toJson(user), mediaType);
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .put(requestBody)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() != 200){
                //Write to log file
                System.out.println(response);
                throw new UnexpectedHttpStatusCodeException(response.code());
            }
        }
    }
    public User findUserById(Long userId) throws IOException {
        String addToPathString = String.format("/%s", userId);
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .get()
                .build();
        Gson gson = new Gson();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() != 200){
                //Write to log file
                System.out.println(response);
                throw new UnexpectedHttpStatusCodeException(response.code());
            }
            assert response.body() != null;
            return gson.fromJson(response.body().string(), User.class);
        }
    }
    public User getUser() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL)
                .get()
                .build();
        Gson gson = new Gson();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() != 200){
                //Write to log file
                System.out.println(response);
                throw new UnexpectedHttpStatusCodeException(response.code());
            }
            assert response.body() != null;
            return gson.fromJson(response.body().string(), User.class);
        }
    }
}