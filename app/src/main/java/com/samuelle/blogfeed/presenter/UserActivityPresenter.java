package com.samuelle.blogfeed.presenter;

import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.model.User;
import com.samuelle.blogfeed.service.APIService;
import com.samuelle.blogfeed.service.APIUtils;
import com.samuelle.blogfeed.view.UserActivity;

import java.util.List;

import io.reactivex.Observable;

public class UserActivityPresenter {
    private UserActivity context;
    private APIService apiService;

    public UserActivityPresenter(UserActivity context) {
        this.context = context;
        this.apiService = APIUtils.getAPIService();
    }

    // Will return an observable of blog posts made by the desired user by making an API call
    public Observable<List<BlogPost>> getBlogPostsByUserObservable(User user) {
        return apiService
                .getBlogPostsByUserId(user.getId());
    }
}
