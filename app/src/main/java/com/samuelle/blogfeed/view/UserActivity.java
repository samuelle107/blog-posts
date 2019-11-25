package com.samuelle.blogfeed.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samuelle.blogfeed.R;
import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.model.User;
import com.samuelle.blogfeed.presenter.UserActivityPresenter;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UserActivity extends AppCompatActivity {
    private TextView name;
    private TextView username;
    private TextView email;
    private TextView street;
    private TextView suite;
    private TextView city;
    private TextView zipcode;
    private TextView phoneNumber;
    private TextView website;
    private TextView companyName;
    private TextView companyCatchPhrase;
    private TextView compnayBs;
    private TextView viewMap;
    private RecyclerView recyclerView;
    private UserActivityPresenter presenter;
    private BlogPostAdapter blogPostAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        this.presenter = new UserActivityPresenter(this);

        User user = getIntent().getExtras().getParcelable("user");

        initializeUserProfile(user);

        presenter
                .getBlogPostsByUserObservable(user)
                .subscribe(new Observer<List<BlogPost>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<BlogPost> blogPosts) {
                        initializeBlogPosts(blogPosts);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void initializeUserProfile(User user) {
        this.name = findViewById(R.id.name);
        this.username = findViewById(R.id.username);
        this.email = findViewById(R.id.email);
        this.street = findViewById(R.id.street);
        this.suite = findViewById(R.id.suite);
        this.city = findViewById(R.id.city);
        this.zipcode = findViewById(R.id.zipcode);
        this.phoneNumber = findViewById(R.id.phoneNumber);
        this.website = findViewById(R.id.website);
        this.companyName = findViewById(R.id.companyName);
        this.companyCatchPhrase = findViewById(R.id.companyCatchPhrase);
        this.compnayBs = findViewById(R.id.companyBs);
        this.viewMap = findViewById(R.id.viewMapText);

        this.name.setText(user.getName());
        this.username.setText(user.getUsername());
        this.email.setText(user.getEmail());
        this.street.setText(user.getAddress().getStreet());
        this.suite.setText(user.getAddress().getSuite());
        this.city.setText(user.getAddress().getCity());
        this.zipcode.setText(user.getAddress().getZipcode());
        this.phoneNumber.setText(user.getPhone());
        this.website.setText(user.getWebsite());
        this.companyName.setText(user.getCompany().getName());
        this.companyCatchPhrase.setText(user.getCompany().getCatchPhrase());
        this.compnayBs.setText(user.getCompany().getBs());

        this.viewMap.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserMap.class);
            intent.putExtra("geo", user.getAddress().getGeo());

            startActivity(intent);
        });
    }

    public void initializeBlogPosts(List<BlogPost> blogPosts) {
        blogPostAdapter = new BlogPostAdapter(this, blogPosts, position -> {
        });

        this.recyclerView = findViewById(R.id.userPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(blogPostAdapter);
    }
}
