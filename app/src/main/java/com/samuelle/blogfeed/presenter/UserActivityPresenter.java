package com.samuelle.blogfeed.presenter;

import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.model.Comment;
import com.samuelle.blogfeed.model.User;
import com.samuelle.blogfeed.service.APIService;
import com.samuelle.blogfeed.service.APIUtils;
import com.samuelle.blogfeed.view.BlogPostActivity;
import com.samuelle.blogfeed.view.UserActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivityPresenter {
    private UserActivity context;
    private APIService apiService;

    public UserActivityPresenter(UserActivity context) {
        this.context = context;
        this.apiService = APIUtils.getAPIService();
    }

    public void fetchBlogPosts(User user) {
        Callback<List<BlogPost>> callback = new Callback<List<BlogPost>>() {
            @Override
            public void onResponse(Call<List<BlogPost>> call, Response<List<BlogPost>> response) {
                context.initializeBlogPosts(response.body());
            }

            @Override
            public void onFailure(Call<List<BlogPost>> call, Throwable t) {

            }
        };

        apiService.getBlogPostsByUserId(user.getId()).enqueue(callback);
    }
}
