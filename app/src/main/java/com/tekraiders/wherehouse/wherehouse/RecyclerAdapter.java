package com.tekraiders.wherehouse.wherehouse;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.tekraiders.wherehouse.wherehouse.drawer.InformationReclyclerDrawer;

import java.util.Collections;
import java.util.List;


/**
 * Created by Sahaj on 2/7/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    private LayoutInflater inflator;

    List<InformationReclyclerDrawer> data= Collections.EMPTY_LIST;
    public Context context;
    public ClickListener mClickListener;
    public View myBackground;
    public SparseBooleanArray selectedItems;
    public static int selected_position=0;
    // public android.util.AttributeSet drawer_list_selector;


    public RecyclerAdapter(Context context, List<InformationReclyclerDrawer> data){
        inflator = LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {                        //Creates new viewHolder to represent the item
        View view=inflator.inflate(R.layout.custom_row,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        InformationReclyclerDrawer current= data.get(position);
        holder.titlea.setText(current.title);
        holder.icona.setImageResource(current.iconID);
        if(selected_position == position){
            // Here I am just highlighting the background
            holder.titlea.setTextColor(Color.parseColor("#434343"));
            holder.itemView.setBackgroundResource(R.color.navi_drawer_on_selection);
        }else{
            holder.titlea.setTextColor(Color.parseColor("#545454"));
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
       // holder.itemView.setOnClickListener(new View.OnClickListener() {
         //   @Override
         //   public void onClick(View v) {

                // Updating old as well as new positions
              //  notifyItemChanged(selected_position);
               // selected_position = holder.getAdapterPosition();
               // notifyItemChanged(selected_position);
           //     if(holder.getAdapterPosition()==1){
            //        Intent intent=new Intent(holder.itemView.getContext(),Profile.class);
             //       holder.itemView.getContext().startActivity(intent);

             //   }else if(holder.getAdapterPosition()==6){
             //       Intent intent=new Intent(holder.itemView.getContext(),AboutUsActivity.class);
              ///      holder.itemView.getContext().startActivity(intent);
              ///  }else if(holder.getAdapterPosition()==3){
             //       Intent intent=new Intent(holder.itemView.getContext(),SplashscreenActivity.class);
             //       holder.itemView.getContext().startActivity(intent);
             //   }else if(holder.getAdapterPosition()==4){

             //   }else{
             //       holder.mViewPager.setCurrentItem(holder.getAdapterPosition());
              //  }

                // Do your another stuff for your onClick
           // }
        //});

        // ClipData.Item item = items.get(position);


    }

    public void setClickListener(ClickListener clickListener) {
        this.mClickListener = clickListener;
    }
    public interface ClickListener{
        public  void itemClicked(View view, int position);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

       // private final ViewPager mViewPager;
        TextView titlea;
        ImageView icona;
        //public View myBackground=new View(context,drawer_list_selector,R.id.myBackground);


        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
             //mViewPager = (ViewPager) itemView.findViewById(R.id.pager);
            titlea=(TextView)itemView.findViewById(R.id.listText);
            icona=(ImageView)itemView.findViewById(R.id.listIcon);
        }

        @Override
        public void onClick(View v) {
            if(mClickListener!=null){
                notifyItemChanged(selected_position);
                selected_position = getAdapterPosition();
                notifyItemChanged(selected_position);
                mClickListener.itemClicked(v,getAdapterPosition());
            }

        }
    }

}