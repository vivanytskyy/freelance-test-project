package com.gmail.ivanytskyy.vitaliy.rest.controllers;

import com.gmail.ivanytskyy.vitaliy.rest.entities.Job;
import com.gmail.ivanytskyy.vitaliy.rest.exceptions.UnexpectedHttpStatusCodeException;
import com.google.gson.Gson;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import java.io.IOException;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 24/07/2023
 */
public class JobController extends BaseController{
    private static final String BASE_URL = "https://freelance.lsrv.in.ua/api/job";

    public int createJob(Job job, String token) throws IOException {
        String addToPathString = "/create";
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(gson.toJson(job), mediaType);
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .addHeader("Authorization", token)
                .post(requestBody)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if(response.code() != 200){
                throw new UnexpectedHttpStatusCodeException(response.code());
            }
            return response.code();
        }
    }
    public Job findJobById(Long jobId, String token) throws IOException {
        String addToPathString = String.format("/%s", jobId);
        Gson gson = new Gson();
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .addHeader("Authorization", token)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if(response.code() != 200){
                throw new UnexpectedHttpStatusCodeException(response.code());
            }
            assert response.body() != null;
            return gson.fromJson(response.body().string(), Job.class);
        }
    }
    public Job[] findAllJobsForUser(String token) throws IOException {
        String addToPathString = "/user/jobs";
        Gson gson = new Gson();
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .addHeader("Authorization", token)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if(response.code() != 200){
                throw new UnexpectedHttpStatusCodeException(response.code());
            }
            assert response.body() != null;
            return gson.fromJson(response.body().string(), Job[].class);
        }
    }
    public Job[] findAllJobs(String token) throws IOException {
        String addToPathString = "/all";
        Gson gson = new Gson();
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .addHeader("Authorization", token)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if(response.code() != 200){
                throw new UnexpectedHttpStatusCodeException(response.code());
            }
            assert response.body() != null;
            return gson.fromJson(response.body().string(), Job[].class);
        }
    }
    public String deleteJobById(Long jobId, String token) throws IOException {
        String addToPathString = String.format("/delete/%s", jobId);
        RequestBody requestBody = RequestBody.create("", mediaType);
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .addHeader("Authorization", token)
                .post(requestBody)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if(response.code() != 200){
                throw new UnexpectedHttpStatusCodeException(response.code());
            }
            assert response.body() != null;
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString("message");
        }
    }
    public int createJobNegativeCase(String invalidJsonBody, String token) throws IOException {
        String addToPathString = "/create";
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
    public int findJobByIdNegativeCase(String jobId, String token) throws IOException {
        String addToPathString = String.format("/%s", jobId);
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .addHeader("Authorization", token)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            return response.code();
        }
    }
}