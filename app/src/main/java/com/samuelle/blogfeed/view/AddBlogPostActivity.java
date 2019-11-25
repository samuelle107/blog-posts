package com.samuelle.blogfeed.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.samuelle.blogfeed.R;
import com.samuelle.blogfeed.presenter.AddBlogPostActivityPresenter;

public class AddBlogPostActivity extends AppCompatActivity {
    private Button confirmPostButton;
    private EditText blogTitle;
    private EditText blogBody;
    private EditText name;
    private EditText email;
    private AddBlogPostActivityPresenter presenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog_post);
        presenter = new AddBlogPostActivityPresenter(this);

        confirmPostButton = findViewById(R.id.confirmPost);
        blogTitle = findViewById(R.id.editTitle);
        blogBody = findViewById(R.id.editBody);
        email = findViewById(R.id.editEmail);
        name = findViewById(R.id.editName);

        confirmPostButton.setOnClickListener(v -> {
            presenter.addBlogPost(
                    1,
                    name.getText().toString(),
                    email.getText().toString(),
                    blogTitle.getText().toString(),
                    blogBody.getText().toString());
        });
    }
}
