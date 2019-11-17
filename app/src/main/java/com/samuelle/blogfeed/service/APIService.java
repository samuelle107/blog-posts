package com.samuelle.blogfeed.service;


import com.samuelle.blogfeed.model.BlogPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    @GET("/posts")
    Call<List<BlogPost>> getBlogPosts();

    @GET("/posts/{id}")
    Call<BlogPost> getBlogPost(@Path("id") int id);

    @POST("/posts")
    @FormUrlEncoded
    Call<BlogPost> addPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String body);
}
