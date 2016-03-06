package com.zskisa.tourismkkc;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zskisa.tourismkkc.apimodel.ApiFeed;
import com.zskisa.tourismkkc.apimodel.ApiFeedRequest;
import com.zskisa.tourismkkc.apimodel.FeedAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private ApiFeedRequest apiFeedRequest;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rv;
    private LinearLayoutManager llm;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean loading = true;
    private FeedAdapter adapter;
    private List<ApiFeed.DataEntity.ResultEntity> feeds;
    private Button btnSearch;
    private EditText txtSearch;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);

        //ตั้งค่าเริ่มต้น apiFeedRequest และดึงค่า login จาก MainActivity
        apiFeedRequest = new ApiFeedRequest();
        apiFeedRequest.setApiLogin(MainActivity.login);

        btnSearch = (Button) view.findViewById(R.id.fsBtnSearch);
        txtSearch = (EditText) view.findViewById(R.id.fs_txt);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feeds != null) {
                    feeds.clear();
                }
                //ล้างค่า apiFeedRequest ให้เริ่มใหม่
                apiFeedRequest.reset();
                apiFeedRequest.setLike(txtSearch.getText().toString());

                SearchFragment.Connect connect = new Connect();
                connect.execute(apiFeedRequest);
            }
        });

        rv = (RecyclerView) view.findViewById(R.id.fs_rv);
        rv.setHasFixedSize(true);

        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = llm.getChildCount();
                    totalItemCount = llm.getItemCount();
                    pastVisiblesItems = llm.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Toast.makeText(getActivity(), "Load more", Toast.LENGTH_SHORT).show();
                            //โหลดรีวิวสถานที่
                            //execute(รหัสสถานที่,รีวิวเริ่มต้น,รีวิวสุดท้าย);
                            SearchFragment.Connect connect = new Connect();
                            connect.execute(apiFeedRequest);
                        }
                    }
                }
            }
        });

        // find the layout
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fs_swipeRefreshLayout);
        // the refresh listner. this would be called when the layout is pulled down
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                feeds.clear();
                //ล้างค่า apiFeedRequest ให้เริ่มใหม่
                apiFeedRequest.reset();
                apiFeedRequest.setLike(txtSearch.getText().toString());

                SearchFragment.Connect connect = new Connect();
                connect.execute(apiFeedRequest);
            }
        });
        // sets the colors used in the refresh animation
        swipeRefreshLayout.setColorSchemeResources(R.color.primary_dark, R.color.primary,
                R.color.cardview_dark_background, R.color.cardview_light_background);

        return view;
    }

    class Connect extends AsyncTask<ApiFeedRequest, Void, ApiFeed> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!swipeRefreshLayout.isRefreshing() && loading) {
               /*
                * สร้าง dialog popup ขึ้นมาแสดงว่ากำลังทำงานอยู่่
                */
                progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
            }

            if (feeds == null) {
                feeds = new ArrayList<ApiFeed.DataEntity.ResultEntity>();
            }
        }

        @Override
        protected ApiFeed doInBackground(ApiFeedRequest... params) {
            return MainActivity.api.feed(params[0]);
        }

        @Override
        protected void onPostExecute(ApiFeed apiFeed) {
            super.onPostExecute(apiFeed);
            if (apiFeed != null) {
                if (apiFeed.getData().getResult().size() > 0) {
                    for (ApiFeed.DataEntity.ResultEntity temp : apiFeed.getData().getResult()) {
                        feeds.add(temp);
                    }
                    if (rv.getAdapter() == null) {
                        adapter = new FeedAdapter(feeds);
                        rv.setAdapter(adapter);
                    } else {
                        //สั่งให้ adapter อัพเดทข้อมูล
                        adapter.notifyDataSetChanged();
                    }
                    //อัพเดทค่า apiFeedRequest ไว้ใช้ดึงค่าครั้งต่อไป
                    apiFeedRequest.prePareNext();
                }
            } else {
                Toast.makeText(getActivity(), "ผิดพลาด", Toast.LENGTH_LONG).show();
            }

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            loading = true;
        }
    }
}
