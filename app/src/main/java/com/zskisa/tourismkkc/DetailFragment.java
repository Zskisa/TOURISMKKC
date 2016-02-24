package com.zskisa.tourismkkc;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.zskisa.tourismkkc.apimodel.ApiPlaces;

public class DetailFragment extends Fragment {
    private View view;
    private String placesID;
    private SliderLayout mDemoSlider;
    private TextView txtName, txtDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail, container, false);

        txtName = (TextView) view.findViewById(R.id.text_places_name);
        txtDetail = (TextView) view.findViewById(R.id.text_detail);
        txtName.setText("กำลังโหลดข้อมูล");
        txtDetail.setText("กำลังโหลดข้อมูล");

        mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);

        Button btnReview = (Button) view.findViewById(R.id.button_review);
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailDialog dialog = new DetailDialog();
                dialog.show(MainActivity.mFragmentManager, "TEST123");
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //ใช้ตรวจสอบค่าและรับค่าทุกครั้งที่มีการเรียกหน้านี้
        Bundle bundle = getArguments();
        if (bundle != null) {
            placesID = bundle.getString("placesID");
            Toast.makeText(getActivity(), placesID, Toast.LENGTH_SHORT).show();
            DetailFragment.Connect connect = new Connect();
            connect.execute(placesID);
        }

        //แก้ไขปุ่ม FloatingActionButton ให้เป็นการทำงานเฉพาะหน้านั้น
        MainActivity.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailDialog dialog = new DetailDialog();
                dialog.show(MainActivity.mFragmentManager, "TEST123");
            }
        });
    }

    class Connect extends AsyncTask<String, Void, ApiPlaces> {

        @Override
        protected ApiPlaces doInBackground(String... params) {
            return MainActivity.api.places(params[0]);
        }

        @Override
        protected void onPostExecute(ApiPlaces apiPlaces) {
            super.onPostExecute(apiPlaces);
            if (apiPlaces == null) {
                Toast.makeText(getActivity(), "ผิดพลาดไม่พบสถานที่ดังกล่าว", Toast.LENGTH_LONG).show();
            } else {
                txtName.setText(apiPlaces.getPlaces_name());
                txtDetail.setText(apiPlaces.getPlaces_desc());

                if (apiPlaces.getPhotos() != null && apiPlaces.getPhotos().size() > 0) {
                    int size = apiPlaces.getPhotos().size();
                    for (int i = 0; i < size; i++) {
                        TextSliderView textSliderView = new TextSliderView(getActivity());
                        // initialize a SliderLayout
                        textSliderView
                                .image(apiPlaces.getPhotos().get(i).getUrl())
                                .setScaleType(BaseSliderView.ScaleType.CenterCrop);

                        //add your extra information
                        mDemoSlider.addSlider(textSliderView);
                    }
                }
            }
        }
    }
}
