package com.samuelle.blogfeed.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.samuelle.blogfeed.R;
import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.presenter.HomeActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

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
                .initializeBlogPosts()
                .subscribe(new Observer<List<BlogPost>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<BlogPost> blogPosts) {
                        setBlogPosts(blogPosts);
                        progressOverlay.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                addToBlogPosts(data.getExtras().getParcelable("blogPost"));
            }
        }
    }

    public void setBlogPosts(List<BlogPost> blogPosts) {
        this.blogPostAdapter.updateBlogPosts(blogPosts);
    }

    public void addToBlogPosts(BlogPost blogPost) {
        this.blogPosts.add(0, blogPost);
        blogPostAdapter.notifyItemInserted(0);
        recyclerView.scrollToPosition(0);
    }
}
