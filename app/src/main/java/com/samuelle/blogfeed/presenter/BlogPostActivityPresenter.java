package com.samuelle.blogfeed.presenter;


import com.samuelle.blogfeed.service.APIService;
import com.samuelle.blogfeed.service.APIUtils;
import com.samuelle.blogfeed.view.BlogPostActivity;

public class BlogPostActivityPresenter {
    private BlogPostActivity context;
    private APIService apiService;

    public BlogPostActivityPresenter(BlogPostActivity context) {
        this.context = context;
        this.apiService = APIUtils.getAPIService();
    }
}
