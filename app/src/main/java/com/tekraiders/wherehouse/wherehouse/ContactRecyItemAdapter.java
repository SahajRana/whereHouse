package com.tekraiders.wherehouse.wherehouse;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

import java.util.List;

/**
 * Created by Sahaj on 3/26/2017.
 */
public class ContactRecyItemAdapter extends AbstractItem<ContactRecyItemAdapter,ContactRecyItemAdapter.ViewHolder> {
    private static final ViewHolderFactory<? extends ViewHolder> FACTORY = new ItemFactory();

    public int mImageViewStateResID;
    public Context mContext;
    public String mTextState;
    public int mRawFile;

    public ContactRecyItemAdapter withImageViewStateResID(int imageViewStateResID) {
        mImageViewStateResID = imageViewStateResID;
        return this;
    }
    public ContactRecyItemAdapter withTextState(String textState) {
        mTextState = textState;
        return this;
    }
    public ContactRecyItemAdapter withContext(Context context) {
        mContext = context;
        return this;
    }
    public ContactRecyItemAdapter withRawFile(int stringRawFile) {
        mRawFile=stringRawFile;
        return this;
    }


    @Override
    public int getType() {
        return R.id.fastadapter_ContactRecyItemAdapter_id;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.contact_item;
    }

    public interface OnItemClickListener {

    }



    @Override
    public void bindView(ViewHolder viewHolder,List<Object> payloads) {
        //call super so the selection is already handled for you
        super.bindView(viewHolder,payloads);
        // Log.e("SahajLOG", "hashhhhh --> "+getHashname());
        viewHolder.typeTextView.setText(mTextState);
        Picasso.with(mContext).load(mImageViewStateResID).transform(new CircleTransform()).into(viewHolder.mImageViewState);
    }

    @Override
    public void unbindView(ViewHolder viewHolder) {
        super.unbindView(viewHolder);
        //holder.name.setText(null);
        viewHolder.typeTextView.setText(null);
        viewHolder.mImageViewState.setImageDrawable(null);
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
        private ImageView mImageViewState;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mImageViewState=(ImageView)itemView.findViewById(R.id.contact_image);
            this.typeTextView=(TextView)itemView.findViewById(R.id.contact_textview);
        }
    }
}


