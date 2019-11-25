package com.samuelle.blogfeed.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.samuelle.blogfeed.model.Address;
import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.model.Company;
import com.samuelle.blogfeed.model.Geo;
import com.samuelle.blogfeed.model.User;
import com.samuelle.blogfeed.service.APIService;
import com.samuelle.blogfeed.service.APIUtils;
import com.samuelle.blogfeed.view.AddBlogPostActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBlogPostActivityPresenter {
    private AddBlogPostActivity context;
    private APIService apiService;

    public AddBlogPostActivityPresenter(AddBlogPostActivity context) {
        this.context = context;
        this.apiService = APIUtils.getAPIService();
    }

    private Observable<BlogPost> addBlockPostObservable(int userId, String title, String body) {
        return apiService
                .addPost(userId, title, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void addBlogPost(int userId, String name, String email, String title, String body) {
        if (!title.isEmpty() && !body.isEmpty()) {
            addBlockPostObservable(userId, title, body)
                    .subscribe(blogPost -> {
                        blogPost.setUser(new User(
                                11,
                                name,
                                "samuelle",
                                email,
                                new Address(
                                        "2123",
                                        "apple",
                                        "Rogers",
                                        "72701",
                                        new Geo("-40",
                                                "2")),
                                "24234235",
                                "abc.com",
                                new Company(
                                        "Mobile Programming",
                                        "Give me good grade",
                                        "please")));

                        Intent intent = new Intent();
                        intent.putExtra("blogPost", blogPost);

                        context.setResult(Activity.RESULT_OK, intent);
                        context.finish();
                    });
        } else {
            Toast.makeText(context, "Enter a title and body", Toast.LENGTH_LONG).show();
        }
    }
}
