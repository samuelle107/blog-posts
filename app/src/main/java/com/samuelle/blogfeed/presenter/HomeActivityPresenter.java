package com.samuelle.blogfeed.presenter;

import android.util.Log;

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

    private Observable<BlogPost> getBlogPostsObservable() {
        return apiService
                .getBlogPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(blogPosts -> {
                    context.setBlogPosts(blogPosts);

                    return Observable.fromIterable(blogPosts);
                });
    }

    private Observable<BlogPost> getUserObservable(final BlogPost blogPost) {
        return apiService
                .getUser(blogPost.getUserId())
                .subscribeOn(Schedulers.io())
                .map(user -> {
                    blogPost.setUser(user);

                    return blogPost;
                });
    }

    private Observable<BlogPost> getCommentsObservable(final BlogPost blogPost) {
        return apiService
                .getComments(blogPost.getId())
                .subscribeOn(Schedulers.io())
                .map(comments -> {
                    blogPost.setComments(comments);

                    return blogPost;
                });
    }

    public void initializeBlogPosts() {
        getBlogPostsObservable()
                .subscribeOn(Schedulers.io())
                .flatMap(blogPost -> getCommentsObservable(blogPost))
                .flatMap(blogPost -> getUserObservable(blogPost))
                .subscribe();
    }

    public void updateBlogPosts(BlogPost blogPost) {
        Log.d("Samuel", blogPost.getTitle());
    }
}
