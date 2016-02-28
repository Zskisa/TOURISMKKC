package com.zskisa.tourismkkc;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zskisa.tourismkkc.apimodel.ApiFeed;
import com.zskisa.tourismkkc.apimodel.ApiLogin;
import com.zskisa.tourismkkc.apimodel.FeedAdapter;

import java.util.List;

public class FeedFragment extends Fragment {

    private View view;
    private RecyclerView rv;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_main, container, false);

        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        FeedFragment.Connect connect = new Connect();
        connect.execute(MainActivity.login);

        // find the layout
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        // the refresh listner. this would be called when the layout is pulled down
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO : request data here
                FeedFragment.Connect connect = new Connect();
                connect.execute(MainActivity.login);
            }
        });
        // sets the colors used in the refresh animation
        swipeRefreshLayout.setColorSchemeResources(R.color.primary_dark, R.color.primary,
                R.color.cardview_dark_background, R.color.cardview_light_background);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //แสดงปุ่ม FloatingActionButton ทุกครั้งที่เข้าหน้านี้
        if (!MainActivity.floatingActionButton.isShown()) {
            MainActivity.floatingActionButton.show();
        }
        MainActivity.floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_add));
        MainActivity.floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.primary)));
        MainActivity.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_content, new AddPlaceFragment());
                transaction.commit();

            }
        });
    }

    class Connect extends AsyncTask<ApiLogin, Void, ApiFeed> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*
            * สร้าง dialog popup ขึ้นมาแสดงว่ากำลังทำงานอยู่่
            */
            progressDialog = new ProgressDialog(getActivity(),
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected ApiFeed doInBackground(ApiLogin... params) {
            return MainActivity.api.feed(params[0]);
        }

        @Override
        protected void onPostExecute(ApiFeed apiFeed) {
            super.onPostExecute(apiFeed);
            progressDialog.dismiss();
            if (apiFeed != null && apiFeed.getData().getResult() != null && apiFeed.getData().getResult().size() != 0) {
                FeedAdapter adapter = new FeedAdapter(apiFeed.getData().getResult());
                rv.setAdapter(adapter);
            } else {
                Toast.makeText(getActivity(), "ผิดพลาด", Toast.LENGTH_LONG).show();
            }

            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }
}
