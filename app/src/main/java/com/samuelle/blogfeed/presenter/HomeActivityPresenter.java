package com.samuelle.blogfeed.presenter;

import android.content.Intent;

import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.service.APIService;
import com.samuelle.blogfeed.service.APIUtils;
import com.samuelle.blogfeed.view.BlogPostActivity;
import com.samuelle.blogfeed.view.HomeActivity;
import com.samuelle.blogfeed.view.ItemAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivityPresenter {
    private HomeActivity context;
    private APIService apiService;

    public HomeActivityPresenter(HomeActivity context) {
        this.context = context;
        this.apiService = APIUtils.getAPIService();
    }

    public void fetchBlogPosts() {
        Callback callback = new Callback<List<BlogPost>>() {
            @Override
            public void onResponse(Call<List<BlogPost>> call, Response<List<BlogPost>> response) {
                if (response.isSuccessful()) {
                    context.setBlogPosts(response.body());
                    initializeBlogPostsView(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<BlogPost>> call, Throwable t) {

            }
        };

        apiService.getBlogPosts().enqueue(callback);
    }

    private void initializeBlogPostsView(List<BlogPost> blogPosts) {
        ItemAdapter itemAdapter = new ItemAdapter(context, blogPosts, position -> {
            Intent intent = new Intent(context, BlogPostActivity.class);
            intent.putExtra("id", context.getBlogPosts().get(position).getId());
            intent.putExtra("title", context.getBlogPosts().get(position).getTitle());
            intent.putExtra("body", context.getBlogPosts().get(position).getBody());

            context.startActivity(intent);
        });

        context.initializeRecyclerView(itemAdapter);
    }

    public void updateBlogPosts(BlogPost blogPost) {
        context.getBlogPosts().add(0, blogPost);
        context.getItemAdapter().notifyItemInserted(0);
        context.scrollToTop();
    }
}
