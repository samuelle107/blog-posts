package com.samuelle.blogfeed.presenter;


import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.service.APIService;
import com.samuelle.blogfeed.service.APIUtils;
import com.samuelle.blogfeed.view.HomeActivity;


import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class HomeActivityPresenter {
    private HomeActivity context;
    private APIService apiService;

    public HomeActivityPresenter(HomeActivity context) {
        this.context = context;
        this.apiService = APIUtils.getAPIService();
    }

    private Observable<BlogPost> getBlogPostsObservable() {
        return apiService
                .getBlogPosts()
                .flatMap(Observable::fromIterable);
    }

    private Observable<BlogPost> getUserObservable(final BlogPost blogPost) {
        return apiService
                .getUser(blogPost.getUserId())
                .map(user -> {
                    blogPost.setUser(user);

                    return blogPost;
                });
    }

    private Observable<BlogPost> getCommentsObservable(final BlogPost blogPost) {
        return apiService
                .getComments(blogPost.getId())
                .map(comments -> {
                    blogPost.setComments(comments);

                    return blogPost;
                });
    }

    public Observable<List<BlogPost>> initializeBlogPosts() {
        return getBlogPostsObservable()
//                .flatMap(this::getCommentsObservable)
                .flatMap(this::getUserObservable)
                .toList()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread());
    }
}
