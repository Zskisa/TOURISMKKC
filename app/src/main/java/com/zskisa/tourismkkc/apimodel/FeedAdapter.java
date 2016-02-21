package com.zskisa.tourismkkc.apimodel;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
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
        FeedViewHolder pvh = new FeedViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        holder.placesName.setText(places.get(position).getPlaces_name());
        holder.typeName.setText(places.get(position).getType_detail_name());
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
        TextView placesRate;
        ImageView placesPhoto;

        FeedViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            placesName = (TextView) itemView.findViewById(R.id.rcv_places_name);
            typeName = (TextView) itemView.findViewById(R.id.rcv_type_name);
            placesRate = (TextView) itemView.findViewById(R.id.rcv_places_rate);
            placesPhoto = (ImageView) itemView.findViewById(R.id.rcv_places_photo);
        }
    }
}
