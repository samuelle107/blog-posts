package com.samuelle.blogfeed.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.samuelle.blogfeed.R;
import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.model.Comment;
import com.samuelle.blogfeed.model.User;
import com.samuelle.blogfeed.presenter.BlogPostActivityPresenter;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class BlogPostActivity extends AppCompatActivity {
    private TextView blogTitle;
    private TextView blogBody;
    private TextView blogAuthor;
    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private FloatingActionButton floatingActionButton;
    private BlogPostActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_post);
        this.presenter = new BlogPostActivityPresenter(this);
        blogTitle = findViewById(R.id.blogTitle);
        blogBody = findViewById(R.id.blogBody);
        blogAuthor = findViewById(R.id.blogAuthor);
        floatingActionButton = findViewById(R.id.addPostButton);

        BlogPost blogPost = getIntent().getExtras().getParcelable("blogPost");

        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddCommentActivity.class);
            intent.putExtra("postId", blogPost.getId());
            startActivityForResult(intent, 1);
        });

        initializePostDetails(blogPost);
        initializeUserDetails(blogPost.getUser());

        presenter
                .getCommentsObservable(blogPost)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Comment>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Comment> comments) {
                        initializeComments(comments);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                commentAdapter.addComment(data.getExtras().getParcelable("comment"));
            }
        }
    }
}
