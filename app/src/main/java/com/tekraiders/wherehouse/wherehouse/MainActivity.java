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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
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
        LocationListener {
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 2;
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

    private HashMap<String,Marker> hashMapMarker;

    private SlidingTabsLayout mTabs;
    private LocationRequest mLocationRequest;
    private Location location;
    private SupportMapFragment mapFragment;


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

    private void setUpMaker(double currentLatitude,double currentLongitude,String Key,String title) {
        hashMapMarker = new HashMap<>();
        Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title(title));
        hashMapMarker.put(Key, marker);
        marker.showInfoWindow();
      //  Log.e("SahajLOG", "setUpMarker > " +marker +" key "+Key);
        //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("I am here!"));
    }
    private void removeUpMaker(String Key) {
        Marker marker = hashMapMarker.get(Key);
        marker.remove();
        hashMapMarker.remove(Key);
        Log.e("SahajLOG", "removeMarker > " + marker + " key " + Key);
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
      //  googleMap.setOnPolygonClickListener(this);
        try {
            final GeoJsonLayer layer = new GeoJsonLayer(mMap, R.raw.map_2,
                    getApplicationContext());
            final GeoJsonLayer layer2 = new GeoJsonLayer(mMap, R.raw.map_3,
                    getApplicationContext());

           // layer.addLayerToMap();
            GeoJsonPolygonStyle polygonStyle=layer.getDefaultPolygonStyle();
            polygonStyle.setFillColor(0x32e74c3c);
            polygonStyle.setStrokeWidth(1.5f);

           // layer2.addLayerToMap();
            GeoJsonPolygonStyle polygonStyle2=layer2.getDefaultPolygonStyle();
            polygonStyle2.setFillColor(0x32e74c3c);
            polygonStyle2.setStrokeWidth(1.5f);

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {

                    //Log.e("SahajLOG", " latlang > " + latLng + " " + layer.getBoundingBox().contains(latLng) + " " + layer.getBoundingBox().including(latLng));
                    String MarkerString = "location marked";

                    LatLngBounds bbc2 = getLatLngBoundingBox(layer2);
                    if (bbc2.contains(latLng)) {
                        layer2.addLayerToMap();
                        MarkerString = "Earthquake ZONE 1";
                    } else if (layer2.isLayerOnMap()) {
                        layer2.removeLayerFromMap();
                    }
                    LatLngBounds bbc = getLatLngBoundingBox(layer);
                    if (bbc.contains(latLng)) {
                        layer.addLayerToMap();
                        MarkerString = "Earthquake ZONE 4";
                    } else if (layer.isLayerOnMap()) {
                        layer.removeLayerFromMap();
                    }

                    if (hashMapMarker != null) {
                        if (hashMapMarker.get("otherLocMark") != null) {
                            removeUpMaker("otherLocMark");
                            setUpMaker(latLng.latitude, latLng.longitude, "otherLocMark", MarkerString);
                        } else
                            setUpMaker(latLng.latitude, latLng.longitude, "otherLocMark", MarkerString);
                    } else
                        setUpMaker(latLng.latitude, latLng.longitude, "otherLocMark", MarkerString);


                }
            });
            layer.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    if (hashMapMarker != null)
                        if (hashMapMarker.get("otherLocMark") != null)
                            removeUpMaker("otherLocMark");
                    layer.removeLayerFromMap();
                    Log.e("SahajLOG", "layer1 called ");
                    if (layer2.isLayerOnMap())
                        layer2.removeLayerFromMap();
                }
            });
            layer2.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    if (hashMapMarker != null)
                        if (hashMapMarker.get("otherLocMark") != null)
                            removeUpMaker("otherLocMark");
                    layer2.removeLayerFromMap();
                    /*LatLng latLng=getLatLng(layer2);
                    String MarkerString="Earthquake ZONE 1";
                    if (hashMapMarker != null) {
                        if (hashMapMarker.get("otherLocMark") != null) {
                            removeUpMaker("otherLocMark");
                            setUpMaker(latLng.latitude, latLng.longitude, "otherLocMark", MarkerString);
                        } else
                            setUpMaker(latLng.latitude, latLng.longitude, "otherLocMark", MarkerString);
                    } else
                        setUpMaker(latLng.latitude, latLng.longitude, "otherLocMark", MarkerString);
                        */
                    Log.e("SahajLOG", "layer2 called ");
                    if (layer.isLayerOnMap())
                        layer.removeLayerFromMap();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Do other setup activities here too, as described elsewhere in this tutorial.
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
       // getDeviceLocation();
    }


   /* @Override
    public void onPolygonClick(Polygon polygon) {
        if (hashMapMarker != null)
            if (hashMapMarker.get("otherLocMark") != null)
                removeUpMaker("otherLocMark");
        Log.e("SahajLOG", "Polygon click " + polygon);
        polygon.setFillColor(0x32e74c3c);
        polygon.setStrokeWidth(1.5f);
        //Log.e("SahajLOG", "Polygon ID "+polygon.getId()+" "+polygon.getZIndex()+polygon.);
    }*/

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
           Log.e("SahajLOG",  "Location services connection failed with code " + connectionResult.getErrorCode());
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

    private LatLngBounds getLatLngBoundingBox(GeoJsonLayer layer) {
        int count=0;
        List<LatLng> coordinates = new ArrayList<>();
        List<List<LatLng>> coordinatesArray = new ArrayList<>();
        for (GeoJsonFeature feature : layer.getFeatures()) {

            if (feature.hasGeometry()) {
                Geometry geometry = feature.getGeometry();
                Log.e("SahajLOG", "geometry > "+geometry);
                Log.e("SahajLOG", "geom> "+" "+geometry.getGeometryObject());
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
                coordinatesArray.add(count,coordinates);
                count++;
            }
        }

        // Get the bounding box builder.
        LatLngBounds.Builder builder = LatLngBounds.builder();

        // Feed the coordinates to the builder.
        for (LatLng latLng : coordinates) {
            builder.include(latLng);
        }

        LatLngBounds boundingBoxFromBuilder = builder.build();

        //return boundingBox;
        return boundingBoxFromBuilder;
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
