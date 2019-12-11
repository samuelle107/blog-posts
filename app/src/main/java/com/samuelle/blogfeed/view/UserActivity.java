package com.samuelle.blogfeed.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.samuelle.blogfeed.R;
import com.samuelle.blogfeed.model.BlogPost;
import com.samuelle.blogfeed.model.Geo;
import com.samuelle.blogfeed.model.User;
import com.samuelle.blogfeed.presenter.UserActivityPresenter;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class UserActivity extends AppCompatActivity {
    private TextView name;
    private TextView username;
    private TextView email;
    private TextView phoneNumber;
    private TextView website;
    private RecyclerView recyclerView;
    private UserActivityPresenter presenter;
    private BlogPostAdapter blogPostAdapter;
    private GoogleMap map;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        this.presenter = new UserActivityPresenter(this);

        User user = getIntent().getExtras().getParcelable("user");

        initializeUserProfile(user);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.userMap);
        // When the map is finished loading, then it will get the location of the user and create a marker at that location
        mapFragment.getMapAsync(googleMap -> {
            Geo geo = user.getAddress().getGeo();
            map = googleMap;

            LatLng userLocation = new LatLng(Double.valueOf(geo.getLat()), Double.valueOf(geo.getLng()));

            map.addMarker(new MarkerOptions()
                    .position(userLocation)
                    .title("User Location")
                    .snippet("Location: " + geo.getLat() + ", " + geo.getLng()));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 5));
        });

        // It will make an async api call for blog posts and then on the main thread, it will initialize the recycler view with blog posts
        presenter
                .getBlogPostsByUserObservable(user)
                .observeOn(AndroidSchedulers.mainThread())
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

    // Given the user, it will load the view with the user details
    public void initializeUserProfile(User user) {
        this.name = findViewById(R.id.name);
        this.username = findViewById(R.id.username);
        this.email = findViewById(R.id.email);
        this.phoneNumber = findViewById(R.id.phoneNumber);
        this.website = findViewById(R.id.website);

        this.name.setText(user.getName());
        this.username.setText(user.getUsername());
        this.email.setText(user.getEmail());
        this.phoneNumber.setText(user.getPhone());
        this.website.setText(user.getWebsite());
    }

    // Given the blog posts, it will set up the recycler view
    public void initializeBlogPosts(List<BlogPost> blogPosts) {
        blogPostAdapter = new BlogPostAdapter(this, blogPosts, position -> {
        });

        this.recyclerView = findViewById(R.id.userPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(blogPostAdapter);
    }
}
