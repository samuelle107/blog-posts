package com.samuelle.blogfeed.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samuelle.blogfeed.R;
import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.model.Comment;
import com.samuelle.blogfeed.model.User;
import com.samuelle.blogfeed.presenter.BlogPostActivityPresenter;

import java.util.HashMap;
import java.util.List;

public class BlogPostActivity extends AppCompatActivity {
    private TextView blogTitle;
    private TextView blogBody;
    private TextView blogAuthor;
    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private BlogPostActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_post);
        this.presenter = new BlogPostActivityPresenter(this);
        blogTitle = findViewById(R.id.blogTitle);
        blogBody = findViewById(R.id.blogBody);
        blogAuthor = findViewById(R.id.blogAuthor);

        BlogPost blogPost = getIntent().getExtras().getParcelable("blogPost");

        initializePostDetails(blogPost);
        initializeUserDetails(blogPost.getUser());
        initializeComments(blogPost.getComments());
    }

    public void initializePostDetails(BlogPost blogPost) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String word : blogPost.getTitle().split(" ")) {
            stringBuilder.append(" ");
            stringBuilder.append(word.substring(0, 1).toUpperCase());
            stringBuilder.append(word.substring(1));
        }

        String title = stringBuilder.toString().substring(1);
        String body = blogPost.getBody().substring(0, 1).toUpperCase() + blogPost.getBody().substring(1);

        this.blogTitle.setText(title);
        this.blogBody.setText(body);
    }
//
    public void initializeUserDetails(User user) {
        String userText = "By " + user.getUsername();

        this.blogAuthor.setText(userText);
        this.blogAuthor.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserActivity.class);
            intent.putExtra("user", user);

            startActivity(intent);
        });
    }
//
    public void initializeComments(List<Comment> comments) {
        this.commentAdapter = new CommentAdapter(this, comments, position -> {});

        this.recyclerView = findViewById(R.id.comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(commentAdapter);
    }
}
