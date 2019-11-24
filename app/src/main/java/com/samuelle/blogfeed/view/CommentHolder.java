package com.samuelle.blogfeed.view;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.samuelle.blogfeed.R;
import com.samuelle.blogfeed.model.Comment;

public class CommentHolder extends RecyclerView.ViewHolder {
    private TextView commentName;
    private TextView commentEmail;
    private TextView commentBody;

    public CommentHolder(View itemView, CommentAdapter.OnItemListener onItemListener) {
        super(itemView);

        this.commentName = itemView.findViewById(R.id.commentName);
        this.commentEmail = itemView.findViewById(R.id.commentEmail);
        this.commentBody = itemView.findViewById(R.id.commentBody);

        itemView.setOnClickListener(v -> onItemListener.onItemClick(getAdapterPosition()));
    }

    public void setItemDetails(Comment comment) {
        this.commentName.setText(comment.getName());
        this.commentEmail.setText(comment.getEmail());
        this.commentBody.setText(comment.getBody());
    }
}