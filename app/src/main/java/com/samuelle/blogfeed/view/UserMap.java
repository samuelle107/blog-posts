package com.samuelle.blogfeed.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.samuelle.blogfeed.R;
import com.samuelle.blogfeed.model.Geo;

public class UserMap extends AppCompatActivity {
    private GoogleMap map;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.userMap);
        mapFragment.getMapAsync(googleMap -> {
            map = googleMap;

            Geo geo = getIntent().getExtras().getParcelable("geo");
            LatLng userLocation = new LatLng(Double.valueOf(geo.getLat()), Double.valueOf(geo.getLng()));

            map.addMarker(new MarkerOptions()
                    .position(userLocation)
                    .title("User Location")
                    .snippet("Location: " + geo.getLat() + ", " + geo.getLng()));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 5));
        });
    }
}
