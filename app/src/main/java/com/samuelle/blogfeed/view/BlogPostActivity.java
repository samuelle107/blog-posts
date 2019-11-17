package com.samuelle.blogfeed.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.samuelle.blogfeed.R;

public class BlogPostActivity extends AppCompatActivity {
    private TextView blogTitle;
    private TextView blogBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_post);
        blogTitle = findViewById(R.id.blogTitle);
        blogBody = findViewById(R.id.blogBody);

        initializeBlogView(
                getIntent().getIntExtra("id", -1),
                getIntent().getStringExtra("title"),
                getIntent().getStringExtra("body"));
    }

    private void initializeBlogView(int id, String title, String body) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String word : title.split(" ")) {
            stringBuilder.append(" ");
            stringBuilder.append(word.substring(0, 1).toUpperCase());
            stringBuilder.append(word.substring(1));
        }
        this.blogTitle.setText(stringBuilder.toString().substring(1));

        blogBody.setText(body.substring(0, 1).toUpperCase() + body.substring(1));
    }
}
