package com.tekraiders.wherehouse.wherehouse.tabs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.fastadapter.utils.ViewHolderFactory;
import com.squareup.picasso.Picasso;
import com.tekraiders.wherehouse.wherehouse.CircleTransform;
import com.tekraiders.wherehouse.wherehouse.R;

import java.util.List;

/**
 * Created by Sahaj on 3/25/2017.
 */
public class MainUpRecyclerItemAdapter extends AbstractItem<MainUpRecyclerItemAdapter,MainUpRecyclerItemAdapter.ViewHolder> {
    private static final ViewHolderFactory<? extends ViewHolder> FACTORY = new ItemFactory();

    private String mDisasterType;
    private GeoJsonLayer mLayer;
    private LatLng mLatLng;
    private List<LatLngBounds> mLatLngBoundsArray;
    private Context mContext;


    public LatLng getLatLng() {
        return mLatLng;
    }

    public void setLatLng(LatLng latLng) {
        mLatLng = latLng;
    }

    public List<LatLngBounds> getLatLngBoundsArray() {
        return mLatLngBoundsArray;
    }

    public void setLatLngBoundsArray(List<LatLngBounds> latLngBoundsArray) {
        mLatLngBoundsArray = latLngBoundsArray;
    }

    public GeoJsonLayer getLayer() {
        return mLayer;
    }

    public void setLayer(GeoJsonLayer layer) {
        mLayer = layer;
    }

    public String getDisasterType() {
        return mDisasterType;
    }

    public void setDisasterType(String disasterType) {
        mDisasterType = disasterType;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }



    @Override
    public int getType() {
        return R.id.fastadapter_MainUpRecyclerItemAdapter_id;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.up_item;
    }

    public interface OnItemClickListener {

    }



    @Override
    public void bindView(ViewHolder viewHolder,List<Object> payloads) {
        //call super so the selection is already handled for you
        super.bindView(viewHolder,payloads);
        // Log.e("SahajLOG", "hashhhhh --> "+getHashname());
        viewHolder.typeTextView.setText(getDisasterType());
        switch(getDisasterType()){
            case "Cyclone": viewHolder.mFrameLayout.setBackgroundColor(getContext().getResources().getColor(R.color.CblueItem));
                break;
            case "Earthquake": viewHolder.mFrameLayout.setBackgroundColor(getContext().getResources().getColor(R.color.EredItem));
                break;
            case "Flood": viewHolder.mFrameLayout.setBackgroundColor(getContext().getResources().getColor(R.color.FblueItem));
                break;
            case "Landslide": viewHolder.mFrameLayout.setBackgroundColor(getContext().getResources().getColor(R.color.LItem));
                break;
        }
        //Picasso.with(getContext()).load(getMpicUserURL()).transform(new CircleTransform()).into(viewHolder.picUser);
    }

    @Override
    public void unbindView(ViewHolder viewHolder) {
        super.unbindView(viewHolder);
        //holder.name.setText(null);
        viewHolder.typeTextView.setText(null);
        //holder.description.setText(null);
    }


    protected static class ItemFactory implements ViewHolderFactory<ViewHolder> {
        public ViewHolder create(View v) {
            return new ViewHolder(v);
        }
    }
    @Override
    public ViewHolderFactory<? extends ViewHolder> getFactory() {
        return FACTORY;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView typeTextView;
        private FrameLayout mFrameLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mFrameLayout=(FrameLayout)itemView.findViewById(R.id.up_item_framel);
            this.typeTextView=(TextView)itemView.findViewById(R.id.up_item_text);
        }
    }
}

