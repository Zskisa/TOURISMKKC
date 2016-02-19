package com.zskisa.tourismkkc;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutFragment extends Fragment {

    CircleImageView img2, img3;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about, container, false);
        init();

        String urlImg2 = "https://graph.facebook.com/100001584548493/picture?type=large";
        String urlImg3 = "https://graph.facebook.com/100002471144491/picture?type=large";

        Picasso.with(getActivity())
                .load(urlImg2)
                .placeholder(R.drawable.com_facebook_profile_picture_blank_portrait)
                .error(R.drawable.ic_menu_gallery)
                .into(img2);

        Picasso.with(getActivity())
                .load(urlImg3)
                .placeholder(R.drawable.com_facebook_profile_picture_blank_portrait)
                .error(R.drawable.ic_menu_gallery)
                .into(img3);

        return view;
    }

    private void init() {
        img2 = (CircleImageView) view.findViewById(R.id.about_img2);
        img3 = (CircleImageView) view.findViewById(R.id.about_img3);
    }
}
