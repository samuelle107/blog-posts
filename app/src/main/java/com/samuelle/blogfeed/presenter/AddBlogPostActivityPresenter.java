package com.samuelle.blogfeed.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.service.APIService;
import com.samuelle.blogfeed.service.APIUtils;
import com.samuelle.blogfeed.view.AddBlogPostActivity;

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

    public void addBlogPost(int userId, String title, String body) {
        Callback callback = new Callback<BlogPost>() {
            @Override
            public void onResponse(Call<BlogPost> call, Response<BlogPost> response) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("userId", response.body().getUserId());
                returnIntent.putExtra("id", response.body().getId());
                returnIntent.putExtra("title", response.body().getTitle());
                returnIntent.putExtra("body", response.body().getBody());

                context.setResult(Activity.RESULT_OK, returnIntent);
                context.finish();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("Samuel", "uh oh");
            }
        };

        if (!title.isEmpty() && !body.isEmpty()) {
            apiService.addPost(userId, title, body).enqueue(callback);
        } else {
            Toast.makeText(context, "Enter a title and body", Toast.LENGTH_LONG).show();
        }
    }
}
