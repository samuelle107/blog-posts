package com.samuelle.blogfeed.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.samuelle.blogfeed.R;
import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.presenter.HomeActivityPresenter;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private List<BlogPost> blogPosts;
    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton addPostButton;
    private HomeActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        presenter = new HomeActivityPresenter(this);

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
                presenter.updateBlogPosts(new BlogPost(
                        data.getIntExtra("userId", -1),
                        data.getIntExtra("id", -1),
                        data.getStringExtra("title"),
                        data.getStringExtra("body")
                ));
            }
        }
    }

    public List<BlogPost> getBlogPosts() {
        return this.blogPosts;
    }

    public void setBlogPosts(List<BlogPost> blogPosts) {
        this.blogPosts = blogPosts;
    }

    public ItemAdapter getItemAdapter() {
        return this.itemAdapter;
    }

    public void initializeRecyclerView(ItemAdapter itemAdapter) {
        this.itemAdapter = itemAdapter;

        recyclerView = findViewById(R.id.blogPostsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(itemAdapter);
    }

    public void scrollToTop() {
        recyclerView.scrollToPosition(0);
    }
}
