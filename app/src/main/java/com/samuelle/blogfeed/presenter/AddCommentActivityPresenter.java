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
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddCommentActivityPresenter {
    private AddCommentActivity context;
    private APIService apiService;

    public AddCommentActivityPresenter(AddCommentActivity context) {
        this.context = context;
        this.apiService = APIUtils.getAPIService();
    }

    // Will make a post request to the end point with the postId, name, email, and body.  Returns an observable.  Done async
    public Observable<Comment> addCommentObservable(int postId, String name, String email, String body) {
        return apiService
                .addComment(postId, name, email, body);
    }
}
