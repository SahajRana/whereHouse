package com.tekraiders.wherehouse.wherehouse;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.drawable.shapes.Shape;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
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
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.helpers.ClickListenerHelper;
import com.tekraiders.wherehouse.wherehouse.drawer.NavigationDrawerFragment;
import com.tekraiders.wherehouse.wherehouse.tabs.MainUpRecyclerItemAdapter;
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
    private static final int MARKER_SET_ZOOM = 6;
    private boolean isMyLocCalled=false;
    private static final int MARKER_REMOVE_ZOOM = 6;
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
    private GeoJsonLayer layer,layerA,layerB,layerC,layerD,layerE,layer2,layer3,layer4,layerE1,layerE2,layerE3,layerE4,layerE5,layerE6,layerE7,layerE8,layerE9,layerE10,layerE11,layerE12,layerE13,layerE14,layerE15,layerE16,layerE17,layerE18,layerE19,layerE20,layerE21,layerE22,layerE23,layerE24,layerE25,layerE26,layerE27,layerE28,layerF1,layerF2,layerF3,layerF4,layerF5,layerF6,layerF7,layerF8,layerF9,layerF10,layerF11,layerF12,layerF13,layerF14,layerF15,layerF16,layerF17,layerF18,layerF19,layerF20,layerF21,layerF22,layerF23,layerF24,layerF25,layerF26,layerF27,layerF28,layerL1,layerL2,layerL3,layerL4,layerL5,layerL6,layerL7,layerL8,layerL9,layerL10,layerL11,layerL12,layerL13,layerL14,layerL15,layerL16,layerL17,layerL18,layerL19,layerL20;
    private ArrayList<LatLngBounds> latLngBoundsArray,latLngBoundsArrayA,latLngBoundsArrayB,latLngBoundsArrayC,latLngBoundsArrayD,latLngBoundsArrayE,latLngBoundsArray2,latLngBoundsArray3,latLngBoundsArray4,latLngBoundsArrayE1,latLngBoundsArrayE2,latLngBoundsArrayE3,latLngBoundsArrayE4,latLngBoundsArrayE5,latLngBoundsArrayE6,latLngBoundsArrayE7,latLngBoundsArrayE8,latLngBoundsArrayE9,latLngBoundsArrayE10,latLngBoundsArrayE11,latLngBoundsArrayE12,latLngBoundsArrayE13,latLngBoundsArrayE14,latLngBoundsArrayE15,latLngBoundsArrayE16,latLngBoundsArrayE17,latLngBoundsArrayE18,latLngBoundsArrayE19,latLngBoundsArrayE20,latLngBoundsArrayE21,latLngBoundsArrayE22,latLngBoundsArrayE23,latLngBoundsArrayE24,latLngBoundsArrayE25,latLngBoundsArrayE26,latLngBoundsArrayE27,latLngBoundsArrayE28,latLngBoundsArrayF1,latLngBoundsArrayF2,latLngBoundsArrayF3,latLngBoundsArrayF4,latLngBoundsArrayF5,latLngBoundsArrayF6,latLngBoundsArrayF7,latLngBoundsArrayF8,latLngBoundsArrayF9,latLngBoundsArrayF10,latLngBoundsArrayF11,latLngBoundsArrayF12,latLngBoundsArrayF13,latLngBoundsArrayF14,latLngBoundsArrayF15,latLngBoundsArrayF16,latLngBoundsArrayF17,latLngBoundsArrayF18,latLngBoundsArrayF19,latLngBoundsArrayF20,latLngBoundsArrayF21,latLngBoundsArrayF22,latLngBoundsArrayF23,latLngBoundsArrayF24,latLngBoundsArrayF25,latLngBoundsArrayF26,latLngBoundsArrayF27,latLngBoundsArrayF28,latLngBoundsArrayL1,latLngBoundsArrayL2,latLngBoundsArrayL3,latLngBoundsArrayL4,latLngBoundsArrayL5,latLngBoundsArrayL6,latLngBoundsArrayL7,latLngBoundsArrayL8,latLngBoundsArrayL9,latLngBoundsArrayL10,latLngBoundsArrayL11,latLngBoundsArrayL12,latLngBoundsArrayL13,latLngBoundsArrayL14,latLngBoundsArrayL15,latLngBoundsArrayL16,latLngBoundsArrayL17,latLngBoundsArrayL18,latLngBoundsArrayL19,latLngBoundsArrayL20;

    private RecyclerView mRecyclerView;
    private FastItemAdapter<MainUpRecyclerItemAdapter> mFastItemAdapter;
    private ClickListenerHelper<MainUpRecyclerItemAdapter> mClickListenerHelper;
    private LinearLayoutManager mLayoutManager;
    private LinearLayout mLinearLayoutUp;
    private ImageButton mImageButtonContact;

    //initial zoom
    static final int initZoom = 8;
    //steps the zoom
    int stepZoom = 0;
    // number of steps in zoom, be careful with this number!
    int stepZoomMax = 1;
    //number of .zoom steps in a step
    int stepZoomDetent =1;// (18 - initZoom) / stepZoomMax;
    //when topause zoom for spin
    int stepToSpin = 1;
    //steps the spin
    int stepSpin = 0;
    //number of steps in spin (factor of 360)
    int stepSpinMax = 1;
    //number of degrees in stepSpin
    int stepSpinDetent = 360 / stepSpinMax;

    Intent detailIntent;
    Intent intent;
    Marker marker;
    final int mapHopDelay = 2000;
    private LatLng latLngX=null;


    private SharedPreferences prefs;
    private FloatingActionButton mFabBack;
    private com.getbase.floatingactionbutton.FloatingActionButton mFabEq,mFabCy,mFabFl,mFabLs,mFabAL;
    private FloatingActionsMenu mFabMulti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Toolbar mToolbar=(Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        setTitle("");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mFabBack=(FloatingActionButton)findViewById(R.id.main_fab_back);
        mFabMulti=(FloatingActionsMenu)findViewById(R.id.main_fab_multi);
        mImageButtonContact=(ImageButton)findViewById(R.id.main_contact_button);
        mFabBack.setVisibility(View.GONE);
        mFabMulti.setVisibility(View.VISIBLE);
        mFabCy=(com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.action_a);
        mFabEq=(com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.action_b);
        mFabFl=(com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.action_c);
        mFabLs=(com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.action_d);
        mFabAL=(com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.action_e);



        NavigationDrawerFragment drawerFragment=(NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_frag);
        drawerFragment.setUp(R.id.nav_frag, (DrawerLayout) findViewById(R.id.drawerLayout), mToolbar);

        mLinearLayoutUp=(LinearLayout)findViewById(R.id.main_layout_up);
        mFastItemAdapter = new FastItemAdapter<>();
        mFastItemAdapter.withSavedInstanceState(savedInstanceState);
        mRecyclerView=(RecyclerView)findViewById(R.id.main_recycler_up);
        mClickListenerHelper = new ClickListenerHelper<>(mFastItemAdapter);
        mFastItemAdapter.withOnClickListener(new FastAdapter.OnClickListener<MainUpRecyclerItemAdapter>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public boolean onClick(View v, IAdapter<MainUpRecyclerItemAdapter> adapter,MainUpRecyclerItemAdapter item, int position) {
               /* Intent myIntent = new Intent(getActivity(), ProfileNewActivity.class);
                myIntent.putExtra("EmailPrefGeneral", item.getEmailPrefsGeneral());
                startActivity(myIntent);*/
                return false;
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // if (mLayoutManager==null){
        Log.e("SahajLOG11", "mLayautNull Called");
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mFastItemAdapter);
        mLinearLayoutUp.setVisibility(View.GONE);

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
        mFabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hashMapMarker != null)
                    if (hashMapMarker.get("otherLocMark") != null)
                        removeUpMaker("otherLocMark");
                removeAllLayers();
                if (mLinearLayoutUp.getVisibility()==View.VISIBLE){
                 //   mLinearLayoutUp.setAlpha(1.0f);
                    Log.e("SahajLOG", "height***********2 "+mLinearLayoutUp.getHeight());
                    mLinearLayoutUp.setTranslationY(0);
                    mLinearLayoutUp.animate()
                            .setDuration(400)
                            .translationY(mLinearLayoutUp.getHeight())
                          //  .alpha(0.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    mLinearLayoutUp.setVisibility(View.GONE);
                                    mFabBack.setVisibility(View.GONE);
                                    mFabMulti.setVisibility(View.VISIBLE);
                                }
                            });
                }

            }
        });
        mFabCy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hashMapMarker != null)
                    if (hashMapMarker.get("otherLocMark") != null)
                        removeUpMaker("otherLocMark");
                removeAllLayers();
                layer.addLayerToMap();
                layerA.addLayerToMap();
                layerB.addLayerToMap();
                layerC.addLayerToMap();
                layerD.addLayerToMap();
                layerE.addLayerToMap();
                layer2.addLayerToMap();
                layer3.addLayerToMap();
                layer4.addLayerToMap();
            }
        });
        mFabEq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hashMapMarker != null)
                    if (hashMapMarker.get("otherLocMark") != null)
                        removeUpMaker("otherLocMark");
                removeAllLayers();
                layerE1.addLayerToMap();
                layerE2.addLayerToMap();
                layerE3.addLayerToMap();
                layerE4.addLayerToMap();
                layerE5.addLayerToMap();
                layerE6.addLayerToMap();
                layerE7.addLayerToMap();
                layerE8.addLayerToMap();
                layerE9.addLayerToMap();
                layerE10.addLayerToMap();
                layerE11.addLayerToMap();
                layerE12.addLayerToMap();
                layerE13.addLayerToMap();
                layerE14.addLayerToMap();
                layerE15.addLayerToMap();
                layerE16.addLayerToMap();
                layerE17.addLayerToMap();
                layerE18.addLayerToMap();
                layerE19.addLayerToMap();
                layerE20.addLayerToMap();
                layerE21.addLayerToMap();
                layerE22.addLayerToMap();
                layerE23.addLayerToMap();
                layerE24.addLayerToMap();
                layerE25.addLayerToMap();
                layerE26.addLayerToMap();
                layerE27.addLayerToMap();
                layerE28.addLayerToMap();
            }
        });
        mFabFl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hashMapMarker != null)
                    if (hashMapMarker.get("otherLocMark") != null)
                        removeUpMaker("otherLocMark");
                removeAllLayers();
                layerF1.addLayerToMap();
                layerF2.addLayerToMap();
                layerF3.addLayerToMap();
                layerF4.addLayerToMap();
                layerF5.addLayerToMap();
                layerF6.addLayerToMap();
                layerF7.addLayerToMap();
                layerF8.addLayerToMap();
                layerF9.addLayerToMap();
                layerF10.addLayerToMap();
                layerF11.addLayerToMap();
                layerF12.addLayerToMap();
                layerF13.addLayerToMap();
                layerF14.addLayerToMap();
                layerF15.addLayerToMap();
                layerF16.addLayerToMap();
                layerF17.addLayerToMap();
                layerF18.addLayerToMap();
                layerF19.addLayerToMap();
                layerF20.addLayerToMap();
                layerF21.addLayerToMap();
                layerF22.addLayerToMap();
                layerF23.addLayerToMap();
                layerF24.addLayerToMap();
                layerF25.addLayerToMap();
                layerF26.addLayerToMap();
                layerF27.addLayerToMap();
                layerF28.addLayerToMap();


            }
        });
        mFabLs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hashMapMarker != null)
                    if (hashMapMarker.get("otherLocMark") != null)
                        removeUpMaker("otherLocMark");
                removeAllLayers();
                layerL1.addLayerToMap();
                layerL2.addLayerToMap();
                layerL3.addLayerToMap();
                layerL4.addLayerToMap();
                layerL5.addLayerToMap();
                layerL6.addLayerToMap();
                layerL7.addLayerToMap();
                layerL8.addLayerToMap();
                layerL9.addLayerToMap();
                layerL10.addLayerToMap();
                layerL11.addLayerToMap();
                layerL12.addLayerToMap();
                layerL13.addLayerToMap();
                layerL14.addLayerToMap();
                layerL15.addLayerToMap();
                layerL16.addLayerToMap();
                layerL17.addLayerToMap();
                layerL18.addLayerToMap();
                layerL19.addLayerToMap();
                layerL20.addLayerToMap();
            }
        });
        mFabAL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hashMapMarker != null)
                    if (hashMapMarker.get("otherLocMark") != null)
                        removeUpMaker("otherLocMark");
                removeAllLayers();
                layer.addLayerToMap();
                layerA.addLayerToMap();
                layerB.addLayerToMap();
                layerC.addLayerToMap();
                layerD.addLayerToMap();
                layerE.addLayerToMap();
                layer2.addLayerToMap();
                layer3.addLayerToMap();
                layer4.addLayerToMap();

                layerE1.addLayerToMap();
                layerE2.addLayerToMap();
                layerE3.addLayerToMap();
                layerE4.addLayerToMap();
                layerE5.addLayerToMap();
                layerE6.addLayerToMap();
                layerE7.addLayerToMap();
                layerE8.addLayerToMap();
                layerE9.addLayerToMap();
                layerE10.addLayerToMap();
                layerE11.addLayerToMap();
                layerE12.addLayerToMap();
                layerE13.addLayerToMap();
                layerE14.addLayerToMap();
                layerE15.addLayerToMap();
                layerE16.addLayerToMap();
                layerE17.addLayerToMap();
                layerE18.addLayerToMap();
                layerE19.addLayerToMap();
                layerE20.addLayerToMap();
                layerE21.addLayerToMap();
                layerE22.addLayerToMap();
                layerE23.addLayerToMap();
                layerE24.addLayerToMap();
                layerE25.addLayerToMap();
                layerE26.addLayerToMap();
                layerE27.addLayerToMap();
                layerE28.addLayerToMap();

                layerF1.addLayerToMap();
                layerF2.addLayerToMap();
                layerF3.addLayerToMap();
                layerF4.addLayerToMap();
                layerF5.addLayerToMap();
                layerF6.addLayerToMap();
                layerF7.addLayerToMap();
                layerF8.addLayerToMap();
                layerF9.addLayerToMap();
                layerF10.addLayerToMap();
                layerF11.addLayerToMap();
                layerF12.addLayerToMap();
                layerF13.addLayerToMap();
                layerF14.addLayerToMap();
                layerF15.addLayerToMap();
                layerF16.addLayerToMap();
                layerF17.addLayerToMap();
                layerF18.addLayerToMap();
                layerF19.addLayerToMap();
                layerF20.addLayerToMap();
                layerF21.addLayerToMap();
                layerF22.addLayerToMap();
                layerF23.addLayerToMap();
                layerF24.addLayerToMap();
                layerF25.addLayerToMap();
                layerF26.addLayerToMap();
                layerF27.addLayerToMap();
                layerF28.addLayerToMap();

                layerL1.addLayerToMap();
                layerL2.addLayerToMap();
                layerL3.addLayerToMap();
                layerL4.addLayerToMap();
                layerL5.addLayerToMap();
                layerL6.addLayerToMap();
                layerL7.addLayerToMap();
                layerL8.addLayerToMap();
                layerL9.addLayerToMap();
                layerL10.addLayerToMap();
                layerL11.addLayerToMap();
                layerL12.addLayerToMap();
                layerL13.addLayerToMap();
                layerL14.addLayerToMap();
                layerL15.addLayerToMap();
                layerL16.addLayerToMap();
                layerL17.addLayerToMap();
                layerL18.addLayerToMap();
                layerL19.addLayerToMap();
                layerL20.addLayerToMap();
            }
        });
        mImageButtonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ContactActivity.class);
                startActivity(intent);
                enterFromBottomAnimation();
            }
        });

    }
    protected void enterFromBottomAnimation(){
        overridePendingTransition(R.anim.activity_open_translate_from_bottom, R.anim.activity_no_animation);
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
                    String MarkerString = "location marked";
                    mFastItemAdapter.clear();
                    prefs.edit().putBoolean("CycloneCalled",false).commit();
                    prefs.edit().putBoolean("EarthquakeCalled",false).commit();
                    prefs.edit().putBoolean("FloodCalled",false).commit();
                    prefs.edit().putBoolean("LandslideCalled",false).commit();

                    makeLayerWork(layer , latLng, latLngBoundsArray,"Cyclone");
                    makeLayerWork(layerA, latLng, latLngBoundsArrayA,"Cyclone");
                    makeLayerWork(layerB, latLng, latLngBoundsArrayB,"Cyclone");
                    makeLayerWork(layerC, latLng, latLngBoundsArrayC,"Cyclone");
                    makeLayerWork(layerD, latLng, latLngBoundsArrayD,"Cyclone");
                    makeLayerWork(layerE, latLng, latLngBoundsArrayE,"Cyclone");
                    makeLayerWork(layer2, latLng, latLngBoundsArray2,"Cyclone");
                    makeLayerWork(layer3, latLng, latLngBoundsArray3,"Cyclone");
                    makeLayerWork(layer4, latLng, latLngBoundsArray4,"Cyclone");

                    makeLayerWork(layerE1 , latLng, latLngBoundsArrayE1,"Earthquake");
                    makeLayerWork(layerE2 , latLng, latLngBoundsArrayE2,"Earthquake");
                    makeLayerWork(layerE3 , latLng, latLngBoundsArrayE3,"Earthquake");
                    makeLayerWork(layerE4 , latLng, latLngBoundsArrayE4,"Earthquake");
                    makeLayerWork(layerE5 , latLng, latLngBoundsArrayE5,"Earthquake");
                    makeLayerWork(layerE6 , latLng, latLngBoundsArrayE6,"Earthquake");
                    makeLayerWork(layerE7 , latLng, latLngBoundsArrayE7,"Earthquake");
                    makeLayerWork(layerE8 , latLng, latLngBoundsArrayE8,"Earthquake");
                    makeLayerWork(layerE9 , latLng, latLngBoundsArrayE9,"Earthquake");
                    makeLayerWork(layerE10 , latLng, latLngBoundsArrayE10,"Earthquake");
                    makeLayerWork(layerE11 , latLng, latLngBoundsArrayE11,"Earthquake");
                    makeLayerWork(layerE12 , latLng, latLngBoundsArrayE12,"Earthquake");
                    makeLayerWork(layerE13 , latLng, latLngBoundsArrayE13,"Earthquake");
                    makeLayerWork(layerE14 , latLng, latLngBoundsArrayE14,"Earthquake");
                    makeLayerWork(layerE15 , latLng, latLngBoundsArrayE15,"Earthquake");
                    makeLayerWork(layerE16 , latLng, latLngBoundsArrayE16,"Earthquake");
                    makeLayerWork(layerE17 , latLng, latLngBoundsArrayE17,"Earthquake");
                    makeLayerWork(layerE18 , latLng, latLngBoundsArrayE18,"Earthquake");
                    makeLayerWork(layerE19 , latLng, latLngBoundsArrayE19,"Earthquake");
                    makeLayerWork(layerE20 , latLng, latLngBoundsArrayE20,"Earthquake");
                    makeLayerWork(layerE21 , latLng, latLngBoundsArrayE21,"Earthquake");
                    makeLayerWork(layerE22 , latLng, latLngBoundsArrayE22,"Earthquake");
                    makeLayerWork(layerE23 , latLng, latLngBoundsArrayE23,"Earthquake");
                    makeLayerWork(layerE24 , latLng, latLngBoundsArrayE24,"Earthquake");
                    makeLayerWork(layerE25 , latLng, latLngBoundsArrayE25,"Earthquake");
                    makeLayerWork(layerE26 , latLng, latLngBoundsArrayE26,"Earthquake");
                    makeLayerWork(layerE27 , latLng, latLngBoundsArrayE27,"Earthquake");
                    makeLayerWork(layerE28 , latLng, latLngBoundsArrayE28,"Earthquake");

                    makeLayerWork(layerF1 , latLng, latLngBoundsArrayF1,"Flood");
                    makeLayerWork(layerF2 , latLng, latLngBoundsArrayF2,"Flood");
                    makeLayerWork(layerF3 , latLng, latLngBoundsArrayF3,"Flood");
                    makeLayerWork(layerF4 , latLng, latLngBoundsArrayF4,"Flood");
                    makeLayerWork(layerF5 , latLng, latLngBoundsArrayF5,"Flood");
                    makeLayerWork(layerF6 , latLng, latLngBoundsArrayF6,"Flood");
                    makeLayerWork(layerF7 , latLng, latLngBoundsArrayF7,"Flood");
                    makeLayerWork(layerF8 , latLng, latLngBoundsArrayF8,"Flood");
                    makeLayerWork(layerF9 , latLng, latLngBoundsArrayF9,"Flood");
                    makeLayerWork(layerF10 , latLng, latLngBoundsArrayF10,"Flood");
                    makeLayerWork(layerF11 , latLng, latLngBoundsArrayF11,"Flood");
                    makeLayerWork(layerF12 , latLng, latLngBoundsArrayF12,"Flood");
                    makeLayerWork(layerF13 , latLng, latLngBoundsArrayF13,"Flood");
                    makeLayerWork(layerF14 , latLng, latLngBoundsArrayF14,"Flood");
                    makeLayerWork(layerF15 , latLng, latLngBoundsArrayF15,"Flood");
                    makeLayerWork(layerF16 , latLng, latLngBoundsArrayF16,"Flood");
                    makeLayerWork(layerF17 , latLng, latLngBoundsArrayF17,"Flood");
                    makeLayerWork(layerF18 , latLng, latLngBoundsArrayF18,"Flood");
                    makeLayerWork(layerF19 , latLng, latLngBoundsArrayF19,"Flood");
                    makeLayerWork(layerF20 , latLng, latLngBoundsArrayF20,"Flood");
                    makeLayerWork(layerF21 , latLng, latLngBoundsArrayF21,"Flood");
                    makeLayerWork(layerF22 , latLng, latLngBoundsArrayF22,"Flood");
                    makeLayerWork(layerF23 , latLng, latLngBoundsArrayF23,"Flood");
                    makeLayerWork(layerF24 , latLng, latLngBoundsArrayF24,"Flood");
                    makeLayerWork(layerF25 , latLng, latLngBoundsArrayF25,"Flood");
                    makeLayerWork(layerF26 , latLng, latLngBoundsArrayF26,"Flood");
                    makeLayerWork(layerF27 , latLng, latLngBoundsArrayF27,"Flood");
                    makeLayerWork(layerF28 , latLng, latLngBoundsArrayF28,"Flood");

                    makeLayerWork(layerL1 , latLng, latLngBoundsArrayL1,"Landslide");
                    makeLayerWork(layerL2 , latLng, latLngBoundsArrayL2,"Landslide");
                    makeLayerWork(layerL3 , latLng, latLngBoundsArrayL3,"Landslide");
                    makeLayerWork(layerL4 , latLng, latLngBoundsArrayL4,"Landslide");
                    makeLayerWork(layerL5 , latLng, latLngBoundsArrayL5,"Landslide");
                    makeLayerWork(layerL6 , latLng, latLngBoundsArrayL6,"Landslide");
                    makeLayerWork(layerL7 , latLng, latLngBoundsArrayL7,"Landslide");
                    makeLayerWork(layerL8 , latLng, latLngBoundsArrayL8,"Landslide");
                    makeLayerWork(layerL9 , latLng, latLngBoundsArrayL9,"Landslide");
                    makeLayerWork(layerL10 , latLng, latLngBoundsArrayL10,"Landslide");
                    makeLayerWork(layerL11 , latLng, latLngBoundsArrayL11,"Landslide");
                    makeLayerWork(layerL12 , latLng, latLngBoundsArrayL12,"Landslide");
                    makeLayerWork(layerL13 , latLng, latLngBoundsArrayL13,"Landslide");
                    makeLayerWork(layerL14 , latLng, latLngBoundsArrayL14,"Landslide");
                    makeLayerWork(layerL15 , latLng, latLngBoundsArrayL15,"Landslide");
                    makeLayerWork(layerL16 , latLng, latLngBoundsArrayL16,"Landslide");
                    makeLayerWork(layerL17 , latLng, latLngBoundsArrayL17,"Landslide");
                    makeLayerWork(layerL18 , latLng, latLngBoundsArrayL18,"Landslide");
                    makeLayerWork(layerL19 , latLng, latLngBoundsArrayL19,"Landslide");
                    makeLayerWork(layerL20 , latLng, latLngBoundsArrayL20,"Landslide");

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
        LatLng latLng=new LatLng(currentLatitude,currentLongitude);
        latLngX=latLng;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), MARKER_SET_ZOOM));
        /*mMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder()
                        .target(latLngX)
                        .zoom(initZoom - 1)
                        .build())
                , mapHopDelay
                , cameraAnimation
        );*/
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
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), MARKER_SET_ZOOM));
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
            if (!isMyLocCalled) {
                if (hashMapMarker != null) {
                    if (hashMapMarker.get("MyLocation") != null) {
                        removeUpMaker("MyLocation");
                        setUpMaker(mLastKnownLocation.getLatitude(),
                                mLastKnownLocation.getLongitude(), "MyLocation", "I am here!");
                    } else
                        setUpMaker(mLastKnownLocation.getLatitude(),
                                mLastKnownLocation.getLongitude(), "MyLocation", "I am here!");
                } else
                    setUpMaker(mLastKnownLocation.getLatitude(),
                            mLastKnownLocation.getLongitude(), "MyLocation", "I am here!");

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(mLastKnownLocation.getLatitude(),
                                mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                isMyLocCalled=true;
            }
        } else {
            Log.e("SahajLOG", "Current location is null. Using defaults.");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("SahajLOG", "**ON MAP READY**");
        mMap = googleMap;

        googleMap.setOnPolygonClickListener(MainActivity.this);
        googleMap.setOnMapClickListener(MainActivity.this);

        try {
            layer= makeInitLayer(R.raw.cyclone1_1);
            layerA=makeInitLayer(R.raw.cyclone1_2);
            layerB=makeInitLayer(R.raw.cyclone1_3);
            layerC=makeInitLayer(R.raw.cyclone1_4);
            layerD=makeInitLayer(R.raw.cyclone1_5);
            layerE=makeInitLayer(R.raw.cyclone1_6);
            layer2=makeInitLayer(R.raw.cyclone2_1);
            layer3=makeInitLayer(R.raw.cyclone2_2);
            layer4=makeInitLayer(R.raw.cyclone2_3);
            layerE1= makeInitLayer(R.raw.e_3_1);
            layerE2= makeInitLayer(R.raw.e_3_2);
            layerE3= makeInitLayer(R.raw.e_3_3);
            layerE4= makeInitLayer(R.raw.e_3_4);
            layerE5= makeInitLayer(R.raw.e_3_5);
            layerE6= makeInitLayer(R.raw.e_3_6);
            layerE7= makeInitLayer(R.raw.e_3_7);
            layerE8= makeInitLayer(R.raw.e_3_8);
            layerE9= makeInitLayer(R.raw.e_3_9);
            layerE10= makeInitLayer(R.raw.e_3_10);
            layerE11= makeInitLayer(R.raw.e_3_11);
            layerE12= makeInitLayer(R.raw.e_3_12);
            layerE13= makeInitLayer(R.raw.e_3_13);
            layerE14= makeInitLayer(R.raw.e_3_14);
            layerE15= makeInitLayer(R.raw.e_3_15);
            layerE16= makeInitLayer(R.raw.e_4_1);
            layerE17= makeInitLayer(R.raw.e_4_2);
            layerE18= makeInitLayer(R.raw.e_4_3);
            layerE19= makeInitLayer(R.raw.e_4_4);
            layerE20= makeInitLayer(R.raw.e_4_5);
            layerE21= makeInitLayer(R.raw.e_4_6);
            layerE22= makeInitLayer(R.raw.e_4_7);
            layerE23= makeInitLayer(R.raw.e_5_1);
            layerE24= makeInitLayer(R.raw.e_5_2);
            layerE25= makeInitLayer(R.raw.e_5_3);
            layerE26= makeInitLayer(R.raw.e_5_4);
            layerE27= makeInitLayer(R.raw.e_5_5);
            layerE28= makeInitLayer(R.raw.e_5_6);

            layerF1= makeInitLayer(R.raw.f1_f2_f3_f4);
            layerF2= makeInitLayer(R.raw.f5_1);
            layerF3= makeInitLayer(R.raw.f5_2);
            layerF4= makeInitLayer(R.raw.f5_3);
            layerF5= makeInitLayer(R.raw.f6);
            layerF6= makeInitLayer(R.raw.f7);
            layerF7= makeInitLayer(R.raw.f8);
            layerF8= makeInitLayer(R.raw.f9);
            layerF9= makeInitLayer(R.raw.f10);
            layerF10= makeInitLayer(R.raw.f11);
            layerF11= makeInitLayer(R.raw.f12__f13);
            layerF12= makeInitLayer(R.raw.f14__f15);
            layerF13= makeInitLayer(R.raw.f16);
            layerF14= makeInitLayer(R.raw.f17);
            layerF15= makeInitLayer(R.raw.f18);
            layerF16= makeInitLayer(R.raw.f19);
            layerF17= makeInitLayer(R.raw.f20_1);
            layerF18= makeInitLayer(R.raw.f20_2);
            layerF19= makeInitLayer(R.raw.f24_1);
            layerF20= makeInitLayer(R.raw.f24_2);
            layerF21= makeInitLayer(R.raw.f24_3);
            layerF22= makeInitLayer(R.raw.f24_3);
            layerF23= makeInitLayer(R.raw.f24_4);
            layerF24= makeInitLayer(R.raw.f24_5);
            layerF25= makeInitLayer(R.raw.f24_6);
            layerF26= makeInitLayer(R.raw.f24_7);
            layerF27= makeInitLayer(R.raw.f24_8);
            layerF28= makeInitLayer(R.raw.f24_9);

            layerL1= makeInitLayer(R.raw.l1_1);
            layerL2= makeInitLayer(R.raw.l1_2);
            layerL3= makeInitLayer(R.raw.l1_3);
            layerL4= makeInitLayer(R.raw.l1_4);
            layerL5= makeInitLayer(R.raw.l2_1);
            layerL6= makeInitLayer(R.raw.l2_2);
            layerL7= makeInitLayer(R.raw.l2_3);
            layerL8= makeInitLayer(R.raw.l3_1);
            layerL9= makeInitLayer(R.raw.l3_2);
            layerL10= makeInitLayer(R.raw.l3_3);
            layerL11= makeInitLayer(R.raw.l3_4);
            layerL12= makeInitLayer(R.raw.l3_5);
            layerL13= makeInitLayer(R.raw.l3_6);
            layerL14= makeInitLayer(R.raw.l3_7);
            layerL15= makeInitLayer(R.raw.l3_8);
            layerL16= makeInitLayer(R.raw.l3_9);
            layerL17= makeInitLayer(R.raw.l3_10);
            layerL18= makeInitLayer(R.raw.l4_1);
            layerL19= makeInitLayer(R.raw.l4_2);
            layerL20= makeInitLayer(R.raw.l4_3);


            latLngBoundsArray=makeLayerStyle(layer, 0x328043b4);
            latLngBoundsArrayA=makeLayerStyle(layerA,0x328043b4);
            latLngBoundsArrayB=makeLayerStyle(layerB,0x328043b4);
            latLngBoundsArrayC=makeLayerStyle(layerC,0x328043b4);
            latLngBoundsArrayD=makeLayerStyle(layerD,0x328043b4);
            latLngBoundsArrayE=makeLayerStyle(layerE,0x328043b4);
            latLngBoundsArray2=makeLayerStyle(layer2,0x328043b4);
            latLngBoundsArray3=makeLayerStyle(layer3,0x328043b4);
            latLngBoundsArray4=makeLayerStyle(layer4,0x328043b4);

            latLngBoundsArrayE1=makeLayerStyle(layerE1 ,0x32e74c3c);
            latLngBoundsArrayE2=makeLayerStyle(layerE2 ,0x32e74c3c);
            latLngBoundsArrayE3=makeLayerStyle(layerE3 ,0x32e74c3c);
            latLngBoundsArrayE4=makeLayerStyle(layerE4 ,0x32e74c3c);
            latLngBoundsArrayE5=makeLayerStyle(layerE5 ,0x32e74c3c);
            latLngBoundsArrayE6=makeLayerStyle(layerE6 ,0x32e74c3c);
            latLngBoundsArrayE7=makeLayerStyle(layerE7 ,0x32e74c3c);
            latLngBoundsArrayE8=makeLayerStyle(layerE8 ,0x32e74c3c);
            latLngBoundsArrayE9=makeLayerStyle(layerE9 ,0x32e74c3c);
            latLngBoundsArrayE10=makeLayerStyle(layerE10 ,0x32e74c3c);
            latLngBoundsArrayE11=makeLayerStyle(layerE11 ,0x32e74c3c);
            latLngBoundsArrayE12=makeLayerStyle(layerE12 ,0x32e74c3c);
            latLngBoundsArrayE13=makeLayerStyle(layerE13 ,0x32e74c3c);
            latLngBoundsArrayE14=makeLayerStyle(layerE14 ,0x32e74c3c);
            latLngBoundsArrayE15=makeLayerStyle(layerE15 ,0x32e74c3c);
            latLngBoundsArrayE16=makeLayerStyle(layerE16 ,0x32e74c3c);
            latLngBoundsArrayE17=makeLayerStyle(layerE17 ,0x32e74c3c);
            latLngBoundsArrayE18=makeLayerStyle(layerE18 ,0x32e74c3c);
            latLngBoundsArrayE19=makeLayerStyle(layerE19 ,0x32e74c3c);
            latLngBoundsArrayE20=makeLayerStyle(layerE20 ,0x32e74c3c);
            latLngBoundsArrayE21=makeLayerStyle(layerE21 ,0x32e74c3c);
            latLngBoundsArrayE22=makeLayerStyle(layerE22 ,0x32e74c3c);
            latLngBoundsArrayE23=makeLayerStyle(layerE23 ,0x32e74c3c);
            latLngBoundsArrayE24=makeLayerStyle(layerE24 ,0x32e74c3c);
            latLngBoundsArrayE25=makeLayerStyle(layerE25 ,0x32e74c3c);
            latLngBoundsArrayE26=makeLayerStyle(layerE26 ,0x32e74c3c);
            latLngBoundsArrayE27=makeLayerStyle(layerE27 ,0x32e74c3c);
            latLngBoundsArrayE28=makeLayerStyle(layerE28 ,0x32e74c3c);

            latLngBoundsArrayF1=makeLayerStyle(layerF1 ,0x32455A64);
            latLngBoundsArrayF2=makeLayerStyle(layerF2 ,0x32455A64);
            latLngBoundsArrayF3=makeLayerStyle(layerF3 ,0x32455A64);
            latLngBoundsArrayF4=makeLayerStyle(layerF4 ,0x32455A64);
            latLngBoundsArrayF5=makeLayerStyle(layerF5 ,0x32455A64);
            latLngBoundsArrayF6=makeLayerStyle(layerF6 ,0x32455A64);
            latLngBoundsArrayF7=makeLayerStyle(layerF7 ,0x32455A64);
            latLngBoundsArrayF8=makeLayerStyle(layerF8 ,0x32455A64);
            latLngBoundsArrayF9=makeLayerStyle(layerF9 ,0x32455A64);
            latLngBoundsArrayF10=makeLayerStyle(layerF10 ,0x32455A64);
            latLngBoundsArrayF11=makeLayerStyle(layerF11 ,0x32455A64);
            latLngBoundsArrayF12=makeLayerStyle(layerF12 ,0x32455A64);
            latLngBoundsArrayF13=makeLayerStyle(layerF13 ,0x32455A64);
            latLngBoundsArrayF14=makeLayerStyle(layerF14 ,0x32455A64);
            latLngBoundsArrayF15=makeLayerStyle(layerF15 ,0x32455A64);
            latLngBoundsArrayF16=makeLayerStyle(layerF16 ,0x32455A64);
            latLngBoundsArrayF17=makeLayerStyle(layerF17 ,0x32455A64);
            latLngBoundsArrayF18=makeLayerStyle(layerF18 ,0x32455A64);
            latLngBoundsArrayF19=makeLayerStyle(layerF19 ,0x32455A64);
            latLngBoundsArrayF20=makeLayerStyle(layerF20 ,0x32455A64);
            latLngBoundsArrayF21=makeLayerStyle(layerF21 ,0x32455A64);
            latLngBoundsArrayF22=makeLayerStyle(layerF22 ,0x32455A64);
            latLngBoundsArrayF23=makeLayerStyle(layerF23 ,0x32455A64);
            latLngBoundsArrayF24=makeLayerStyle(layerF24 ,0x32455A64);
            latLngBoundsArrayF25=makeLayerStyle(layerF25 ,0x32455A64);
            latLngBoundsArrayF26=makeLayerStyle(layerF26 ,0x32455A64);
            latLngBoundsArrayF27=makeLayerStyle(layerF27 ,0x32455A64);
            latLngBoundsArrayF28=makeLayerStyle(layerF28 ,0x32455A64);

            latLngBoundsArrayL1=makeLayerStyle(layerL1 ,0x32FFEA00);
            latLngBoundsArrayL2=makeLayerStyle(layerL2 ,0x32FFEA00);
            latLngBoundsArrayL3=makeLayerStyle(layerL3 ,0x32FFEA00);
            latLngBoundsArrayL4=makeLayerStyle(layerL4 ,0x32FFEA00);
            latLngBoundsArrayL5=makeLayerStyle(layerL5 ,0x32FFEA00);
            latLngBoundsArrayL6=makeLayerStyle(layerL6 ,0x32FFEA00);
            latLngBoundsArrayL7=makeLayerStyle(layerL7 ,0x32FFEA00);
            latLngBoundsArrayL8=makeLayerStyle(layerL8 ,0x32FFEA00);
            latLngBoundsArrayL9=makeLayerStyle(layerL9 ,0x32FFEA00);
            latLngBoundsArrayL10=makeLayerStyle(layerL10 ,0x32FFEA00);
            latLngBoundsArrayL11=makeLayerStyle(layerL11 ,0x32FFEA00);
            latLngBoundsArrayL12=makeLayerStyle(layerL12 ,0x32FFEA00);
            latLngBoundsArrayL13=makeLayerStyle(layerL13 ,0x32FFEA00);
            latLngBoundsArrayL14=makeLayerStyle(layerL14 ,0x32FFEA00);
            latLngBoundsArrayL15=makeLayerStyle(layerL15 ,0x32FFEA00);
            latLngBoundsArrayL16=makeLayerStyle(layerL16 ,0x32FFEA00);
            latLngBoundsArrayL17=makeLayerStyle(layerL17 ,0x32FFEA00);
            latLngBoundsArrayL18=makeLayerStyle(layerL18 ,0x32FFEA00);
            latLngBoundsArrayL19=makeLayerStyle(layerL19 ,0x32FFEA00);
            latLngBoundsArrayL20=makeLayerStyle(layerL20 ,0x32FFEA00);



            // Do other setup activities here too, as described elsewhere in this tutorial.
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Get the current location of the device and set the position of the map.
    }


    public GoogleMap.CancelableCallback cameraAnimation = new GoogleMap.CancelableCallback(){

        @Override
        public void onFinish()
        {
            if (stepZoom < stepZoomMax && stepZoom != stepToSpin)
            {
                stepZoom++;
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder()
                        .target(latLngX)
                        .zoom(initZoom + (stepZoomDetent * (stepZoom - 1)))
                                //   .bearing(40*aniStep)
                                //   .tilt(60)
                        .build()), mapHopDelay, cameraAnimation);

            }
            else if (stepZoom >= stepZoomMax)// ending position hard coded for this application
            {mMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder()
                    .target(latLngX)
                    .zoom(18)
                            //  .bearing(0)
                    .tilt(0)
                    .build()));
            }
            else
            {
                if (stepSpin <= stepSpinMax)
                {
                    stepSpin++;
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder()
                            .target(latLngX)
                            .zoom(initZoom + stepZoomDetent * stepZoom)
                            .bearing(stepSpinDetent * (stepSpin - 1))
                            .tilt(60)
                            .build()), mapHopDelay, cameraAnimation);
                }
                else
                {
                    stepZoom++;
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder()
                            .target(latLngX)
                            .zoom(initZoom + stepZoomDetent * stepZoom)
                            .bearing(0)
                            .tilt(0)
                            .build()), mapHopDelay, cameraAnimation);
                }
            }
        }

        @Override
        public void onCancel()
        {}

    };

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
            mFastItemAdapter.clear();
            prefs.edit().putBoolean("CycloneCalled",false).commit();
            prefs.edit().putBoolean("EarthquakeCalled",false).commit();
            prefs.edit().putBoolean("FloodCalled",false).commit();
            prefs.edit().putBoolean("LandslideCalled",false).commit();

            //Log.e("SahajLOG", " latlang > " + latLng + " " + layer.getBoundingBox().contains(latLng) + " " + layer.getBoundingBox().including(latLng));
            String MarkerString = "location marked";

            makeLayerWork(layer , latLng, latLngBoundsArray,"Cyclone");
            makeLayerWork(layerA, latLng, latLngBoundsArrayA,"Cyclone");
            makeLayerWork(layerB, latLng, latLngBoundsArrayB,"Cyclone");
            makeLayerWork(layerC, latLng, latLngBoundsArrayC,"Cyclone");
            makeLayerWork(layerD, latLng, latLngBoundsArrayD,"Cyclone");
            makeLayerWork(layerE, latLng, latLngBoundsArrayE,"Cyclone");
            makeLayerWork(layer2, latLng, latLngBoundsArray2,"Cyclone");
            makeLayerWork(layer3, latLng, latLngBoundsArray3,"Cyclone");
            makeLayerWork(layer4, latLng, latLngBoundsArray4,"Cyclone");

            makeLayerWork(layerE1 , latLng, latLngBoundsArrayE1,"Earthquake");
            makeLayerWork(layerE2 , latLng, latLngBoundsArrayE2,"Earthquake");
            makeLayerWork(layerE3 , latLng, latLngBoundsArrayE3,"Earthquake");
            makeLayerWork(layerE4 , latLng, latLngBoundsArrayE4,"Earthquake");
            makeLayerWork(layerE5 , latLng, latLngBoundsArrayE5,"Earthquake");
            makeLayerWork(layerE6 , latLng, latLngBoundsArrayE6,"Earthquake");
            makeLayerWork(layerE7 , latLng, latLngBoundsArrayE7,"Earthquake");
            makeLayerWork(layerE8 , latLng, latLngBoundsArrayE8,"Earthquake");
            makeLayerWork(layerE9 , latLng, latLngBoundsArrayE9,"Earthquake");
            makeLayerWork(layerE10 , latLng, latLngBoundsArrayE10,"Earthquake");
            makeLayerWork(layerE11 , latLng, latLngBoundsArrayE11,"Earthquake");
            makeLayerWork(layerE12 , latLng, latLngBoundsArrayE12,"Earthquake");
            makeLayerWork(layerE13 , latLng, latLngBoundsArrayE13,"Earthquake");
            makeLayerWork(layerE14 , latLng, latLngBoundsArrayE14,"Earthquake");
            makeLayerWork(layerE15 , latLng, latLngBoundsArrayE15,"Earthquake");
            makeLayerWork(layerE16 , latLng, latLngBoundsArrayE16,"Earthquake");
            makeLayerWork(layerE17 , latLng, latLngBoundsArrayE17,"Earthquake");
            makeLayerWork(layerE18 , latLng, latLngBoundsArrayE18,"Earthquake");
            makeLayerWork(layerE19 , latLng, latLngBoundsArrayE19,"Earthquake");
            makeLayerWork(layerE20 , latLng, latLngBoundsArrayE20,"Earthquake");
            makeLayerWork(layerE21 , latLng, latLngBoundsArrayE21,"Earthquake");
            makeLayerWork(layerE22 , latLng, latLngBoundsArrayE22,"Earthquake");
            makeLayerWork(layerE23 , latLng, latLngBoundsArrayE23,"Earthquake");
            makeLayerWork(layerE24 , latLng, latLngBoundsArrayE24,"Earthquake");
            makeLayerWork(layerE25 , latLng, latLngBoundsArrayE25,"Earthquake");
            makeLayerWork(layerE26 , latLng, latLngBoundsArrayE26,"Earthquake");
            makeLayerWork(layerE27 , latLng, latLngBoundsArrayE27,"Earthquake");
            makeLayerWork(layerE28 , latLng, latLngBoundsArrayE28,"Earthquake");

            makeLayerWork(layerF1 , latLng, latLngBoundsArrayF1,"Flood");
            makeLayerWork(layerF2 , latLng, latLngBoundsArrayF2,"Flood");
            makeLayerWork(layerF3 , latLng, latLngBoundsArrayF3,"Flood");
            makeLayerWork(layerF4 , latLng, latLngBoundsArrayF4,"Flood");
            makeLayerWork(layerF5 , latLng, latLngBoundsArrayF5,"Flood");
            makeLayerWork(layerF6 , latLng, latLngBoundsArrayF6,"Flood");
            makeLayerWork(layerF7 , latLng, latLngBoundsArrayF7,"Flood");
            makeLayerWork(layerF8 , latLng, latLngBoundsArrayF8,"Flood");
            makeLayerWork(layerF9 , latLng, latLngBoundsArrayF9,"Flood");
            makeLayerWork(layerF10 , latLng, latLngBoundsArrayF10,"Flood");
            makeLayerWork(layerF11 , latLng, latLngBoundsArrayF11,"Flood");
            makeLayerWork(layerF12 , latLng, latLngBoundsArrayF12,"Flood");
            makeLayerWork(layerF13 , latLng, latLngBoundsArrayF13,"Flood");
            makeLayerWork(layerF14 , latLng, latLngBoundsArrayF14,"Flood");
            makeLayerWork(layerF15 , latLng, latLngBoundsArrayF15,"Flood");
            makeLayerWork(layerF16 , latLng, latLngBoundsArrayF16,"Flood");
            makeLayerWork(layerF17 , latLng, latLngBoundsArrayF17,"Flood");
            makeLayerWork(layerF18 , latLng, latLngBoundsArrayF18,"Flood");
            makeLayerWork(layerF19 , latLng, latLngBoundsArrayF19,"Flood");
            makeLayerWork(layerF20 , latLng, latLngBoundsArrayF20,"Flood");
            makeLayerWork(layerF21 , latLng, latLngBoundsArrayF21,"Flood");
            makeLayerWork(layerF22 , latLng, latLngBoundsArrayF22,"Flood");
            makeLayerWork(layerF23 , latLng, latLngBoundsArrayF23,"Flood");
            makeLayerWork(layerF24 , latLng, latLngBoundsArrayF24,"Flood");
            makeLayerWork(layerF25 , latLng, latLngBoundsArrayF25,"Flood");
            makeLayerWork(layerF26 , latLng, latLngBoundsArrayF26,"Flood");
            makeLayerWork(layerF27 , latLng, latLngBoundsArrayF27,"Flood");
            makeLayerWork(layerF28 , latLng, latLngBoundsArrayF28,"Flood");

            makeLayerWork(layerL1 , latLng, latLngBoundsArrayL1,"Landslide");
            makeLayerWork(layerL2 , latLng, latLngBoundsArrayL2,"Landslide");
            makeLayerWork(layerL3 , latLng, latLngBoundsArrayL3,"Landslide");
            makeLayerWork(layerL4 , latLng, latLngBoundsArrayL4,"Landslide");
            makeLayerWork(layerL5 , latLng, latLngBoundsArrayL5,"Landslide");
            makeLayerWork(layerL6 , latLng, latLngBoundsArrayL6,"Landslide");
            makeLayerWork(layerL7 , latLng, latLngBoundsArrayL7,"Landslide");
            makeLayerWork(layerL8 , latLng, latLngBoundsArrayL8,"Landslide");
            makeLayerWork(layerL9 , latLng, latLngBoundsArrayL9,"Landslide");
            makeLayerWork(layerL10 , latLng, latLngBoundsArrayL10,"Landslide");
            makeLayerWork(layerL11 , latLng, latLngBoundsArrayL11,"Landslide");
            makeLayerWork(layerL12 , latLng, latLngBoundsArrayL12,"Landslide");
            makeLayerWork(layerL13 , latLng, latLngBoundsArrayL13,"Landslide");
            makeLayerWork(layerL14 , latLng, latLngBoundsArrayL14,"Landslide");
            makeLayerWork(layerL15 , latLng, latLngBoundsArrayL15,"Landslide");
            makeLayerWork(layerL16 , latLng, latLngBoundsArrayL16,"Landslide");
            makeLayerWork(layerL17 , latLng, latLngBoundsArrayL17,"Landslide");
            makeLayerWork(layerL18 , latLng, latLngBoundsArrayL18,"Landslide");
            makeLayerWork(layerL19 , latLng, latLngBoundsArrayL19,"Landslide");
            makeLayerWork(layerL20 , latLng, latLngBoundsArrayL20,"Landslide");

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

    private Boolean makeLayerWork(GeoJsonLayer layer,LatLng latLng,List<LatLngBounds> latLngBoundsArray,String DisasterType){
        int i = 0;
        Boolean markerCalled=false;
        if (layer != null)
            for (LatLngBounds bbc : latLngBoundsArray) {
                Log.e("SahajLOG", "callad A1" + bbc.contains(latLng));
                if (bbc.contains(latLng)) {
                    if (!prefs.getBoolean(DisasterType+"Called",false)){
                        MainUpRecyclerItemAdapter sMainUpRecyclerItemAdapter=new MainUpRecyclerItemAdapter();
                        sMainUpRecyclerItemAdapter.setContext(MainActivity.this);
                        sMainUpRecyclerItemAdapter.setDisasterType(DisasterType);
                        sMainUpRecyclerItemAdapter.setLayer(layer);
                        sMainUpRecyclerItemAdapter.setLatLng(latLng);
                        sMainUpRecyclerItemAdapter.setLatLngBoundsArray(latLngBoundsArray);
                        addItemRecyclerUp(sMainUpRecyclerItemAdapter);
                        prefs.edit().putBoolean(DisasterType+"Called",true).commit();
                        mFastItemAdapter.notifyAdapterDataSetChanged();
                    }
                    //Log.e("SahajLOG", "count "+mFastItemAdapter.getItemAdapter().getItemCount()+mFastItemAdapter.getAdapterItems());
                   // if (mFastItemAdapter.getItemAdapter().getItemCount()>0){
                        if (mLinearLayoutUp.getVisibility()==View.GONE) {
                            Log.e("SahajLOG", "called*(*(*");
                            //mLinearLayoutUp.setVisibility(View.VISIBLE);
                           // mLinearLayoutUp.setAlpha(0.0f);
                            Log.e("SahajLOG", "height*********** "+mLinearLayoutUp.getHeight());
                            mLinearLayoutUp.setTranslationY(mLinearLayoutUp.getHeight());
                            mLinearLayoutUp.animate()
                                    .setDuration(400)
                                    .translationY(0)
                                  //  .alpha(1.0f)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            Log.e("SahajLOG", "calledYOYOYOYO**********");
                                            super.onAnimationEnd(animation);
                                            mLinearLayoutUp.setVisibility(View.VISIBLE);
                                            mFabBack.setVisibility(View.VISIBLE);
                                            mFabMulti.setVisibility(View.GONE);
                                        }
                                    });
                        }
                   // }
                    i++;
                    layer.addLayerToMap();
                    markerCalled = true;
                } else if (layer.isLayerOnMap() && i == 0) {
                    layer.removeLayerFromMap();
                }
                /**/
            }
        return markerCalled;
    }

    private ArrayList<LatLngBounds> makeLayerStyle(GeoJsonLayer layer, int color){
        GeoJsonPolygonStyle polygonStyle=layer.getDefaultPolygonStyle();
        polygonStyle.setFillColor(color);
        polygonStyle.setStrokeWidth(1.5f);
        ArrayList<LatLngBounds> latLngBoundsArray=new ArrayList<>();
        latLngBoundsArray.addAll(getLatLngBoundingBox(layer));
        return latLngBoundsArray;
    }

    private GeoJsonLayer makeInitLayer(int resIdJson){
        try {
            GeoJsonLayer layerX = new GeoJsonLayer(mMap, resIdJson,
                    getApplicationContext());
            return layerX;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
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
        if (layerE4!=null)
            if (layerE4.isLayerOnMap())
                layerE4.removeLayerFromMap();
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
        if (layerE16!=null)
            if (layerE16.isLayerOnMap())
                layerE16.removeLayerFromMap();
        if (layerE17!=null)
            if (layerE17.isLayerOnMap())
                layerE17.removeLayerFromMap();
        if (layerE18!=null)
            if (layerE18.isLayerOnMap())
                layerE18.removeLayerFromMap();
        if (layerE19!=null)
            if (layerE19.isLayerOnMap())
                layerE19.removeLayerFromMap();
        if (layerE20!=null)
            if (layerE20.isLayerOnMap())
                layerE20.removeLayerFromMap();
        if (layerE21!=null)
            if (layerE21.isLayerOnMap())
                layerE21.removeLayerFromMap();
        if (layerE22!=null)
            if (layerE22.isLayerOnMap())
                layerE22.removeLayerFromMap();
        if (layerE23!=null)
            if (layerE23.isLayerOnMap())
                layerE23.removeLayerFromMap();
        if (layerE24!=null)
            if (layerE24.isLayerOnMap())
                layerE24.removeLayerFromMap();
        if (layerE25!=null)
            if (layerE25.isLayerOnMap())
                layerE25.removeLayerFromMap();
        if (layerE26!=null)
            if (layerE26.isLayerOnMap())
                layerE26.removeLayerFromMap();
        if (layerE27!=null)
            if (layerE27.isLayerOnMap())
                layerE27.removeLayerFromMap();
        if (layerE28!=null)
            if (layerE28.isLayerOnMap())
                layerE28.removeLayerFromMap();

        if (layerF1!=null)
            if (layerF1.isLayerOnMap())
                layerF1.removeLayerFromMap();
        if (layerF2!=null)
            if (layerF2.isLayerOnMap())
                layerF2.removeLayerFromMap();
        if (layerF3!=null)
            if (layerF3.isLayerOnMap())
                layerF3.removeLayerFromMap();
        if (layerF5!=null)
            if (layerF5.isLayerOnMap())
                layerF5.removeLayerFromMap();
        if (layerF6!=null)
            if (layerF6.isLayerOnMap())
                layerF6.removeLayerFromMap();
        if (layerF7!=null)
            if (layerF7.isLayerOnMap())
                layerF7.removeLayerFromMap();
        if (layerF8!=null)
            if (layerF8.isLayerOnMap())
                layerF8.removeLayerFromMap();
        if (layerF9!=null)
            if (layerF9.isLayerOnMap())
                layerF9.removeLayerFromMap();
        if (layerF10!=null)
            if (layerF10.isLayerOnMap())
                layerF10.removeLayerFromMap();
        if (layerF11!=null)
            if (layerF11.isLayerOnMap())
                layerF11.removeLayerFromMap();
        if (layerF12!=null)
            if (layerF12.isLayerOnMap())
                layerF12.removeLayerFromMap();
        if (layerF13!=null)
            if (layerF13.isLayerOnMap())
                layerF13.removeLayerFromMap();
        if (layerF14!=null)
            if (layerF14.isLayerOnMap())
                layerF14.removeLayerFromMap();
        if (layerF15!=null)
            if (layerF15.isLayerOnMap())
                layerF15.removeLayerFromMap();
        if (layerF16!=null)
            if (layerF16.isLayerOnMap())
                layerF16.removeLayerFromMap();
        if (layerF17!=null)
            if (layerF17.isLayerOnMap())
                layerF17.removeLayerFromMap();
        if (layerF18!=null)
            if (layerF18.isLayerOnMap())
                layerF18.removeLayerFromMap();
        if (layerF19!=null)
            if (layerF19.isLayerOnMap())
                layerF19.removeLayerFromMap();
        if (layerF20!=null)
            if (layerF20.isLayerOnMap())
                layerF20.removeLayerFromMap();
        if (layerF21!=null)
            if (layerF21.isLayerOnMap())
                layerF21.removeLayerFromMap();
        if (layerF22!=null)
            if (layerF22.isLayerOnMap())
                layerF22.removeLayerFromMap();
        if (layerF23!=null)
            if (layerF23.isLayerOnMap())
                layerF23.removeLayerFromMap();
        if (layerF24!=null)
            if (layerF24.isLayerOnMap())
                layerF24.removeLayerFromMap();
        if (layerF25!=null)
            if (layerF25.isLayerOnMap())
                layerF25.removeLayerFromMap();
        if (layerF26!=null)
            if (layerF26.isLayerOnMap())
                layerF26.removeLayerFromMap();
        if (layerF27!=null)
            if (layerF27.isLayerOnMap())
                layerF27.removeLayerFromMap();
        if (layerF28!=null)
            if (layerF28.isLayerOnMap())
                layerF28.removeLayerFromMap();

        if (layerL1!=null)
            if (layerL1.isLayerOnMap())
                layerL1.removeLayerFromMap();
        if (layerL2!=null)
            if (layerL2.isLayerOnMap())
                layerL2.removeLayerFromMap();
        if (layerL3!=null)
            if (layerL3.isLayerOnMap())
                layerL3.removeLayerFromMap();
        if (layerL5!=null)
            if (layerL5.isLayerOnMap())
                layerL5.removeLayerFromMap();
        if (layerL6!=null)
            if (layerL6.isLayerOnMap())
                layerL6.removeLayerFromMap();
        if (layerL7!=null)
            if (layerL7.isLayerOnMap())
                layerL7.removeLayerFromMap();
        if (layerL8!=null)
            if (layerL8.isLayerOnMap())
                layerL8.removeLayerFromMap();
        if (layerL9!=null)
            if (layerL9.isLayerOnMap())
                layerL9.removeLayerFromMap();
        if (layerL10!=null)
            if (layerL10.isLayerOnMap())
                layerL10.removeLayerFromMap();
        if (layerL11!=null)
            if (layerL11.isLayerOnMap())
                layerL11.removeLayerFromMap();
        if (layerL12!=null)
            if (layerL12.isLayerOnMap())
                layerL12.removeLayerFromMap();
        if (layerL13!=null)
            if (layerL13.isLayerOnMap())
                layerL13.removeLayerFromMap();
        if (layerL14!=null)
            if (layerL14.isLayerOnMap())
                layerL14.removeLayerFromMap();
        if (layerL15!=null)
            if (layerL15.isLayerOnMap())
                layerL15.removeLayerFromMap();
        if (layerL16!=null)
            if (layerL16.isLayerOnMap())
                layerL16.removeLayerFromMap();
        if (layerL17!=null)
            if (layerL17.isLayerOnMap())
                layerL17.removeLayerFromMap();
        if (layerL18!=null)
            if (layerL18.isLayerOnMap())
                layerL18.removeLayerFromMap();
        if (layerL19!=null)
            if (layerL19.isLayerOnMap())
                layerL19.removeLayerFromMap();
        if (layerL20!=null)
            if (layerL20.isLayerOnMap())
                layerL20.removeLayerFromMap();

    }

    private void addItemRecyclerUp(final MainUpRecyclerItemAdapter sMainUpRecyclerItemAdapter) {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                Log.e("SahajLOG", "MainUpItem "+sMainUpRecyclerItemAdapter.getDisasterType());
                mFastItemAdapter.add(sMainUpRecyclerItemAdapter);
            }
        });
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
