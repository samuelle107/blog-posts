package com.samuelle.blogfeed.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.samuelle.blogfeed.R;
import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.model.User;
import com.samuelle.blogfeed.presenter.HomeActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity {
    private List<BlogPost> blogPosts;

    private BlogPostAdapter blogPostAdapter;
    private RecyclerView recyclerView;
    private HomeActivityPresenter presenter;
    private CompositeDisposable disposables;
    private FrameLayout progressOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        presenter = new HomeActivityPresenter(this);
        progressOverlay = findViewById(R.id.progressOverlay);
        disposables = new CompositeDisposable();
        blogPosts = new ArrayList<>();
        blogPostAdapter = new BlogPostAdapter(this, blogPosts, position -> {
            BlogPost blogPost = blogPosts.get(position);

            Intent intent = new Intent(this, BlogPostActivity.class);
            intent.putExtra("blogPost", blogPost);

            startActivity(intent);
        });

        presenter
                .getBlogPostObservable()
                .flatMap(presenter::getUserObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BlogPost>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BlogPost blogPost) {
                        updateBlogPost(blogPost);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        setProgressOverlayVisibility(View.INVISIBLE);
                    }
                });

        recyclerView = findViewById(R.id.blogPostsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(blogPostAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        disposables.clear();
    }

    public void setBlogPosts(List<BlogPost> blogPosts) {
        this.blogPostAdapter.updateBlogPosts(blogPosts);
    }

    public void updateBlogPost(BlogPost blogPost) {
        blogPostAdapter.updateBlogPost(blogPost);
    }

    public void setProgressOverlayVisibility(int visibility) {
        this.progressOverlay.setVisibility(visibility);
    }
}
