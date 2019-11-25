package com.samuelle.blogfeed.service;


import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.model.Comment;
import com.samuelle.blogfeed.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @GET("/posts")
    Observable<List<BlogPost>> getBlogPosts();

    @GET("/posts/{postId}")
    Observable<BlogPost> getBlogPost(@Path("postId") int postId);

    @GET("/posts")
    Observable<List<BlogPost>> getBlogPostsByUserId(@Query("userId") int userId);

    @GET("/comments")
    Observable<List<Comment>> getComments(@Query("postId") int postId);

    @GET("/users/{id}")
    Observable<User> getUser(@Path("id") int id);

    @POST("/posts")
    @FormUrlEncoded
    Observable<BlogPost> addPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String body);
}
