package com.example.pinpointer.app;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.*;
import android.content.Context;
import android.view.View;
import android.widget.*;

public class MapsActivity extends FragmentActivity {
    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private MyLocationListener mLocationListener;
    private LocationManager mLocationManager;
    private Location currentLocation;
    int i = 0;
    Button refresh;
    Marker marker;

    public class MyLocationListener implements LocationListener {

        //private final String TAG = MyLocationListener.class.getSimpleName();

        @Override
        public void onLocationChanged(Location location) {
            //GPS.this.main.locationChanged(loc.getLongitude(), loc.getLatitude());
            currentLocation = location;
        }

        @Override
        public void onProviderDisabled(String provider) {
            //GPS.this.main.displayGPSSettingsDialog();
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setUpMapIfNeeded();
        refresh = (Button) findViewById(R.id.select);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSwag();
            }
        });
        mLocationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        mLocationListener = new MyLocationListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        updateSwag();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
        mMap.setMyLocationEnabled(true);
        Marker hamburg = mMap.addMarker(new MarkerOptions().position(HAMBURG)
                .title("Hamburg"));
        Marker kiel = mMap.addMarker(new MarkerOptions()
                .position(KIEL)
                .title("Kiel")
                .snippet("Kiel is cool")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_launcher)));

        // Move the camera instantly to hamburg with a zoom of 15.
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

        // Zoom in, animating the camera.
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
    private void updateSwag(){
        String provider = LocationManager.GPS_PROVIDER;
        //long minTime = 0;
        //float minDistance = 0;
        //mLocationManager.requestLocationUpdates(provider, minTime, minDistance,mLocationListener);
        currentLocation = mLocationManager.getLastKnownLocation(provider);
        LatLng here = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        if(i == 0)
        {
            marker = mMap.addMarker(new MarkerOptions()
                    .position(here)
                    .title("Blow Me")
                    .snippet(""));
            mMap.addMarker(new MarkerOptions().position(new LatLng(currentLocation.getLatitude()+0.02,currentLocation.getLongitude()+0.02)).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(5), 2000, null);
            i++;
        }
        marker.setPosition(here);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(here, 10));
        // Zoom in, animating the camera.
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(5), 2000, null);

    }
}
