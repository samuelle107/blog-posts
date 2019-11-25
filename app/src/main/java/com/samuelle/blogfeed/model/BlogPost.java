package com.samuelle.blogfeed.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BlogPost implements Parcelable {
    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("body")
    @Expose
    private String body;
    private User user;
    private List<Comment> comments;
    private int dumb;

    public BlogPost(int userId, int id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setDumb(int i) {
        this.dumb = i;
    }

    public int getDumb() {
        return this.dumb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.body);
        dest.writeParcelable(this.user, flags);
        dest.writeList(this.comments);
    }

    protected BlogPost(Parcel in) {
        this.userId = in.readInt();
        this.id = in.readInt();
        this.title = in.readString();
        this.body = in.readString();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.comments = new ArrayList<Comment>();
        in.readList(this.comments, Comment.class.getClassLoader());
    }

    public static final Creator<BlogPost> CREATOR = new Creator<BlogPost>() {
        @Override
        public BlogPost createFromParcel(Parcel source) {
            return new BlogPost(source);
        }

        @Override
        public BlogPost[] newArray(int size) {
            return new BlogPost[size];
        }
    };
}
