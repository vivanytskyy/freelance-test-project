package com.gmail.ivanytskyy.vitaliy.rest.controllers;

import com.gmail.ivanytskyy.vitaliy.rest.entities.Comment;
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
public class CommentController extends BaseController{
    private static final String BASE_URL = "https://freelance.lsrv.in.ua/api/comment";

    public int createComment(Comment comment, Long jobId, String token) throws IOException {
        String addToPathString = String.format("/%s/create", jobId);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(gson.toJson(comment), mediaType);
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .addHeader("Authorization", token)
                .post(requestBody)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if(response.code() != 200){
                throw  new UnexpectedHttpStatusCodeException(response.code());
            }
            return response.code();
        }
    }
    public Comment[] findAllCommentsByJobId(Long jobId, String token) throws IOException {
        String addToPathString = String.format("/%s/all", jobId);
        Request request = new Request.Builder()
                .url(BASE_URL + addToPathString)
                .addHeader("Authorization", token)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if(response.code() != 200){
                throw new UnexpectedHttpStatusCodeException(response.code());
            }
            Gson gson = new Gson();
            assert response.body() != null;
            return gson.fromJson(response.body().string(), Comment[].class);
        }
    }
}