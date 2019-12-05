package com.samuelle.blogfeed.presenter;

import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.service.APIService;
import com.samuelle.blogfeed.service.APIUtils;
import com.samuelle.blogfeed.view.HomeActivity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeActivityPresenter {
    private HomeActivity context;
    private APIService apiService;

    public HomeActivityPresenter(HomeActivity context) {
        this.context = context;
        this.apiService = APIUtils.getAPIService();
    }

    // Will make an async api call to blog posts, then send the blog posts to the main thread
    public Observable<BlogPost> getBlogPostObservable() {
        return apiService
                .getBlogPosts()
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(blogPosts -> {
                    context.setBlogPosts(blogPosts);

                    return Observable.fromIterable(blogPosts);
                })
                .observeOn(Schedulers.io());
    }

    // Will make an async call to get user data corresponding to the blog post
    public Observable<BlogPost> getUserObservable(final BlogPost blogPost) {
        return apiService
                .getUser(blogPost.getUserId())
                .flatMap(user -> {
                    blogPost.setUser(user);

                    return Observable.just(blogPost);
                });
    }
}
