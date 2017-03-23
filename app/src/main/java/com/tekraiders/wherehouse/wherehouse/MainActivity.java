package com.tekraiders.wherehouse.wherehouse;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.drawable.shapes.Shape;
import android.location.Location;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.Geometry;
import com.google.maps.android.data.Layer;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonGeometryCollection;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonLineString;
import com.google.maps.android.data.geojson.GeoJsonMultiLineString;
import com.google.maps.android.data.geojson.GeoJsonMultiPoint;
import com.google.maps.android.data.geojson.GeoJsonMultiPolygon;
import com.google.maps.android.data.geojson.GeoJsonPoint;
import com.google.maps.android.data.geojson.GeoJsonPolygon;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;
import com.tekraiders.wherehouse.wherehouse.drawer.NavigationDrawerFragment;
import com.tekraiders.wherehouse.wherehouse.tabs.SlidingTabsLayout;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks ,
        LocationListener,GoogleMap.OnPolygonClickListener, GoogleMap.OnMapClickListener{
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 2;
    private static final String TAG = "SahajLOG";
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;
    private CameraPosition mCameraPosition;
    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(28.5272181,77.0689018);
    private static final int DEFAULT_ZOOM = 5;

    private HashMap<String,Marker> hashMapMarker = new HashMap<>();

    private SlidingTabsLayout mTabs;
    private LocationRequest mLocationRequest;
    private Location location;
    private SupportMapFragment mapFragment;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 3;
    private GeoJsonLayer layer,layerA,layerB,layerC,layerD,layerE,layer2,layer3,layer4,layerE1,layerE2,layerE3,layerE4,layerE5,layerE6,layerE7,layerE8,layerE9,layerE10,layerE11,layerE12,layerE13,layerE14,layerE15;
    private ArrayList<LatLngBounds> latLngBoundsArray,latLngBoundsArrayA,latLngBoundsArrayB,latLngBoundsArrayC,latLngBoundsArrayD,latLngBoundsArrayE,latLngBoundsArray2,latLngBoundsArray3,latLngBoundsArray4,latLngBoundsArrayE1,latLngBoundsArrayE2,latLngBoundsArrayE3,latLngBoundsArrayE4,latLngBoundsArrayE5,latLngBoundsArrayE6,latLngBoundsArrayE7,latLngBoundsArrayE8,latLngBoundsArrayE9,latLngBoundsArrayE10,latLngBoundsArrayE11,latLngBoundsArrayE12,latLngBoundsArrayE13,latLngBoundsArrayE14,latLngBoundsArrayE15;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolbar=(Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        setTitle("");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        NavigationDrawerFragment drawerFragment=(NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_frag);
        drawerFragment.setUp(R.id.nav_frag, (DrawerLayout) findViewById(R.id.drawerLayout), mToolbar);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        MainActivity.this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds



    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            try {
                Toast.makeText(MainActivity.this, "One moment...", Toast.LENGTH_SHORT).show();
                AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                        .setCountry("IN")
                        .build();

                Intent intent =
                        new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                .setFilter(typeFilter)
                                .build(this);
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            } catch (GooglePlayServicesRepairableException e) {
                // TODO: Handle the error.
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                // TODO: Handle the error.
                e.printStackTrace();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place: " + place.getName());
                LatLng latLng = place.getLatLng();
                try {
                    int i = 0;

                    //Log.e("SahajLOG", " latlang > " + latLng + " " + layer.getBoundingBox().contains(latLng) + " " + layer.getBoundingBox().including(latLng));
                    String MarkerString = "location marked";

                    makeLayerWork(layerA, latLng, latLngBoundsArrayA);
                    makeLayerWork(layerB, latLng, latLngBoundsArrayB);
                    makeLayerWork(layerC, latLng, latLngBoundsArrayC);
                    makeLayerWork(layerD, latLng, latLngBoundsArrayD);
                    makeLayerWork(layerE, latLng, latLngBoundsArrayE);


                    i = 0;
                    for (LatLngBounds bbc : latLngBoundsArray) {
                        Log.e("SahajLOG", "callad A1" + bbc.contains(latLng));
                        if (bbc.contains(latLng)) {
                            i++;
                            layer.addLayerToMap();
                            MarkerString = "Cyclone ZONE 1";
                        } else if (layer.isLayerOnMap() && i == 0) {
                            layer.removeLayerFromMap();
                        }
                    }


                    i = 0;
                    for (LatLngBounds bbc : latLngBoundsArray2) {
                        Log.e("SahajLOG", "callad A2" + bbc.contains(latLng));
                        if (bbc.contains(latLng)) {
                            i++;
                            layer2.addLayerToMap();
                            MarkerString = "Cyclone ZONE 2";
                        } else if (layer2.isLayerOnMap() && i == 0) {
                            layer2.removeLayerFromMap();
                        }
                    }


                    i = 0;
                    for (LatLngBounds bbc : latLngBoundsArray3) {
                        Log.e("SahajLOG", "callad A3" + bbc.contains(latLng));
                        if (bbc.contains(latLng)) {
                            i++;
                            layer3.addLayerToMap();
                            MarkerString = "Cyclone ZONE 3";
                        } else if (layer3.isLayerOnMap() && i == 0) {
                            layer3.removeLayerFromMap();
                        }
                    }


                    i = 0;
                    for (LatLngBounds bbc : latLngBoundsArray4) {
                        Log.e("SahajLOG", "callad A4" + bbc.contains(latLng));
                        if (bbc.contains(latLng)) {
                            i++;
                            layer4.addLayerToMap();
                            MarkerString = "Cyclone ZONE 4";
                        } else if (layer4.isLayerOnMap() && i == 0) {
                            layer4.removeLayerFromMap();
                        }
                    }

                    if (hashMapMarker != null) {
                        if (hashMapMarker.get("otherLocMark") != null) {
                            removeUpMaker("otherLocMark");
                            setUpMaker(latLng.latitude, latLng.longitude, "otherLocMark", MarkerString);
                        } else
                            setUpMaker(latLng.latitude, latLng.longitude, "otherLocMark", MarkerString);
                    } else
                        setUpMaker(latLng.latitude, latLng.longitude, "otherLocMark", MarkerString);

                } catch (Exception e) {
                    e.printStackTrace();
                }



            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private void setUpMaker(double currentLatitude,double currentLongitude,String Key,String title) {

        Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title(title));
        hashMapMarker.put(Key, marker);
        marker.showInfoWindow();
      //  Log.e("SahajLOG", "setUpMarker > " +marker +" key "+Key);
        //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("I am here!"));
    }
    private void removeUpMaker(String Key) {
        if (hashMapMarker!=null) {
            Marker marker = hashMapMarker.get(Key);
            if (marker != null)
                marker.remove();
            hashMapMarker.remove(Key);
            Log.e("SahajLOG", "removeMarker > " + marker + " key " + Key);
        }
    }

    private void getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        // A step later in the tutorial adds the code to get the device location.
        /*
     * Before getting the device location, you must check location
     * permission, as described earlier in the tutorial. Then:
     * Get the best and most recent location of the device, which may be
     * null in rare cases when a location is not available.
     */
        if (mLocationPermissionGranted) {
            mLastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastKnownLocation == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }
        Log.e("SahajLOG", "Prem Granted " + mLocationPermissionGranted + " mLastKnowLoc " + mLastKnownLocation);
        // Set the map's camera position to the current location of the device.
        if (mCameraPosition != null) {
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
        } else if (mLastKnownLocation != null) {

            if (hashMapMarker!=null) {
                if (hashMapMarker.get("MyLocation") != null) {
                    removeUpMaker("MyLocation");
                    setUpMaker(mLastKnownLocation.getLatitude(),
                            mLastKnownLocation.getLongitude(), "MyLocation", "I am here!");
                } else
                    setUpMaker(mLastKnownLocation.getLatitude(),
                            mLastKnownLocation.getLongitude(), "MyLocation", "I am here!");
            }else
                setUpMaker(mLastKnownLocation.getLatitude(),
                        mLastKnownLocation.getLongitude(), "MyLocation", "I am here!");

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mLastKnownLocation.getLatitude(),
                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));

        } else {
            Log.e("SahajLOG", "Current location is null. Using defaults.");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }

    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mLastKnownLocation = null;
        }
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
        Log.e("SahajLOG", "**ON MAP READY**");
        mMap = googleMap;
        //MapFragment mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // Add polylines and polygons to the map. This section shows just
        // a single polyline. Read the rest of the tutorial to learn more.
        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-35.016, 143.321),
                        new LatLng(-34.747, 145.592),
                        new LatLng(-34.364, 147.891),
                        new LatLng(-33.501, 150.217),
                        new LatLng(-32.306, 149.248),
                        new LatLng(-32.491, 147.309)));

        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.

        //--googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.684, 133.903), 4));

        // Set listeners for click events.
     //   googleMap.setOnPolylineClickListener(MainActivity.this);
        googleMap.setOnPolygonClickListener(MainActivity.this);
        googleMap.setOnMapClickListener(MainActivity.this);

        try {
             layer = new GeoJsonLayer(mMap, R.raw.cyclone1_1,
                    getApplicationContext());
            layerA = new GeoJsonLayer(mMap, R.raw.cyclone1_2,
                    getApplicationContext());
            layerB = new GeoJsonLayer(mMap, R.raw.cyclone1_3,
                    getApplicationContext());
            layerC = new GeoJsonLayer(mMap, R.raw.cyclone1_4,
                    getApplicationContext());
            layerD = new GeoJsonLayer(mMap, R.raw.cyclone1_5,
                    getApplicationContext());
            layerE = new GeoJsonLayer(mMap, R.raw.cyclone1_6,
                    getApplicationContext());
             layer2 = new GeoJsonLayer(mMap, R.raw.cyclone2_1,
                    getApplicationContext());
             layer3 = new GeoJsonLayer(mMap, R.raw.cyclone2_2,
                    getApplicationContext());
             layer4 = new GeoJsonLayer(mMap, R.raw.cyclone2_3,
                    getApplicationContext());
           /* layerE1 = new GeoJsonLayer(mMap, R.raw.ert_1_1,
                    getApplicationContext());
            layerE2= new GeoJsonLayer(mMap, R.raw.ert_1_3,
                    getApplicationContext());
            layerE3= new GeoJsonLayer(mMap, R.raw.ert_1_4,
                    getApplicationContext());
            layerE4= new GeoJsonLayer(mMap, R.raw.ert_1_5,
                    getApplicationContext());
            layerE5= new GeoJsonLayer(mMap, R.raw.ert_1_6,
                    getApplicationContext());
            layerE6= new GeoJsonLayer(mMap, R.raw.ert_2_1,
                    getApplicationContext());
            layerE7 = new GeoJsonLayer(mMap, R.raw.ert_2_2,
                    getApplicationContext());
            layerE8 = new GeoJsonLayer(mMap, R.raw.ert_2_3,
                    getApplicationContext());
            layerE9 = new GeoJsonLayer(mMap, R.raw.ert_2_4,
                    getApplicationContext());
            layerE10 = new GeoJsonLayer(mMap, R.raw.ert_3_1,
                    getApplicationContext());
            layerE11 = new GeoJsonLayer(mMap, R.raw.ert_3_2,
                    getApplicationContext());
            layerE12 = new GeoJsonLayer(mMap, R.raw.ert_4_1,
                    getApplicationContext());
            layerE13 = new GeoJsonLayer(mMap, R.raw.ert_4_2,
                    getApplicationContext());
            layerE14 = new GeoJsonLayer(mMap, R.raw.ert_4_3,
                    getApplicationContext());
            layerE15 = new GeoJsonLayer(mMap, R.raw.ert_1_2,
                    getApplicationContext());
                    */
            // layer.addLayerToMap();
            GeoJsonPolygonStyle polygonStyle=layer.getDefaultPolygonStyle();
            polygonStyle.setFillColor(0x32e74c3c);
            polygonStyle.setStrokeWidth(1.5f);
             latLngBoundsArray = new ArrayList<>();
            latLngBoundsArray.addAll(getLatLngBoundingBox(layer));

            GeoJsonPolygonStyle polygonStyleA=layerA.getDefaultPolygonStyle();
            polygonStyleA.setFillColor(0x32e74c3c);
            polygonStyleA.setStrokeWidth(1.5f);
            latLngBoundsArrayA = new ArrayList<>();
            latLngBoundsArrayA.addAll(getLatLngBoundingBox(layerA));

            GeoJsonPolygonStyle polygonStyleB=layerB.getDefaultPolygonStyle();
            polygonStyleB.setFillColor(0x32e74c3c);
            polygonStyleB.setStrokeWidth(1.5f);
            latLngBoundsArrayB = new ArrayList<>();
            latLngBoundsArrayB.addAll(getLatLngBoundingBox(layerB));

            GeoJsonPolygonStyle polygonStyleC=layerC.getDefaultPolygonStyle();
            polygonStyleC.setFillColor(0x32e74c3c);
            polygonStyleC.setStrokeWidth(1.5f);
            latLngBoundsArrayC = new ArrayList<>();
            latLngBoundsArrayC.addAll(getLatLngBoundingBox(layerC));

            GeoJsonPolygonStyle polygonStyleD=layerD.getDefaultPolygonStyle();
            polygonStyleD.setFillColor(0x32e74c3c);
            polygonStyleD.setStrokeWidth(1.5f);
            latLngBoundsArrayD = new ArrayList<>();
            latLngBoundsArrayD.addAll(getLatLngBoundingBox(layerD));

            GeoJsonPolygonStyle polygonStyleE=layerE.getDefaultPolygonStyle();
            polygonStyleE.setFillColor(0x32e74c3c);
            polygonStyleE.setStrokeWidth(1.5f);
            latLngBoundsArrayE = new ArrayList<>();
            latLngBoundsArrayE.addAll(getLatLngBoundingBox(layerE));

           // layer2.addLayerToMap();
            GeoJsonPolygonStyle polygonStyle2=layer2.getDefaultPolygonStyle();
            polygonStyle2.setFillColor(0x32e74c3c);
            polygonStyle2.setStrokeWidth(1.5f);
            latLngBoundsArray2 = new ArrayList<>();
            latLngBoundsArray2.addAll(getLatLngBoundingBox(layer2));

            GeoJsonPolygonStyle polygonStyle3=layer3.getDefaultPolygonStyle();
            polygonStyle3.setFillColor(0x32e74c3c);
            polygonStyle3.setStrokeWidth(1.5f);
             latLngBoundsArray3 = new ArrayList<>();
            latLngBoundsArray3.addAll(getLatLngBoundingBox(layer3));

            GeoJsonPolygonStyle polygonStyle4=layer4.getDefaultPolygonStyle();
            polygonStyle4.setFillColor(0x32e74c3c);
            polygonStyle4.setStrokeWidth(1.5f);
             latLngBoundsArray4 = new ArrayList<>();
            latLngBoundsArray4.addAll(getLatLngBoundingBox(layer4));

         /*   // layer.addLayerToMap();
            GeoJsonPolygonStyle polygonStyleE1=layerE1.getDefaultPolygonStyle();
            polygonStyleE1.setFillColor(0x32e74c3c);
            polygonStyleE1.setStrokeWidth(1.5f);
            latLngBoundsArrayE1 = new ArrayList<>();
            latLngBoundsArrayE1.addAll(getLatLngBoundingBox(layerE1));

            // layer2.addLayerToMap();
            GeoJsonPolygonStyle polygonStyleE2=layerE2.getDefaultPolygonStyle();
            polygonStyleE2.setFillColor(0x32e74c3c);
            polygonStyleE2.setStrokeWidth(1.5f);
            latLngBoundsArrayE2 = new ArrayList<>();
            latLngBoundsArrayE2.addAll(getLatLngBoundingBox(layerE2));

            GeoJsonPolygonStyle polygonStyleE3=layerE3.getDefaultPolygonStyle();
            polygonStyleE3.setFillColor(0x32e74c3c);
            polygonStyleE3.setStrokeWidth(1.5f);
            latLngBoundsArrayE3 = new ArrayList<>();
            latLngBoundsArrayE3.addAll(getLatLngBoundingBox(layerE3));

            GeoJsonPolygonStyle polygonStyleE4=layerE4.getDefaultPolygonStyle();
            polygonStyleE4.setFillColor(0x32e74c3c);
            polygonStyleE4.setStrokeWidth(1.5f);
            latLngBoundsArrayE4 = new ArrayList<>();
            latLngBoundsArrayE4.addAll(getLatLngBoundingBox(layerE4));

            // layer.addLayerToMap();
            GeoJsonPolygonStyle polygonStyleE5=layerE5.getDefaultPolygonStyle();
            polygonStyleE5.setFillColor(0x32e74c3c);
            polygonStyleE5.setStrokeWidth(1.5f);
            latLngBoundsArrayE5 = new ArrayList<>();
            latLngBoundsArrayE5.addAll(getLatLngBoundingBox(layerE5));

            // layer2.addLayerToMap();
            GeoJsonPolygonStyle polygonStyleE6=layerE6.getDefaultPolygonStyle();
            polygonStyleE6.setFillColor(0x32e74c3c);
            polygonStyleE6.setStrokeWidth(1.5f);
            latLngBoundsArrayE6 = new ArrayList<>();
            latLngBoundsArrayE6.addAll(getLatLngBoundingBox(layerE6));

            GeoJsonPolygonStyle polygonStyleE7=layerE7.getDefaultPolygonStyle();
            polygonStyleE7.setFillColor(0x32e74c3c);
            polygonStyleE7.setStrokeWidth(1.5f);
            latLngBoundsArrayE7 = new ArrayList<>();
            latLngBoundsArrayE7.addAll(getLatLngBoundingBox(layerE7));

            GeoJsonPolygonStyle polygonStyleE8=layerE8.getDefaultPolygonStyle();
            polygonStyleE8.setFillColor(0x32e74c3c);
            polygonStyleE8.setStrokeWidth(1.5f);
            latLngBoundsArrayE8 = new ArrayList<>();
            latLngBoundsArrayE8.addAll(getLatLngBoundingBox(layerE8));

            // layer.addLayerToMap();
            GeoJsonPolygonStyle polygonStyleE9=layerE9.getDefaultPolygonStyle();
            polygonStyleE9.setFillColor(0x32e74c3c);
            polygonStyleE9.setStrokeWidth(1.5f);
            latLngBoundsArrayE9 = new ArrayList<>();
            latLngBoundsArrayE9.addAll(getLatLngBoundingBox(layerE9));

            // layer2.addLayerToMap();
            GeoJsonPolygonStyle polygonStyleE10=layerE10.getDefaultPolygonStyle();
            polygonStyleE10.setFillColor(0x32e74c3c);
            polygonStyleE10.setStrokeWidth(1.5f);
            latLngBoundsArrayE10 = new ArrayList<>();
            latLngBoundsArrayE10.addAll(getLatLngBoundingBox(layerE10));

            GeoJsonPolygonStyle polygonStyleE11=layerE11.getDefaultPolygonStyle();
            polygonStyleE11.setFillColor(0x32e74c3c);
            polygonStyleE11.setStrokeWidth(1.5f);
            latLngBoundsArrayE11 = new ArrayList<>();
            latLngBoundsArrayE11.addAll(getLatLngBoundingBox(layerE11));

            GeoJsonPolygonStyle polygonStyleE12=layerE12.getDefaultPolygonStyle();
            polygonStyleE12.setFillColor(0x32e74c3c);
            polygonStyleE12.setStrokeWidth(1.5f);
            latLngBoundsArrayE12 = new ArrayList<>();
            latLngBoundsArrayE12.addAll(getLatLngBoundingBox(layerE12));

            // layer.addLayerToMap();
            GeoJsonPolygonStyle polygonStyleE13=layerE13.getDefaultPolygonStyle();
            polygonStyleE13.setFillColor(0x32e74c3c);
            polygonStyleE13.setStrokeWidth(1.5f);
            latLngBoundsArrayE13 = new ArrayList<>();
            latLngBoundsArrayE13.addAll(getLatLngBoundingBox(layerE13));

            // layer2.addLayerToMap();
            GeoJsonPolygonStyle polygonStyleE14=layerE14.getDefaultPolygonStyle();
            polygonStyleE14.setFillColor(0x32e74c3c);
            polygonStyleE14.setStrokeWidth(1.5f);
            latLngBoundsArrayE14 = new ArrayList<>();
            latLngBoundsArrayE14.addAll(getLatLngBoundingBox(layerE14));

            GeoJsonPolygonStyle polygonStyleE15=layerE15.getDefaultPolygonStyle();
            polygonStyleE15.setFillColor(0x32e74c3c);
            polygonStyleE15.setStrokeWidth(1.5f);
            latLngBoundsArrayE15 = new ArrayList<>();
            latLngBoundsArrayE15.addAll(getLatLngBoundingBox(layerE15));

             mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {

                }
            });

               layer.setOnFeatureClickListener(MainActivity.this);
            layerA.setOnFeatureClickListener(MainActivity.this);
            layerB.setOnFeatureClickListener(MainActivity.this);
            layerC.setOnFeatureClickListener(MainActivity.this);
            layerD.setOnFeatureClickListener(MainActivity.this);
            layerE.setOnFeatureClickListener(MainActivity.this);
            layer2.setOnFeatureClickListener(MainActivity.this);
            layer3.setOnFeatureClickListener(MainActivity.this);
            layer4.setOnFeatureClickListener(MainActivity.this);

            */



           /* layer.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    try {
                        removeAllLayers();
                        if (hashMapMarker != null)
                            if (hashMapMarker.get("otherLocMark") != null)
                                removeUpMaker("otherLocMark");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            makeLayerListerner(layerA);
            makeLayerListerner(layerB);
            makeLayerListerner(layerC);
            makeLayerListerner(layerD);
            makeLayerListerner(layerE);
            layer2.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    try {
                        if (hashMapMarker != null)
                            if (hashMapMarker.get("otherLocMark") != null)
                                removeUpMaker("otherLocMark");
                        removeAllLayers();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            layer3.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    try{
                        if (hashMapMarker != null)
                            if (hashMapMarker.get("otherLocMark") != null)
                                removeUpMaker("otherLocMark");
                        removeAllLayers();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            layer4.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    try{
                        if (hashMapMarker != null)
                            if (hashMapMarker.get("otherLocMark") != null)
                                removeUpMaker("otherLocMark");
                        removeAllLayers();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            layerE1.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    try{
                        if (hashMapMarker != null)
                            if (hashMapMarker.get("otherLocMark") != null)
                                removeUpMaker("otherLocMark");
                        removeAllLayers();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            layerE2.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    try{
                        if (hashMapMarker != null)
                            if (hashMapMarker.get("otherLocMark") != null)
                                removeUpMaker("otherLocMark");
                        removeAllLayers();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            layerE3.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    try{
                        if (hashMapMarker != null)
                            if (hashMapMarker.get("otherLocMark") != null)
                                removeUpMaker("otherLocMark");
                        removeAllLayers();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            layerE4.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    try{
                        if (hashMapMarker != null)
                            if (hashMapMarker.get("otherLocMark") != null)
                                removeUpMaker("otherLocMark");
                        removeAllLayers();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            layerE5.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    try{
                        if (hashMapMarker != null)
                            if (hashMapMarker.get("otherLocMark") != null)
                                removeUpMaker("otherLocMark");
                        removeAllLayers();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            layerE6.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    try{
                        if (hashMapMarker != null)
                            if (hashMapMarker.get("otherLocMark") != null)
                                removeUpMaker("otherLocMark");
                        removeAllLayers();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            layerE7.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    try{
                        if (hashMapMarker != null)
                            if (hashMapMarker.get("otherLocMark") != null)
                                removeUpMaker("otherLocMark");
                        removeAllLayers();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            layerE8.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    try{
                        if (hashMapMarker != null)
                            if (hashMapMarker.get("otherLocMark") != null)
                                removeUpMaker("otherLocMark");
                        removeAllLayers();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            layerE9.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    try{
                        if (hashMapMarker != null)
                            if (hashMapMarker.get("otherLocMark") != null)
                                removeUpMaker("otherLocMark");
                        removeAllLayers();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            layerE10.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    try{
                        if (hashMapMarker != null)
                            if (hashMapMarker.get("otherLocMark") != null)
                                removeUpMaker("otherLocMark");
                        removeAllLayers();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            layerE11.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    try{
                        if (hashMapMarker != null)
                            if (hashMapMarker.get("otherLocMark") != null)
                                removeUpMaker("otherLocMark");
                        removeAllLayers();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            layerE12.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    try{
                        if (hashMapMarker != null)
                            if (hashMapMarker.get("otherLocMark") != null)
                                removeUpMaker("otherLocMark");
                        removeAllLayers();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            layerE13.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    try{
                        if (hashMapMarker != null)
                            if (hashMapMarker.get("otherLocMark") != null)
                                removeUpMaker("otherLocMark");
                        removeAllLayers();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            layerE14.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    try{
                        if (hashMapMarker != null)
                            if (hashMapMarker.get("otherLocMark") != null)
                                removeUpMaker("otherLocMark");
                        removeAllLayers();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            layerE15.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    try{
                        if (hashMapMarker != null)
                            if (hashMapMarker.get("otherLocMark") != null)
                                removeUpMaker("otherLocMark");
                        removeAllLayers();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            */


        } catch (Exception e) {
            e.printStackTrace();
        }
        // Do other setup activities here too, as described elsewhere in this tutorial.
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
       // getDeviceLocation();
    }

    @Override
    public void onPolygonClick(Polygon polygon) {
        try {
            removeAllLayers();
            if (hashMapMarker != null)
                if (hashMapMarker.get("otherLocMark") != null)
                    removeUpMaker("otherLocMark");

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("SahajLOG", "Polygon called");
    }


    @Override
    public void onMapClick(LatLng latLng) {
        try {
            int i = 0;

            //Log.e("SahajLOG", " latlang > " + latLng + " " + layer.getBoundingBox().contains(latLng) + " " + layer.getBoundingBox().including(latLng));
            String MarkerString = "location marked";


            i = 0;
            if (layer != null)
                for (LatLngBounds bbc : latLngBoundsArray) {
                    Log.e("SahajLOG", "callad A1" + bbc.contains(latLng));
                    if (bbc.contains(latLng)) {
                        i++;
                        layer.addLayerToMap();
                        MarkerString = "Cyclone ZONE 1";
                    } else if (layer.isLayerOnMap() && i == 0) {
                        layer.removeLayerFromMap();
                    }
                }
            makeLayerWork(layerA, latLng, latLngBoundsArrayA);
            makeLayerWork(layerB, latLng, latLngBoundsArrayB);
            makeLayerWork(layerC, latLng, latLngBoundsArrayC);
            makeLayerWork(layerD, latLng, latLngBoundsArrayD);
            makeLayerWork(layerE, latLng, latLngBoundsArrayE);

            i = 0;
            if (layer2 != null)
                for (LatLngBounds bbc : latLngBoundsArray2) {
                    Log.e("SahajLOG", "callad A2" + bbc.contains(latLng));
                    if (bbc.contains(latLng)) {
                        i++;
                        layer2.addLayerToMap();
                        MarkerString = "Cyclone ZONE 2";
                    } else if (layer2.isLayerOnMap() && i == 0) {
                        layer2.removeLayerFromMap();
                    }
                }

            i = 0;
            if (layer3 != null)
                for (LatLngBounds bbc : latLngBoundsArray3) {
                    Log.e("SahajLOG", "callad A3" + bbc.contains(latLng));
                    if (bbc.contains(latLng)) {
                        i++;
                        layer3.addLayerToMap();
                        MarkerString = "Cyclone ZONE 3";
                    } else if (layer3.isLayerOnMap() && i == 0) {
                        layer3.removeLayerFromMap();
                    }
                }


            i = 0;
            if (layer4 != null)
                for (LatLngBounds bbc : latLngBoundsArray4) {
                    Log.e("SahajLOG", "callad A4" + bbc.contains(latLng));
                    if (bbc.contains(latLng)) {
                        i++;
                        layer4.addLayerToMap();
                        MarkerString = "Cyclone ZONE 4";
                    } else if (layer4.isLayerOnMap() && i == 0) {
                        layer4.removeLayerFromMap();
                    }
                }

                     /*   i = 0;
                        if (layerE1 != null)
                            for (LatLngBounds bbc : latLngBoundsArrayE1) {
                                Log.e("SahajLOG", "callad AE1" + bbc.contains(latLng));
                                if (bbc.contains(latLng)) {
                                    i++;
                                    layerE1.addLayerToMap();
                                    MarkerString = "Cyclone ZONE 4";
                                } else if (layerE1.isLayerOnMap() && i == 0) {
                                    layerE1.removeLayerFromMap();
                                }
                            }

                        i = 0;
                        if (layerE2 != null)
                            for (LatLngBounds bbc : latLngBoundsArrayE2) {
                                Log.e("SahajLOG", "callad A4" + bbc.contains(latLng));
                                if (bbc.contains(latLng)) {
                                    i++;
                                    layerE2.addLayerToMap();
                                    MarkerString = "Cyclone ZONE 4";
                                } else if (layerE2.isLayerOnMap() && i == 0) {
                                    layerE2.removeLayerFromMap();
                                }
                            }

                        i = 0;
                        if (layerE3 != null)
                            for (LatLngBounds bbc : latLngBoundsArrayE3) {
                                Log.e("SahajLOG", "callad A4" + bbc.contains(latLng));
                                if (bbc.contains(latLng)) {
                                    i++;
                                    layerE3.addLayerToMap();
                                    MarkerString = "Cyclone ZONE 4";
                                } else if (layerE3.isLayerOnMap() && i == 0) {
                                    layerE3.removeLayerFromMap();
                                }
                            }

                        i = 0;
                        if (layerE4 != null)
                            for (LatLngBounds bbc : latLngBoundsArrayE4) {
                                Log.e("SahajLOG", "callad A4" + bbc.contains(latLng));
                                if (bbc.contains(latLng)) {
                                    i++;
                                    layerE4.addLayerToMap();
                                    MarkerString = "Cyclone ZONE 4";
                                } else if (layerE4.isLayerOnMap() && i == 0) {
                                    layerE4.removeLayerFromMap();
                                }
                            }

                        i = 0;
                        if (layerE5 != null)
                            for (LatLngBounds bbc : latLngBoundsArrayE5) {
                                Log.e("SahajLOG", "callad A4" + bbc.contains(latLng));
                                if (bbc.contains(latLng)) {
                                    i++;
                                    layerE5.addLayerToMap();
                                    MarkerString = "Cyclone ZONE 4";
                                } else if (layerE5.isLayerOnMap() && i == 0) {
                                    layerE5.removeLayerFromMap();
                                }
                            }

                        i = 0;
                        if (layerE6 != null)
                            for (LatLngBounds bbc : latLngBoundsArrayE6) {
                                Log.e("SahajLOG", "callad A4" + bbc.contains(latLng));
                                if (bbc.contains(latLng)) {
                                    i++;
                                    layerE6.addLayerToMap();
                                    MarkerString = "Cyclone ZONE 4";
                                } else if (layerE6.isLayerOnMap() && i == 0) {
                                    layerE6.removeLayerFromMap();
                                }
                            }

                        i = 0;
                        if (layerE7 != null)
                            for (LatLngBounds bbc : latLngBoundsArrayE7) {
                                Log.e("SahajLOG", "callad A4" + bbc.contains(latLng));
                                if (bbc.contains(latLng)) {
                                    i++;
                                    layerE7.addLayerToMap();
                                    MarkerString = "Cyclone ZONE 4";
                                } else if (layerE7.isLayerOnMap() && i == 0) {
                                    layerE7.removeLayerFromMap();
                                }
                            }

                        i = 0;
                        if (layerE8 != null)
                            for (LatLngBounds bbc : latLngBoundsArrayE8) {
                                Log.e("SahajLOG", "callad A4" + bbc.contains(latLng));
                                if (bbc.contains(latLng)) {
                                    i++;
                                    layerE8.addLayerToMap();
                                    MarkerString = "Cyclone ZONE 4";
                                } else if (layerE8.isLayerOnMap() && i == 0) {
                                    layerE8.removeLayerFromMap();
                                }
                            }

                        i = 0;
                        if (layerE9 != null)
                            for (LatLngBounds bbc : latLngBoundsArrayE9) {
                                Log.e("SahajLOG", "callad A4" + bbc.contains(latLng));
                                if (bbc.contains(latLng)) {
                                    i++;
                                    layerE9.addLayerToMap();
                                    MarkerString = "Cyclone ZONE 4";
                                } else if (layerE9.isLayerOnMap() && i == 0) {
                                    layerE9.removeLayerFromMap();
                                }
                            }

                        i = 0;
                        if (layerE10 != null)
                            for (LatLngBounds bbc : latLngBoundsArrayE10) {
                                Log.e("SahajLOG", "callad A4" + bbc.contains(latLng));
                                if (bbc.contains(latLng)) {
                                    i++;
                                    layerE10.addLayerToMap();
                                    MarkerString = "Cyclone ZONE 4";
                                } else if (layerE10.isLayerOnMap() && i == 0) {
                                    layerE10.removeLayerFromMap();
                                }
                            }

                        i = 0;
                        if (layerE11 != null)
                            for (LatLngBounds bbc : latLngBoundsArrayE11) {
                                Log.e("SahajLOG", "callad A4" + bbc.contains(latLng));
                                if (bbc.contains(latLng)) {
                                    i++;
                                    layerE11.addLayerToMap();
                                    MarkerString = "Cyclone ZONE 4";
                                } else if (layerE11.isLayerOnMap() && i == 0) {
                                    layerE11.removeLayerFromMap();
                                }
                            }

                        i = 0;
                        if (layerE12 != null)
                            for (LatLngBounds bbc : latLngBoundsArrayE12) {
                                Log.e("SahajLOG", "callad A4" + bbc.contains(latLng));
                                if (bbc.contains(latLng)) {
                                    i++;
                                    layerE12.addLayerToMap();
                                    MarkerString = "Cyclone ZONE 4";
                                } else if (layerE12.isLayerOnMap() && i == 0) {
                                    layerE12.removeLayerFromMap();
                                }
                            }

                        i = 0;
                        if (layerE13 != null)
                            for (LatLngBounds bbc : latLngBoundsArrayE13) {
                                Log.e("SahajLOG", "callad A4" + bbc.contains(latLng));
                                if (bbc.contains(latLng)) {
                                    i++;
                                    layerE13.addLayerToMap();
                                    MarkerString = "Cyclone ZONE 4";
                                } else if (layerE13.isLayerOnMap() && i == 0) {
                                    layerE13.removeLayerFromMap();
                                }
                            }

                        i = 0;
                        if (layerE14 != null)
                            for (LatLngBounds bbc : latLngBoundsArrayE14) {
                                Log.e("SahajLOG", "callad A4" + bbc.contains(latLng));
                                if (bbc.contains(latLng)) {
                                    i++;
                                    layerE14.addLayerToMap();
                                    MarkerString = "Cyclone ZONE 4";
                                } else if (layerE14.isLayerOnMap() && i == 0) {
                                    layerE14.removeLayerFromMap();
                                }
                            }

                        i = 0;
                        if (layerE15 != null)
                            for (LatLngBounds bbc : latLngBoundsArrayE15) {
                                Log.e("SahajLOG", "callad A4" + bbc.contains(latLng));
                                if (bbc.contains(latLng)) {
                                    i++;
                                    layerE15.addLayerToMap();
                                    MarkerString = "Cyclone ZONE 4";
                                } else if (layerE15.isLayerOnMap() && i == 0) {
                                    layerE15.removeLayerFromMap();
                                }
                            }
                            */

            if (hashMapMarker != null) {
                if (hashMapMarker.get("otherLocMark") != null) {
                    removeUpMaker("otherLocMark");
                    setUpMaker(latLng.latitude, latLng.longitude, "otherLocMark", MarkerString);
                } else
                    setUpMaker(latLng.latitude, latLng.longitude, "otherLocMark", MarkerString);
            } else
                setUpMaker(latLng.latitude, latLng.longitude, "otherLocMark", MarkerString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Boolean makeLayerWork(GeoJsonLayer layer,LatLng latLng,List<LatLngBounds> latLngBoundsArray){
        int i = 0;
        Boolean markerCalled=false;
        if (layer != null)
            for (LatLngBounds bbc : latLngBoundsArray) {
                Log.e("SahajLOG", "callad A1" + bbc.contains(latLng));
                if (bbc.contains(latLng)) {
                    i++;
                    layer.addLayerToMap();
                    markerCalled = true;
                } else if (layer.isLayerOnMap() && i == 0) {
                    layer.removeLayerFromMap();
                }
            }
        return markerCalled;
    }

    private void removeAllLayers(){
        if (layer!=null)
            if (layer.isLayerOnMap())
                layer.removeLayerFromMap();
        if (layerA!=null)
            if (layerA.isLayerOnMap())
                layerA.removeLayerFromMap();
        if (layerB!=null)
            if (layerB.isLayerOnMap())
                layerB.removeLayerFromMap();
        if (layerC!=null)
            if (layerC.isLayerOnMap())
                layerC.removeLayerFromMap();
        if (layerD!=null)
            if (layerD.isLayerOnMap())
                layerD.removeLayerFromMap();
        if (layerE!=null)
            if (layerE.isLayerOnMap())
                layerE.removeLayerFromMap();
        if (layer2!=null)
            if (layer2.isLayerOnMap())
                layer2.removeLayerFromMap();
        if (layer3!=null)
            if (layer3.isLayerOnMap())
                layer3.removeLayerFromMap();
        if (layer4!=null)
            if (layer4.isLayerOnMap())
                layer4.removeLayerFromMap();
        if (layerE1!=null)
            if (layerE1.isLayerOnMap())
                layerE1.removeLayerFromMap();
        if (layerE2!=null)
            if (layerE2.isLayerOnMap())
                layerE2.removeLayerFromMap();
        if (layerE3!=null)
            if (layerE3.isLayerOnMap())
                layerE3.removeLayerFromMap();
        if (layerE5!=null)
            if (layerE5.isLayerOnMap())
                layerE5.removeLayerFromMap();
        if (layerE6!=null)
            if (layerE6.isLayerOnMap())
                layerE6.removeLayerFromMap();
        if (layerE7!=null)
            if (layerE7.isLayerOnMap())
                layerE7.removeLayerFromMap();
        if (layerE8!=null)
            if (layerE8.isLayerOnMap())
                layerE8.removeLayerFromMap();
        if (layerE9!=null)
            if (layerE9.isLayerOnMap())
                layerE9.removeLayerFromMap();
        if (layerE10!=null)
            if (layerE10.isLayerOnMap())
                layerE10.removeLayerFromMap();
        if (layerE11!=null)
            if (layerE11.isLayerOnMap())
                layerE11.removeLayerFromMap();
        if (layerE12!=null)
            if (layerE12.isLayerOnMap())
                layerE12.removeLayerFromMap();
        if (layerE13!=null)
            if (layerE13.isLayerOnMap())
                layerE13.removeLayerFromMap();
        if (layerE14!=null)
            if (layerE14.isLayerOnMap())
                layerE14.removeLayerFromMap();
        if (layerE15!=null)
            if (layerE15.isLayerOnMap())
                layerE15.removeLayerFromMap();
    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
           Log.e("SahajLOG", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();

            getDeviceLocation();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onLocationChanged(Location location) {
        Log.e("SahajLOG", "LOctionChanged "+location);
        mLastKnownLocation=location;
    }

    private List<LatLngBounds> getLatLngBoundingBox(GeoJsonLayer layer) {
        int count=0;
        List<LatLng> coordinates=null;
        List<List<LatLng>> coordinatesArray = new ArrayList<>();
        for (GeoJsonFeature feature : layer.getFeatures()) {
            coordinates = new ArrayList<>();
            if (feature.hasGeometry()) {

                Geometry geometry = feature.getGeometry();
                Log.e("SahajLOG", "geometry > "+count);
               // Log.e("SahajLOG", "geometry > "+geometry);
                Log.e("SahajLOG", "geom> "+" "+geometry.getGeometryObject());
                if (geometry.toString().equals("GeometryCollection")) {
                    List<Geometry> geometries =
                            ((GeoJsonGeometryCollection)geometry).getGeometries();

                    for (Geometry geom : geometries) {
                        coordinates.addAll(getCoordinatesFromGeometry(geom));
                    }
                }
                else {
                    coordinates.clear();
                    coordinates.addAll(getCoordinatesFromGeometry(geometry));
                }
                coordinatesArray.add(count,coordinates);
                //Log.e("SahajLOG", "CoordinatesArray AfterAdd > " + coordinatesArray.size() + coordinatesArray);
                count++;
            }
        }
        Log.e("SahajLOG", "CoordinatesArray > "+coordinatesArray.size()+coordinatesArray);
        // Get the bounding box builder.

        List<LatLngBounds.Builder> builderArray = new ArrayList<>();

        for (int i=0;i<count;i++) {
            LatLngBounds.Builder builder= LatLngBounds.builder();

            for (LatLng latLng : coordinatesArray.get(i)) {
                builder.include(latLng);
            }
            builderArray.add(builder);
        }


        // Feed the coordinates to the builder.
        List<LatLngBounds> boundingBoxFromBuilderArray=new ArrayList<>();

        for (int i=0;i<count;i++) {
            LatLngBounds boundingBoxFromBuilder = builderArray.get(i).build();
            boundingBoxFromBuilderArray.add(boundingBoxFromBuilder);
        }

        //return boundingBox;
        return boundingBoxFromBuilderArray;
    }
    private List<LatLng> getCoordinatesFromGeometry(Geometry geometry) {
       // Log.e("SahajLOG", "geometry2 > "+geometry.getGeometryType());

        List<LatLng> coordinates = new ArrayList<>();

        // GeoJSON geometry types:
        // http://geojson.org/geojson-spec.html#geometry-objects

        switch (geometry.getGeometryType()) {
            case "Point":
                coordinates.add(((GeoJsonPoint) geometry).getCoordinates());
                break;
            case "MultiPoint":
                List<GeoJsonPoint> points = ((GeoJsonMultiPoint) geometry).getPoints();
                for (GeoJsonPoint point : points) {
                    coordinates.add(point.getCoordinates());
                }
                break;
            case "LineString":
                coordinates.addAll(((GeoJsonLineString) geometry).getCoordinates());
                break;
            case "MultiLineString":
                List<GeoJsonLineString> lines =
                        ((GeoJsonMultiLineString) geometry).getLineStrings();
                for (GeoJsonLineString line : lines) {
                    coordinates.addAll(line.getCoordinates());
                }
                break;
            case "Polygon":
                List<? extends List<LatLng>> lists =
                        ((GeoJsonPolygon) geometry).getCoordinates();
                for (List<LatLng> list : lists) {
                    coordinates.addAll(list);
                }
                break;
            case "MultiPolygon":
                List<GeoJsonPolygon> polygons =
                        ((GeoJsonMultiPolygon) geometry).getPolygons();
                for (GeoJsonPolygon polygon : polygons) {
                    for (List<LatLng> list : polygon.getCoordinates()) {
                        coordinates.addAll(list);
                    }
                }
                break;
        }

        return coordinates;
    }

    private LatLng getLatLng(GeoJsonLayer layer) {

        List<LatLng> coordinates = new ArrayList<>();

        for (GeoJsonFeature feature : layer.getFeatures()) {

            if (feature.hasGeometry()) {
                Geometry geometry = feature.getGeometry();
                //Log.e("SahajLOG", "geometry > "+geometry+" "+geometry.toString()+ " "+geometry.getGeometryType()+" "+geometry.getGeometryObject());
                if (geometry.toString().equals("GeometryCollection")) {
                    List<Geometry> geometries =
                            ((GeoJsonGeometryCollection)geometry).getGeometries();

                    for (Geometry geom : geometries) {
                        coordinates.addAll(getCoordinatesFromGeometry(geom));
                    }
                }
                else {
                    coordinates.addAll(getCoordinatesFromGeometry(geometry));
                }
            }
        }

        // Get the bounding box builder.
        //LatLngBounds.Builder builder = LatLngBounds.builder();
        LatLng latLng1= coordinates.get(0);
        // Feed the coordinates to the builder.
        for (int i=1;i<coordinates.size();i++) {
          //  builder.include(latLng);
            LatLng latlngA=new LatLng((latLng1.latitude+coordinates.get(i).latitude),(latLng1.longitude+coordinates.get(i).longitude));
            LatLng latlngHalf=new LatLng((latlngA.latitude/2.0),(latlngA.longitude/2.0));
            latLng1=latlngHalf;
        }
        return latLng1;
    }



}
