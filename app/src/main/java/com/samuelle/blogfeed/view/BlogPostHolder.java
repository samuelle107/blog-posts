package com.samuelle.blogfeed.view;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.samuelle.blogfeed.R;
import com.samuelle.blogfeed.model.BlogPost;


public class BlogPostHolder extends RecyclerView.ViewHolder {
    private TextView itemTitle;

    public BlogPostHolder(View itemView, BlogPostAdapter.OnItemListener onItemListener) {
        super(itemView);

        this.itemTitle = itemView.findViewById(R.id.itemTitle);

        itemView.setOnClickListener(v -> onItemListener.onItemClick(getAdapterPosition()));
    }

    public void setItemDetails(BlogPost blogPost) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String word : blogPost.getTitle().split(" ")) {
            stringBuilder.append(" ");
            stringBuilder.append(word.substring(0, 1).toUpperCase());
            stringBuilder.append(word.substring(1));
        }

        this.itemTitle.setText(stringBuilder.toString().substring(1));
    }
}