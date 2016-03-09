package com.zskisa.tourismkkc;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zskisa.tourismkkc.apimodel.ApiFeedReview;
import com.zskisa.tourismkkc.apimodel.ApiPlaces;
import com.zskisa.tourismkkc.apimodel.ReviewAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {
    private String placesID;
    private SliderLayout mDemoSlider;
    private TextView txtName, txtDetail;
    private RecyclerView rcvReview;
    private LinearLayoutManager llm;
    private ReviewAdapter adapter;
    private List<ApiFeedReview.DataEntity.ResultEntity> reviews;
    private boolean loading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    private int num_review = 1;
    private int review_plus = 5;
    private CallbackManager callbackManager;
    private View view;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity());
        callbackManager = CallbackManager.Factory.create();
        view = inflater.inflate(R.layout.fragment_detail, container, false);

        txtName = (TextView) view.findViewById(R.id.text_places_name);
        txtDetail = (TextView) view.findViewById(R.id.text_detail);
        txtName.setText("กำลังโหลดข้อมูล");
        txtDetail.setText("กำลังโหลดข้อมูล");

        mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);

        rcvReview = (RecyclerView) view.findViewById(R.id.recycler_review);
        rcvReview.setHasFixedSize(true);

        llm = new LinearLayoutManager(getActivity());
        llm.setAutoMeasureEnabled(true);
        rcvReview.setLayoutManager(llm);

        rcvReview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = llm.getChildCount();
                    totalItemCount = llm.getItemCount();
                    pastVisibleItems = llm.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            /*
                            * Toast.makeText(getActivity(), "Load more review", Toast.LENGTH_SHORT).show();
                            * โหลดรีวิวสถานที่
                            * execute(รหัสสถานที่,รีวิวเริ่มต้น,รีวิวสุดท้าย);
                            * */
                            DetailFragment.ConnectReview connectReview = new ConnectReview();
                            connectReview.execute(placesID, String.valueOf(num_review), String.valueOf(num_review + review_plus));
                            num_review = num_review + review_plus;
                        }
                    }
                }
            }
        });

        MapView mMapView = (MapView) view.findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(savedInstanceState);

            mMapView.onResume(); // needed to get the map to display immediately

            googleMap = mMapView.getMap();
            // latitude and longitude
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return view;
            }
            googleMap.setMyLocationEnabled(true);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        /*
        * ใช้ตรวจสอบค่าและรับค่าทุกครั้งที่มีการเรียกหน้านี้
        * */
        Bundle bundle = getArguments();
        if (bundle != null) {
            placesID = bundle.getString("placesID");
            /*
            * Toast.makeText(getActivity(), placesID, Toast.LENGTH_SHORT).show();
            * โหลดข้อมูลสถานที่
            * */
            DetailFragment.Connect connect = new Connect();
            connect.execute(placesID);
            /*
            * โหลดรีวิวสถานที่
            * execute(รหัสสถานที่,รีวิวเริ่มต้น,รีวิวสุดท้าย);
            * */
            DetailFragment.ConnectReview connectReview = new ConnectReview();
            connectReview.execute(placesID, String.valueOf(num_review), String.valueOf(num_review + review_plus));
            num_review = num_review + review_plus;
        }

        /*
        * แก้ไขปุ่ม FloatingActionButton ให้เป็นการทำงานเฉพาะหน้านั้น
        * */
        MainActivity.floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_review));
        MainActivity.floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.com_facebook_blue)));
        MainActivity.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailDialog dialog = new DetailDialog();
                dialog.places_id = placesID;
                dialog.show(MainActivity.mFragmentManager, "TEST123");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(getActivity());
    }

    class Connect extends AsyncTask<String, Void, ApiPlaces> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /*
            * สร้าง dialog popup ขึ้นมาแสดงว่ากำลังทำงานอยู่่
            * */
            progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            /*
            * ล้างค่า Slide เก่าออกกรณีโหลดใหม่
            * */
            mDemoSlider.removeAllSliders();
        }

        @Override
        protected ApiPlaces doInBackground(String... params) {
            return MainActivity.api.places(params[0]);
        }

        @Override
        protected void onPostExecute(ApiPlaces apiPlaces) {
            super.onPostExecute(apiPlaces);
            progressDialog.dismiss();
            if (apiPlaces == null) {
                Toast.makeText(getActivity(), "ผิดพลาดไม่พบสถานที่ดังกล่าว", Toast.LENGTH_LONG).show();
            } else {
                txtName.setText(apiPlaces.getPlaces_name());
                txtDetail.setText(apiPlaces.getPlaces_desc());

                //Point map and zoom
                // create marker
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(Double.parseDouble(apiPlaces.getLocation_lat()), Double.parseDouble(apiPlaces.getLocation_lng())))
                        .title(apiPlaces.getPlaces_name());

                // Changing marker icon
                marker.icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

                // adding marker
                googleMap.addMarker(marker);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(Double.parseDouble(apiPlaces.getLocation_lat()), Double.parseDouble(apiPlaces.getLocation_lng()))).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));

                ShareLinkContent content;
                String url = "http://www.tourism-kkc.com/main/places/";

                if (apiPlaces.getPhotos() != null && apiPlaces.getPhotos().size() > 0) {

                    content = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse(url + placesID))
                            .setContentTitle(apiPlaces.getPlaces_name())
                            .setImageUrl(Uri.parse(apiPlaces.getPhotos().get(0).getUrl()))
                            .setContentDescription(apiPlaces.getPlaces_desc())
                            .build();

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
                } else {
                    content = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse(url + placesID))
                            .setContentTitle(apiPlaces.getPlaces_name())
                            .setContentDescription(apiPlaces.getPlaces_desc())
                            .build();
                }
                ShareButton shareButton = (ShareButton) view.findViewById(R.id.fb_share);
                shareButton.setShareContent(content);
            }
        }
    }

    class ConnectReview extends AsyncTask<String, Void, ApiFeedReview> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (reviews == null) {
                reviews = new ArrayList<ApiFeedReview.DataEntity.ResultEntity>();
            }
        }

        @Override
        protected ApiFeedReview doInBackground(String... params) {
            return MainActivity.api.review(params[0], params[1], params[2]);
        }

        @Override
        protected void onPostExecute(ApiFeedReview apiFeedReview) {
            super.onPostExecute(apiFeedReview);
            if (apiFeedReview != null) {
                if (apiFeedReview.getData().getResult().size() > 0) {
                    for (ApiFeedReview.DataEntity.ResultEntity temp : apiFeedReview.getData().getResult()) {
                        reviews.add(temp);
                    }
                    if (rcvReview.getAdapter() == null) {
                        adapter = new ReviewAdapter(reviews);
                        rcvReview.setAdapter(adapter);
                    } else {
                        /*
                        * สั่งให้ adapter อัพเดทข้อมูล
                        * */
                        adapter.notifyDataSetChanged();
                    }
                }
            } else {
                Toast.makeText(getActivity(), "ผิดพลาด", Toast.LENGTH_LONG).show();
            }
            loading = true;
        }
    }
}
