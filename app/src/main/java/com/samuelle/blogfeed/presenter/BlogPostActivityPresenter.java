package com.samuelle.blogfeed.presenter;


import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.model.Comment;
import com.samuelle.blogfeed.service.APIService;
import com.samuelle.blogfeed.service.APIUtils;
import com.samuelle.blogfeed.view.BlogPostActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogPostActivityPresenter {
    private BlogPostActivity context;
    private APIService apiService;

    public BlogPostActivityPresenter(BlogPostActivity context) {
        this.context = context;
        this.apiService = APIUtils.getAPIService();
    }

    public void fetchComments(BlogPost blogPost) {
        Callback<List<Comment>> callback = new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                context.initializeComments(response.body());
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        };

        apiService.getComments(blogPost.getId()).enqueue(callback);
    }
}
