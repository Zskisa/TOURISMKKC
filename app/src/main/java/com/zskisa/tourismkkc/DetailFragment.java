package com.zskisa.tourismkkc;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.zskisa.tourismkkc.apimodel.ApiPlaces;

public class DetailFragment extends Fragment {
    private View view;
    private String placesID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail, container, false);
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
            }
        }
    }
}
