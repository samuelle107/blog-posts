package com.samuelle.blogfeed.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.samuelle.blogfeed.model.Comment;
import com.samuelle.blogfeed.service.APIService;
import com.samuelle.blogfeed.service.APIUtils;
import com.samuelle.blogfeed.view.AddCommentActivity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddCommentActivityPresenter {
    private AddCommentActivity context;
    private APIService apiService;

    public AddCommentActivityPresenter(AddCommentActivity context) {
        this.context = context;
        this.apiService = APIUtils.getAPIService();
    }

    private Observable<Comment> addCommentObservable(int postId, String name, String email, String body) {
        return apiService
                .addComment(postId, name, email, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void addComment(String name, String email, String body) {
        if (!name.isEmpty() && !email.isEmpty() && !body.isEmpty()) {
            int postId = context.getIntent().getIntExtra("postId", -1);

            addCommentObservable(postId, name, email, body)
            .subscribe(comment -> {
                Intent intent = new Intent();
                intent.putExtra("comment", comment);

                context.setResult(Activity.RESULT_OK, intent);
                context.finish();
            });

        } else {
            Toast.makeText(context, "Fill in all fields", Toast.LENGTH_LONG).show();
        }
    }
}
