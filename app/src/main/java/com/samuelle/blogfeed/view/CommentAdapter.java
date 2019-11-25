package com.samuelle.blogfeed.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samuelle.blogfeed.R;
import com.samuelle.blogfeed.model.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {
    private Context context;
    private List<Comment> comments;
    private CommentAdapter.OnItemListener onItemListener;

    public CommentAdapter(
            Context context,
            List<Comment> comments,
            CommentAdapter.OnItemListener onItemListener) {
        this.context = context;
        this.comments = comments;
        this.onItemListener = onItemListener;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder commentHolder, int position) {
        Comment comment = comments.get(position);
        commentHolder.setItemDetails(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.comment_item, parent, false);

        return new CommentHolder(view, onItemListener);
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }

    public void addComment(Comment comment) {
        comments.add(0, comment);
        notifyItemChanged(0);
    }
}
