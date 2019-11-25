package com.samuelle.blogfeed.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samuelle.blogfeed.R;
import com.samuelle.blogfeed.model.BlogPost;

import java.util.List;

public class BlogPostAdapter extends RecyclerView.Adapter<BlogPostHolder> {
    private Context context;
    private List<BlogPost> blogPosts;
    private OnItemListener onItemListener;

    public BlogPostAdapter(
            Context context,
            List<BlogPost> blogPosts,
            OnItemListener onItemListener) {
        this.context = context;
        this.blogPosts = blogPosts;
        this.onItemListener = onItemListener;
    }

    @Override
    public void onBindViewHolder(@NonNull BlogPostHolder blogPostHolder, int position) {
        BlogPost blogPost = blogPosts.get(position);

        blogPostHolder.setItemDetails(blogPost);
    }

    @Override
    public int getItemCount() {
        return blogPosts.size();
    }

    @NonNull
    @Override
    public BlogPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.blog_post_item, parent, false);

        return new BlogPostHolder(view, onItemListener);
    }

    public void updateBlogPosts(List<BlogPost> blogPosts) {
        this.blogPosts.clear();
        this.blogPosts.addAll(blogPosts);
        this.notifyDataSetChanged();
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }
}