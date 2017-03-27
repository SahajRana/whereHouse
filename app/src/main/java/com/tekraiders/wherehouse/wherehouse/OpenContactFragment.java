package com.tekraiders.wherehouse.wherehouse;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class OpenContactFragment extends Fragment {

    private TextView mTextView,mTextToolbar;
    private ImageButton mImageButton;

    public OpenContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_open_contact, container, false);
        Toolbar mToolbar=(Toolbar)view.findViewById(R.id.contact_frag_toolbar);
        Bundle bundle = this.getArguments();
        //String mTextToolbar = bundle.getString("heading");
        mTextView=(TextView)view.findViewById(R.id.contact_frag_text);
        mImageButton=(ImageButton)view.findViewById(R.id.contact_frag_back);
        mTextToolbar=(TextView)view.findViewById(R.id.contact_frag_toolbarText);
        mTextToolbar.setText(bundle.getString("heading"));
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                OpenContactFragment f = (OpenContactFragment) fm.findFragmentByTag("about");
                ft.remove(f);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                ft.commit();
            }
        });
        try {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(bundle.getInt("intRawFile"));
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            mTextView.setText(new String(b));
        } catch (Exception e) {
            // e.printStackTrace();
            mTextView.setText("Error: can't show help.");
        }

        return view;
    }

}
