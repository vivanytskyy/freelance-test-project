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

    public void updateUser(User user, String token) throws IOException {
        String addToPathString = "/update";
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(gson.toJson(user), mediaType);
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .addHeader("Authorization", token)
                .post(requestBody)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() != 200){
                throw new UnexpectedHttpStatusCodeException(response.code());
            }
        }
    }
    public User findUserById(Long userId, String token) throws IOException {
        String addToPathString = String.format("/%s", userId);
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .addHeader("Authorization", token)
                .get()
                .build();
        Gson gson = new Gson();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() != 200){
                throw new UnexpectedHttpStatusCodeException(response.code());
            }
            assert response.body() != null;
            return gson.fromJson(response.body().string(), User.class);
        }
    }
    public User getUser(String token) throws IOException {
        String addToPathString = "/";
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .addHeader("Authorization", token)
                .get()
                .build();
        Gson gson = new Gson();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() != 200){
                throw new UnexpectedHttpStatusCodeException(response.code());
            }
            assert response.body() != null;
            return gson.fromJson(response.body().string(), User.class);
        }
    }
    public int findUserByIdNegativeCase(String userId, String token) throws IOException {
        String addToPathString = String.format("/%s", userId);
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .addHeader("Authorization", token)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            return response.code();
        }
    }
    public int updateUserNegativeCase(String invalidJsonBody, String token) throws IOException {
        String addToPathString = "/update";
        RequestBody requestBody = RequestBody.create(invalidJsonBody, mediaType);
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .addHeader("Authorization", token)
                .post(requestBody)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            return response.code();
        }
    }
}