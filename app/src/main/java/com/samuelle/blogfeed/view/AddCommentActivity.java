package com.samuelle.blogfeed.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.samuelle.blogfeed.R;
import com.samuelle.blogfeed.presenter.AddCommentActivityPresenter;

public class AddCommentActivity extends AppCompatActivity {
    private Button confirmPostButton;
    private EditText blogBody;
    private EditText name;
    private EditText email;
    private AddCommentActivityPresenter presenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog_post);
        presenter = new AddCommentActivityPresenter(this);

        confirmPostButton = findViewById(R.id.confirmPost);
        blogBody = findViewById(R.id.editBody);
        email = findViewById(R.id.editEmail);
        name = findViewById(R.id.editName);

        confirmPostButton.setOnClickListener(v -> {
            presenter.addComment(
                    name.getText().toString(),
                    email.getText().toString(),
                    blogBody.getText().toString());
        });
    }
}
