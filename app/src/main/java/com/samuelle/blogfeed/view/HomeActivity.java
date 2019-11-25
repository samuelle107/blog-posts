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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.samuelle.blogfeed.R;
import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.model.User;
import com.samuelle.blogfeed.presenter.HomeActivityPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity {
    private List<BlogPost> blogPosts;
    private List<User> users;
    private Map<BlogPost, User> blogPostUserMap;
    private BlogPostAdapter blogPostAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton addPostButton;
    private HomeActivityPresenter presenter;
    private CompositeDisposable disposables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        presenter = new HomeActivityPresenter(this);
        disposables = new CompositeDisposable();

        addPostButton = findViewById(R.id.addPostButton);
        addPostButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddBlogPostActivity.class);
            startActivityForResult(intent, 1);
        });

        blogPosts = new ArrayList<>();

        blogPostAdapter = new BlogPostAdapter(this, blogPosts, position -> {
            BlogPost blogPost = blogPosts.get(position);

            Intent intent = new Intent(this, BlogPostActivity.class);
            intent.putExtra("blogPost", blogPost);

            startActivity(intent);
        });

        presenter.initializeBlogPosts();

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
