package com.samuelle.blogfeed.presenter;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.model.User;
import com.samuelle.blogfeed.service.APIService;
import com.samuelle.blogfeed.service.APIUtils;
import com.samuelle.blogfeed.view.BlogPostActivity;
import com.samuelle.blogfeed.view.HomeActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivityPresenter {
    private HomeActivity context;
    private APIService apiService;

    public HomeActivityPresenter(HomeActivity context) {
        this.context = context;
        this.apiService = APIUtils.getAPIService();
    }

    public void fetchBlogPosts() {
        Callback<List<BlogPost>> callback = new Callback<List<BlogPost>>() {
            @Override
            public void onResponse(Call<List<BlogPost>> call, Response<List<BlogPost>> response) {
                List<BlogPost> blogPosts = response.body();
                context.setBlogPosts(blogPosts);
                context.initializeRecyclerView();
            }

            @Override
            public void onFailure(Call<List<BlogPost>> call, Throwable t) {

            }
        };

        apiService.getBlogPosts().enqueue(callback);
    }

    public void fetchUserInfo(BlogPost blogPost) {
        Callback<User> callback = new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                Intent intent = new Intent(context, BlogPostActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("blogPost", blogPost);

                context.setProgressOverlayVisibility(View.INVISIBLE);

                context.startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        };

        apiService.getUser(blogPost.getUserId()).enqueue(callback);
    }

    public void initializeBlogPostView(BlogPost blogPost) {
        fetchUserInfo(blogPost);
    }

    public void updateBlogPosts(BlogPost blogPost) {
        Log.d("Samuel", blogPost.getTitle());
    }
}
