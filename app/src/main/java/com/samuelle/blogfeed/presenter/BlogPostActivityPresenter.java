package com.samuelle.blogfeed.presenter;


import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.model.Comment;
import com.samuelle.blogfeed.service.APIService;
import com.samuelle.blogfeed.service.APIUtils;
import com.samuelle.blogfeed.view.BlogPostActivity;

import java.util.List;

import io.reactivex.Observable;

public class BlogPostActivityPresenter {
    private BlogPostActivity context;
    private APIService apiService;

    public BlogPostActivityPresenter(BlogPostActivity context) {
        this.context = context;
        this.apiService = APIUtils.getAPIService();
    }

    // Will make an async call to get comments
    public Observable<List<Comment>> getCommentsObservable(BlogPost blogPost) {
        return apiService
                .getComments(blogPost.getId());
    }
}
