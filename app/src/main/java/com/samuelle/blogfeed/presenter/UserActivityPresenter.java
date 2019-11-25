package com.samuelle.blogfeed.presenter;

import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.model.User;
import com.samuelle.blogfeed.service.APIService;
import com.samuelle.blogfeed.service.APIUtils;
import com.samuelle.blogfeed.view.UserActivity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserActivityPresenter {
    private UserActivity context;
    private APIService apiService;

    public UserActivityPresenter(UserActivity context) {
        this.context = context;
        this.apiService = APIUtils.getAPIService();
    }

    public Observable<List<BlogPost>> getBlogPostsByUserObservable(User user) {
        return apiService
                .getBlogPostsByUserId(user.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
