package com.samuelle.blogfeed.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.samuelle.blogfeed.R;
import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.model.User;
import com.samuelle.blogfeed.presenter.HomeActivityPresenter;

import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private List<BlogPost> blogPosts;
    private BlogPostAdapter blogPostAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton addPostButton;
    private HomeActivityPresenter presenter;
    private FrameLayout progressOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        presenter = new HomeActivityPresenter(this);
        progressOverlay = findViewById(R.id.progressOverlay);

        addPostButton = findViewById(R.id.addPostButton);
        addPostButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddBlogPostActivity.class);
            startActivityForResult(intent, 1);
        });

        presenter.fetchBlogPosts();
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
        this.blogPosts = blogPosts;
    }

    public void initializeRecyclerView() {
        blogPostAdapter = new BlogPostAdapter(this, blogPosts, position -> {
            setProgressOverlayVisibility(View.VISIBLE);

            BlogPost blogPost = blogPosts.get(position);

            presenter.initializeBlogPostView(blogPost);
        });

        recyclerView = findViewById(R.id.blogPostsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(blogPostAdapter);
    }

    public void setProgressOverlayVisibility(int visibility) {
        this.progressOverlay.setVisibility(visibility);
    }

    public void addToBlogPosts(BlogPost blogPost) {
        this.blogPosts.add(0, blogPost);
        blogPostAdapter.notifyItemInserted(0);
        recyclerView.scrollToPosition(0);
    }
}
