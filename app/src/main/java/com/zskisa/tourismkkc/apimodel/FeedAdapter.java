package com.zskisa.tourismkkc.apimodel;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zskisa.tourismkkc.DetailDialog;
import com.zskisa.tourismkkc.DetailFragment;
import com.zskisa.tourismkkc.MainActivity;
import com.zskisa.tourismkkc.R;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {
    List<ApiFeed.DataEntity.ResultEntity> places;

    public FeedAdapter(List<ApiFeed.DataEntity.ResultEntity> places) {
        this.places = places;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_row, viewGroup, false);
        return new FeedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FeedViewHolder holder, final int position) {

        /*
        * เวลาคลิกที่รายการจะเด้งไปหน้ารายละเอียด
        * */
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //สร้าง Object ของ DetailFragment
                DetailFragment detailFragment = new DetailFragment();

                //สร้าง Bundle เพื่อเตรียมส่งค่า placesID ไปพร้อมกับการเปลี่ยนหน้า
                Bundle bundle = new Bundle();
                bundle.putString("placesID", places.get(position).getPlaces_id());
                detailFragment.setArguments(bundle);

                //เปลี่ยนหน้า
                FragmentTransaction transaction = MainActivity.mFragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_content, detailFragment);
                transaction.commit();
            }
        });

        /*
        * ดึงรายละเอียดของ ชื่อ รายละเอียด และรูปมาแสดงผลเป็น listView
        * */
        holder.placesName.setText(places.get(position).getPlaces_name());
        holder.typeName.setText(places.get(position).getType_detail_name());
        holder.placesRate.setRating(Float.parseFloat(places.get(position).getAvgstar()));
        Context context = holder.placesPhoto.getContext();
        if (!places.get(position).getPhoto_link().isEmpty()) {
            Picasso.with(context).load(places.get(position).getPhoto_link()).fit().centerCrop().into(holder.placesPhoto);
        }
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView placesName;
        TextView typeName;
        RatingBar placesRate;
        ImageView placesPhoto;

        FeedViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            placesName = (TextView) itemView.findViewById(R.id.rcv_places_name);
            typeName = (TextView) itemView.findViewById(R.id.rcv_type_name);
            placesRate = (RatingBar) itemView.findViewById(R.id.ratingBar_feed);
            placesPhoto = (ImageView) itemView.findViewById(R.id.rcv_places_photo);
        }
    }
}
