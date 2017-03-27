package com.tekraiders.wherehouse.wherehouse;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.helpers.ClickListenerHelper;
import com.tekraiders.wherehouse.wherehouse.tabs.MainUpRecyclerItemAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FastItemAdapter<ContactRecyItemAdapter> mFastItemAdapter;
    //private ItemAdapter<SimpleImageItem> mItemAdapter;
    private ClickListenerHelper<ContactRecyItemAdapter> mClickListenerHelper;
    private LinearLayoutManager mLayoutManager;
    private List<ContactRecyItemAdapter> getContactItems() {
        return toList(
                new ContactRecyItemAdapter().withIdentifier(1L).withContext(this).withTextState("Principal Secretaries/ Relief Commissioners Of States").withImageViewStateResID(R.drawable.secretary).withRawFile(R.raw.principal_sec),
                new ContactRecyItemAdapter().withIdentifier(2L).withContext(this).withTextState("Chief Secretaries All States").withImageViewStateResID(R.drawable.boss__1_).withRawFile(R.raw.chief),
                new ContactRecyItemAdapter().withIdentifier(3L).withContext(this).withTextState("NDMA (National Disaster Management Disaster)").withImageViewStateResID(R.drawable.reunion).withRawFile(R.raw.ndma),
                new ContactRecyItemAdapter().withIdentifier(4L).withContext(this).withTextState("Himachal Pradesh").withImageViewStateResID(R.drawable.goal).withRawFile(R.raw.himachal_pradesh),
                new ContactRecyItemAdapter().withIdentifier(5L).withContext(this).withTextState("Madhya Pradesh").withImageViewStateResID(R.drawable.park).withRawFile(R.raw.madhya_pradesh),
                new ContactRecyItemAdapter().withIdentifier(6L).withContext(this).withTextState("Assam").withImageViewStateResID(R.drawable.mountains).withRawFile(R.raw.assam),
                new ContactRecyItemAdapter().withIdentifier(7L).withContext(this).withTextState("Meghalaya").withImageViewStateResID(R.drawable.waterfall).withRawFile(R.raw.meghalaya),
                new ContactRecyItemAdapter().withIdentifier(8L).withContext(this).withTextState("Uttar pradesh").withImageViewStateResID(R.drawable.field).withRawFile(R.raw.uttar_pradesh),
                new ContactRecyItemAdapter().withIdentifier(9L).withContext(this).withTextState("Gujarat").withImageViewStateResID(R.drawable.cliff).withRawFile(R.raw.gujarat),
                new ContactRecyItemAdapter().withIdentifier(10L).withContext(this).withTextState("Rajasthan").withImageViewStateResID(R.drawable.desert).withRawFile(R.raw.rajasthan),
                new ContactRecyItemAdapter().withIdentifier(11L).withContext(this).withTextState("Jammu & Kashmir").withImageViewStateResID(R.drawable.hills).withRawFile(R.raw.jammu_kashmir)
        );
    }
    private static List<ContactRecyItemAdapter> toList(ContactRecyItemAdapter... contactItems) {
        return Arrays.asList(contactItems);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar mToolbar=(Toolbar)findViewById(R.id.contact_toolbar);
        setSupportActionBar(mToolbar);
        setTitle("");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mFastItemAdapter = new FastItemAdapter<>();
        mFastItemAdapter.withSavedInstanceState(savedInstanceState);
        mRecyclerView=(RecyclerView)findViewById(R.id.contact_recycler);
        mClickListenerHelper = new ClickListenerHelper<>(mFastItemAdapter);
        mFastItemAdapter.withOnClickListener(new FastAdapter.OnClickListener<ContactRecyItemAdapter>() {
            @Override
            public boolean onClick(View v, IAdapter<ContactRecyItemAdapter> adapter, ContactRecyItemAdapter item, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("heading", item.mTextState);
                bundle.putInt("intRawFile", item.mRawFile);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                OpenContactFragment fragObj = new OpenContactFragment();
                fragObj.setArguments(bundle);
                ft.replace(R.id.contact_frame, fragObj, "about");
                ft.setCustomAnimations(R.anim.activity_open_translate_from_bottom, R.anim.activity_no_animation);
                ft.addToBackStack(null);
                ft.commit();
                return false;
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // if (mLayoutManager==null){
        Log.e("SahajLOG11", "mLayautNull Called");
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mFastItemAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //add some dummy data
                mFastItemAdapter.add(getContactItems());
            }
        }, 50);
       /* mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                Log.e("SahajLOG", "ContactItem "+getContactItems());

            }
        });*/

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

}
