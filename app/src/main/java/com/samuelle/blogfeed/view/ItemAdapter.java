package com.samuelle.blogfeed.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samuelle.blogfeed.R;
import com.samuelle.blogfeed.model.BlogPost;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
    private Context context;
    private List<BlogPost> blogPosts;
    private OnItemListener onItemListener;

    public ItemAdapter(
            Context context,
            List<BlogPost> blogPosts,
            OnItemListener onItemListener) {
        this.context = context;
        this.blogPosts = blogPosts;
        this.onItemListener = onItemListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int position) {
        BlogPost blogPost = blogPosts.get(position);
        itemHolder.setItemDetails(blogPost);
    }

    @Override
    public int getItemCount() {
        return blogPosts.size();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.item_label, parent, false);

        return new ItemHolder(view, onItemListener);
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }
}