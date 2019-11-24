package com.samuelle.blogfeed.service;


import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.model.Comment;
import com.samuelle.blogfeed.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @GET("/posts")
    Call<List<BlogPost>> getBlogPosts();

    @GET("/posts/{postId}")
    Call<BlogPost> getBlogPost(@Path("postId") int postId);

    @GET("/posts")
    Call<List<BlogPost>> getBlogPostsByUserId(@Query("userId") int userId);

    @GET("/comments")
    Call<List<Comment>> getComments(@Query("postId") int postId);

    @GET("/users/{id}")
    Call<User> getUser(@Path("id") int id);

    @POST("/posts")
    @FormUrlEncoded
    Call<BlogPost> addPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String body);
}
