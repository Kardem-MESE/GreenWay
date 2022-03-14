package com.kardemmese.greenway;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kardemmese.greenway.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapLongClickListener{

    private GoogleMap mMap;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mUsers;
    Marker marker;



    private ActivityMapsBinding binding;
    ActivityResultLauncher<String> permissionLauncher;
    LocationManager locationManager;
    LocationListener locationListener;
    SharedPreferences sharedPreferences;
    boolean info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ChildEventListener mChildEventListener;
        mUsers = FirebaseDatabase.getInstance().getReference("Users");
        mUsers.push().setValue(marker);

        registerLauncher();

        sharedPreferences = this.getSharedPreferences("com.kardemmese.greenway",MODE_PRIVATE);
        info = false;
    }
    public void save(View view) {
        Intent intent = new Intent(MapsActivity.this,NewAdvert.class);
        startActivity(intent);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);
          googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
          mUsers.addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                  for (DataSnapshot s : snapshot.getChildren()){
                      Userinformation user = s.getValue(Userinformation.class);
                      LatLng location = new LatLng(user.latitude,user.longitude);
                      mMap.addMarker(new MarkerOptions().position(location)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                  }
              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }
          });

        mMap.setOnMapLongClickListener(this);

         locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
         locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {


                 info = sharedPreferences.getBoolean("info",false);
                if (!info){
                    LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15));
                    sharedPreferences.edit().putBoolean("info",true).apply();
                }

            }

             @Override
             public void onStatusChanged(String provider, int status, Bundle extras) {

             }
         };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Snackbar.make(binding.getRoot(), "Permission needed for maps", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                }).show();
            } else {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                Location lastLoaction = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastLoaction != null){
                    LatLng lastUserLocation = new LatLng(lastLoaction.getLatitude(),lastLoaction.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,15));
                }

                mMap.setMyLocationEnabled(true);
        }





    }

    private void registerLauncher(){
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result){
                    if(ContextCompat.checkSelfPermission(MapsActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) ==PackageManager.PERMISSION_GRANTED){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);

                        Location lastLoaction = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (lastLoaction != null){
                            LatLng lastUserLocation = new LatLng(lastLoaction.getLatitude(),lastLoaction.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,15));
                        }
                    }

                }else{
                    Toast.makeText(MapsActivity.this,"Permission needed!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
         mMap.clear();
         mMap.addMarker(new MarkerOptions().position(latLng));
    }
}