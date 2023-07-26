package com.gmail.ivanytskyy.vitaliy.rest.controllers;

import com.gmail.ivanytskyy.vitaliy.rest.entities.Profile;
import com.gmail.ivanytskyy.vitaliy.rest.exceptions.UnexpectedHttpStatusCodeException;
import com.google.gson.Gson;
import okhttp3.*;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 18/07/2023
 */
public class ImageController extends BaseController{
    private static final String BASE_URL = "https://freelance.lsrv.in.ua/api/image";

    public String uploadFile(File file, String token) throws IOException {
        String addToPathString = "/upload";
        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getAbsolutePath(),
                        RequestBody.create(file, MediaType.parse("image/png")))
                .build();
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .addHeader("Authorization", token)
                .post(formBody)
                .build();
        try(Response response = httpClient.newCall(request).execute()) {
            if (response.code() != 200){
                throw new UnexpectedHttpStatusCodeException(response.code());
            }
            assert response.body() != null;
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString("message");
        }
    }
    public Profile findProfileByUserId(Long userId, String token) throws IOException {
        String addToPathString = String.format("/%s", userId);
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .addHeader("Authorization", token)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() != 200){
                throw new UnexpectedHttpStatusCodeException(response.code());
            }
            Gson gson = new Gson();
            assert response.body() != null;
            return gson.fromJson(response.body().string(), Profile.class);
        }
    }
    public Profile findProfile(String token) throws IOException {
        String addToPathString = "/profile";
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .addHeader("Authorization", token)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() != 200){
                throw new UnexpectedHttpStatusCodeException(response.code());
            }
            Gson gson = new Gson();
            assert response.body() != null;
            return gson.fromJson(response.body().string(), Profile.class);
        }
    }
    public int findProfileByUserIdNegativeCase(String userId, String token) throws IOException {
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
    public int uploadFileNegativeCase(String path, String token) throws IOException {
        String addToPathString = "/upload";
        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", path,
                        RequestBody.create(path, MediaType.parse("image/png")))
                .build();
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .addHeader("Authorization", token)
                .post(formBody)
                .build();
        try(Response response = httpClient.newCall(request).execute()) {
            return response.code();
        }
    }
}