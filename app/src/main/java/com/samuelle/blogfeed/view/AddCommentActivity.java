package com.samuelle.blogfeed.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.samuelle.blogfeed.R;
import com.samuelle.blogfeed.model.Comment;
import com.samuelle.blogfeed.presenter.AddCommentActivityPresenter;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

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

        // Called when the user clicks on the confirm button
        // Sends the info to make a post request as long as all fields are filled out
        // If the call is successful, then it will get the post result and finish the activity
        confirmPostButton.setOnClickListener(v -> {
            if (!name.toString().isEmpty() && !email.toString().isEmpty() && !blogBody.toString().isEmpty()) {
                int postId = getIntent().getIntExtra("postId", -1);

                presenter
                        .addCommentObservable(postId, name.getText().toString(), email.getText().toString(), blogBody.getText().toString())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Comment>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Comment comment) {
                                Intent intent = new Intent();
                                intent.putExtra("comment", comment);

                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            } else {
                Toast.makeText(this, "Fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
