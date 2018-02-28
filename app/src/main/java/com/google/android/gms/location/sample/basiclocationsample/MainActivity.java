

package com.google.android.gms.location.sample.basiclocationsample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.support.v4.app.FragmentActivity;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.*;



import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    private GoogleMap mMap;
    private Button btn1;
    private Button stp1;
    private Button stp2;
    private Button stp3;
    private int choice = 4;

    private FusedLocationProviderClient mFusedLocationClient;


    protected Location mLastLocation;


    private TextView mLatitudeText;
    private TextView mLongitudeText;
    private ArrayList<durak> duraklist;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        mLatitudeText = (TextView) findViewById((R.id.latitude_text));
        mLongitudeText = (TextView) findViewById((R.id.longitude_text));

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        duraklist = new ArrayList<durak>();
        duraklist.add(new durak(39.942826, 32.682008,"511","Et3","Kiz16"));
        duraklist.add(new durak(39.947838, 32.667684,"511","Et2","Kiz16"));
        duraklist.add(new durak(39.959887, 32.604978,"511","Sin1","Kiz16"));
        duraklist.add(new durak(39.907500, 32.762554,"511","Bil1","Kiz16"));

        duraklist.add(new durak(39.965574, 32.634938,"510","Er1","Kiz13"));
        duraklist.add(new durak(39.962651, 32.663171,"510","Er3","Kiz13"));
        duraklist.add(new durak(39.945166, 32.714020,"510","Sas1","Kiz13"));
        duraklist.add(new durak(39.953152, 32.832271,"510","Akk1","Kiz13"));

        duraklist.add(new durak(39.984887, 32.691372,"201-6","Bat1","Kiz14"));
        duraklist.add(new durak(39.969644, 32.715319,"201-6","Bat2","Kiz14"));
        duraklist.add(new durak(39.964242, 32.749244,"201-6","Bat3","Kiz14"));
        duraklist.add(new durak(39.965086, 32.797023,"201-6","Yen1","Kiz14"));

        btn1 = (Button) findViewById(R.id.rButton);
        btn1.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick (View v)
            {

                if (!checkPermissions()) {
                    requestPermissions();
                } else {
                    choice = 4;
                    getLastLocation();
                }
            }
        });
        stp1 = (Button) findViewById(R.id.button1);
        stp1.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick (View v)
            {

                if (!checkPermissions()) {
                    requestPermissions();
                } else {
                    choice = 1;
                    getLastLocation();
                }
            }
        });
        stp2 = (Button) findViewById(R.id.button2);
        stp2.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick (View v)
            {

                if (!checkPermissions()) {
                    requestPermissions();
                } else {
                    choice = 2;
                    getLastLocation();
                }
            }
        });
        stp3 = (Button) findViewById(R.id.button3);
        stp3.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick (View v)
            {

                if (!checkPermissions()) {
                    requestPermissions();
                } else {
                    choice = 3;
                    getLastLocation();
                }
            }
        });

    }




    public durak getmebest() {
        int returning = 0;
        Location loc = new Location(mLastLocation);
        loc.setLatitude(duraklist.get(0).getX());
        loc.setLongitude(duraklist.get(0).getY());
        double mindist =  mLastLocation.distanceTo(loc);
        for(int i = 1; i < duraklist.size(); i++) {

            loc.setLatitude(duraklist.get(i).getX());
            loc.setLongitude(duraklist.get(i).getY());
            double dist =  mLastLocation.distanceTo(loc);
            if(dist < mindist) {
                mindist = dist;
                returning = i;
            }
        }

        return duraklist.get(returning);
    }
   /* public durak getmebest(ArrayList<durak> duraklist, double x, double y) {
        int returning = 0;
        double mindist = Math.sqrt((duraklist.get(0).getX()-x)*(duraklist.get(0).getX()-x) + (duraklist.get(0).getY()-y)* (duraklist.get(0).getY()-y));

        for(int i = 1; i < duraklist.size(); i++) {

            double dist = Math.sqrt((duraklist.get(i).getX()-x)*(duraklist.get(i).getX()-x) + (duraklist.get(i).getY()-y)* (duraklist.get(i).getY()-y));
            System.out.println(dist);
            if(dist < mindist) {
                mindist = dist;
                returning = i;
            }
        }

        return duraklist.get(returning);
    }
*/
    @Override
    public void onStart() {
        super.onStart();

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }
    }

    public float distStop(durak togo){
        Location loc = new Location(mLastLocation);
        loc.setLatitude(togo.getX());
        loc.setLongitude(togo.getY());
        return mLastLocation.distanceTo(loc);
    }


    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            mMap.clear();
                            LatLng myLoc = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(myLoc).title("My Location"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLoc));
                            if(choice == 1){
                                durak togo = getmebest();
                                LatLng newdurak = new LatLng(togo.getX(),togo.getY());


                                mMap.addMarker(new MarkerOptions().position(newdurak).title("First Bus Stop: " + togo.getBusstop() + " Dist: " + distStop(togo) + " m"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newdurak, 14.5f));
                                mLatitudeText.setText("First EGO No: " + togo.getOtobus());
                                mLongitudeText.setText("First Dest Stop:" + togo.getDeststop());
                            }else if(choice == 2){
                                durak togo = getmebest();
                                int ind = duraklist.indexOf(togo);
                                duraklist.remove(ind);
                                durak togo2 = getmebest();
                                LatLng newdurak = new LatLng(togo2.getX(),togo2.getY());
                                mMap.addMarker(new MarkerOptions().position(newdurak).title("Second Bus Stop: " + togo2.getBusstop() + " Dist: " + distStop(togo2) + " m"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newdurak, 14.5f));
                                duraklist.add(togo);
                                mLatitudeText.setText("Second EGO No: " + togo2.getOtobus());
                                mLongitudeText.setText("Second Dest Stop:" + togo2.getDeststop());
                            }else if(choice == 3){
                                durak togo = getmebest();


                                int ind = duraklist.indexOf(togo);
                                duraklist.remove(ind);
                                durak togo2 = getmebest();


                                ind = duraklist.indexOf(togo2);
                                duraklist.remove(ind);
                                durak togo3 = getmebest();
                                LatLng newdurak = new LatLng(togo3.getX(),togo3.getY());
                                mMap.addMarker(new MarkerOptions().position(newdurak).title("Third Bus Stop: " + togo3.getBusstop() + " Dist: " + distStop(togo3) + " m"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newdurak, 14.5f));

                                duraklist.add(togo);
                                duraklist.add(togo2);


                                mLatitudeText.setText("Third EGO No: " + togo3.getOtobus());
                                mLongitudeText.setText("Third Dest Stop:" + togo3.getDeststop());

                            }else{
                                durak togo = getmebest();
                                LatLng newdurak = new LatLng(togo.getX(),togo.getY());


                                mMap.addMarker(new MarkerOptions().position(newdurak).title("First Bus Stop: " + togo.getBusstop() + " Dist: " + distStop(togo) + " m"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newdurak, 12.0f));

                                int ind = duraklist.indexOf(togo);
                                duraklist.remove(ind);
                                durak togo2 = getmebest();
                                newdurak = new LatLng(togo2.getX(),togo2.getY());
                                mMap.addMarker(new MarkerOptions().position(newdurak).title("Second Bus Stop: " + togo2.getBusstop() + " Dist: " + distStop(togo2) + " m"));


                                ind = duraklist.indexOf(togo2);
                                duraklist.remove(ind);
                                durak togo3 = getmebest();
                                newdurak = new LatLng(togo3.getX(),togo3.getY());
                                mMap.addMarker(new MarkerOptions().position(newdurak).title("Third Bus Stop: " + togo3.getBusstop() + " Dist: " + distStop(togo3) + " m"));


                                duraklist.add(togo);
                                duraklist.add(togo2);


                                mLatitudeText.setText("First EGO No: " + togo.getOtobus());
                                mLongitudeText.setText("First Dest Stop:" + togo.getDeststop());

                            }




                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                            showSnackbar(getString(R.string.no_location_detected));
                        }
                    }
                });
    }


    private void showSnackbar(final String text) {
        View container = findViewById(R.id.main_activity_container);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }


    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }


    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");

            startLocationPermissionRequest();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {

                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getLastLocation();
            } else {

                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }
}
