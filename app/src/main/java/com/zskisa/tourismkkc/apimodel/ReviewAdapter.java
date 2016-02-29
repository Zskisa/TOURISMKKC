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

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    List<ApiFeedReview.DataEntity.ResultEntity> reviews;

    public ReviewAdapter(List<ApiFeedReview.DataEntity.ResultEntity> reviews) {
        this.reviews = reviews;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_row, parent, false);
        return new ReviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        /*
        * ดึงรายละเอียดของรีวิวและรูปมาแสดงผลเป็น listView
        * */
        holder.reviewDatail.setText(reviews.get(position).getReview_detail());
        holder.reviewDate.setText(reviews.get(position).getReview_datetime());
        holder.rateValue.setText(reviews.get(position).getRate_value());
        Context context = holder.userPhoto.getContext();
        String sUID = "";
        if (!reviews.get(position).getUser_fb_id().isEmpty()) {
            sUID = "https://graph.facebook.com/" + reviews.get(position).getUser_fb_id() + "/picture?type=large";
        } else {
            sUID = "https://graph.facebook.com//picture?type=large";
        }
        Picasso.with(context).load(sUID).fit().centerCrop().into(holder.userPhoto);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView reviewDatail;
        TextView reviewDate;
        TextView rateValue;
        ImageView userPhoto;

        ReviewViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.rr_cv);
            reviewDatail = (TextView) itemView.findViewById(R.id.rr_review_detail);
            reviewDate = (TextView) itemView.findViewById(R.id.rr_review_datetime);
            rateValue = (TextView) itemView.findViewById(R.id.rr_rate_value);
            userPhoto = (ImageView) itemView.findViewById(R.id.rr_user_photo);
        }
    }
}
