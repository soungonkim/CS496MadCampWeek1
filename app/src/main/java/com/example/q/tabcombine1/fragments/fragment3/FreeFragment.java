package com.example.q.tabcombine1.fragments.fragment3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.Math;

import com.example.q.tabcombine1.R;


public class FreeFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    GeoDataClient mGeoDataClient;
    PlaceDetectionClient mPlaceDetectionClient;
    FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = FreeFragment.class.getSimpleName();
    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    private Button btnFindPath;
    private TextView tm;
    private TextView distance;

    LatLng kaistN1 = new LatLng(36.374438, 127.365583);
    LatLng northRes = new LatLng(36.373669, 127.359132);
    LatLng westRes = new LatLng(36.366932, 127.360485);
    LatLng eastRes = new LatLng(36.369180, 127.363580);
    LatLng lotte = new LatLng(36.361910, 127.379042);
    LatLng gungdong = new LatLng(36.362309, 127.351120);
    LatLng aeundong = new LatLng(36.363493, 127.358705);
    LatLng kaistdorm = new LatLng(36.373604, 127.357537);

    double yourlat, yourlng;

    public FreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_free, null, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setContentView(R.layout.fragment_free);
//        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGeoDataClient = Places.getGeoDataClient(getActivity(), null);
        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(getActivity(), null);
        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        btnFindPath = (Button) getActivity().findViewById(R.id.btnFindPath);

        distance = (TextView) getActivity().findViewById(R.id.tvDistance);
        tm = (TextView) getActivity().findViewById(R.id.tvDuration);
        btnFindPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity().getApplicationContext(), v);
                getActivity().getMenuInflater().inflate(R.menu.menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.m1:
                                Toast toast1 = Toast.makeText(getActivity().getApplicationContext(), "KAIST N1", Toast.LENGTH_SHORT);
                                toast1.setGravity(Gravity.CENTER, 0, +120);
                                toast1.show();
                                distance.setText(distanceLatLong2(yourlat, yourlng, 36.374438, 127.365583) + " m");
                                tm.setText(calTime(distanceLatLong2(yourlat, yourlng, 36.374438, 127.365583)) + " min");
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kaistN1, 15));

                                break;
                            case R.id.m2:
                                Toast toast2 = Toast.makeText(getActivity().getApplicationContext(), "북측식당", Toast.LENGTH_SHORT);
                                toast2.setGravity(Gravity.CENTER, 0, +120);
                                toast2.show();
                                distance.setText(distanceLatLong2(yourlat, yourlng, 36.373669, 127.359132) + " m");
                                tm.setText(calTime(distanceLatLong2(yourlat, yourlng, 36.373669, 127.359132)) + " min");
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(northRes, 15));

                                break;
                            case R.id.m3:
                                Toast toast3 = Toast.makeText(getActivity().getApplicationContext(), "서측식당", Toast.LENGTH_SHORT);
                                toast3.setGravity(Gravity.CENTER, 0, +120);
                                toast3.show();
                                distance.setText(distanceLatLong2(yourlat, yourlng, 36.366932, 127.360485) + " m");
                                tm.setText(calTime(distanceLatLong2(yourlat, yourlng, 36.366932, 127.360485)) + " min");
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(westRes, 15));

                                break;
                            case R.id.m4:
                                Toast toast4 = Toast.makeText(getActivity().getApplicationContext(), "동측식당", Toast.LENGTH_SHORT);
                                toast4.setGravity(Gravity.CENTER, 0, +120);
                                toast4.show();
                                distance.setText(distanceLatLong2(yourlat, yourlng, 36.369180, 127.363580) + " m");
                                tm.setText(calTime(distanceLatLong2(yourlat, yourlng, 36.369180, 127.363580)) + " min");
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eastRes, 15));

                                break;
                            case R.id.m5:
                                Toast toast5 = Toast.makeText(getActivity().getApplicationContext(), "둔산동 롯데시네마", Toast.LENGTH_SHORT);
                                toast5.setGravity(Gravity.CENTER, 0, +120);
                                toast5.show();
                                distance.setText(distanceLatLong2(yourlat, yourlng, 36.361910, 127.379042) + " m");
                                tm.setText(calTime(distanceLatLong2(yourlat, yourlng, 36.361910, 127.379042)) + " min");
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lotte, 15));
                                break;
                            case R.id.m6:
                                Toast toast6 = Toast.makeText(getActivity().getApplicationContext(), "궁동 로데오거리", Toast.LENGTH_SHORT);
                                toast6.setGravity(Gravity.CENTER, 0, +120);
                                toast6.show();
                                distance.setText(distanceLatLong2(yourlat, yourlng, 36.362309, 127.351120) + " m");
                                tm.setText(calTime(distanceLatLong2(yourlat, yourlng, 36.362309, 127.351120)) + " min");
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gungdong, 15));

                                break;
                            case R.id.m7:
                                Toast toast7 = Toast.makeText(getActivity().getApplicationContext(), "어은동 통통왕숯불구이", Toast.LENGTH_SHORT);
                                toast7.setGravity(Gravity.CENTER, 0, +120);
                                toast7.show();
                                distance.setText(distanceLatLong2(yourlat, yourlng, 36.363493, 127.358705) + " m");
                                tm.setText(calTime(distanceLatLong2(yourlat, yourlng, 36.363493, 127.358705)) + " min");
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aeundong, 15));

                                break;
                            case R.id.m8:
                                Toast toast8 = Toast.makeText(getActivity().getApplicationContext(), "기숙사", Toast.LENGTH_SHORT);
                                toast8.setGravity(Gravity.CENTER, 0, +120);
                                toast8.show();
                                distance.setText(distanceLatLong2(yourlat, yourlng, 36.373604, 127.357537) + " m");
                                tm.setText(calTime(distanceLatLong2(yourlat, yourlng, 36.373604, 127.357537)) + " min");
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kaistdorm, 15));
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
                ;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(kaistN1).title("N1"));
        mMap.addMarker(new MarkerOptions().position(northRes).title("북측식당"));
        mMap.addMarker(new MarkerOptions().position(westRes).title("서측식당"));
        mMap.addMarker(new MarkerOptions().position(eastRes).title("동측식당"));
        mMap.addMarker(new MarkerOptions().position(lotte).title("둔산동 롯데시네마"));
        mMap.addMarker(new MarkerOptions().position(gungdong).title("궁동 로데오거리"));
        mMap.addMarker(new MarkerOptions().position(aeundong).title("어은동 한빛교회"));
        mMap.addMarker(new MarkerOptions().position(kaistdorm).title("기숙사"));

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kaistN1, 15));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                getDeviceLocation();
                return true;
            }
        });

        getLocationPermission();
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();
        // Get the current location of the device and set the position of the map.
        getDeviceLocation();


    }

    public static String distanceLatLong2(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371.0d; // KM: use mile here if you want mile result

        double dLat = toRadian(lat2 - lat1);
        double dLng = toRadian(lng2 - lng1);

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.cos(toRadian(lat1)) * Math.cos(toRadian(lat2)) *
                        Math.pow(Math.sin(dLng / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return Double.toString(Math.round(earthRadius * c * 1000d) / 1000d * 1000); // returns result kilometers
    }

    public static String calTime(String distance) {
        double dist = Double.parseDouble(distance);
        double time = dist / 70;
        return Double.toString(Math.round(time * 10d) / 10d);
    }


    public static double toRadian(double degrees) {
        return (degrees * Math.PI) / 180.0d;
    }


    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            yourlat = mLastKnownLocation.getLatitude();
                            yourlng = mLastKnownLocation.getLongitude();
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

}