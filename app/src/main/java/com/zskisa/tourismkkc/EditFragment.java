package com.zskisa.tourismkkc;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class EditFragment extends Fragment {

    private View view;
    private Button chk01, chk02, chk03, chk04, chk05, chk06, chk07, chk08, chk09, chk10, chk11, chk12,
            chk13, chk14, chk15, chk16, chk17, chk18, chk19, chk20, chk21, chk22, chk23, chk24;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit, container, false);

        //ซ่อน FloatingButton
        if (MainActivity.floatingActionButton.isShown()) {
            MainActivity.floatingActionButton.hide();
        }

        //เชื่อมปุ่มต่างๆ
        initial(view);

        return view;
    }

    private void initial(View v) {
        chk01 = (Button) v.findViewById(R.id.chk01);
        chk02 = (Button) v.findViewById(R.id.chk02);
        chk03 = (Button) v.findViewById(R.id.chk03);
        chk04 = (Button) v.findViewById(R.id.chk04);
        chk05 = (Button) v.findViewById(R.id.chk05);
        chk06 = (Button) v.findViewById(R.id.chk06);
        chk07 = (Button) v.findViewById(R.id.chk07);
        chk08 = (Button) v.findViewById(R.id.chk08);
        chk09 = (Button) v.findViewById(R.id.chk09);
        chk10 = (Button) v.findViewById(R.id.chk10);
        chk11 = (Button) v.findViewById(R.id.chk11);
        chk12 = (Button) v.findViewById(R.id.chk12);
        chk13 = (Button) v.findViewById(R.id.chk13);
        chk14 = (Button) v.findViewById(R.id.chk14);
        chk15 = (Button) v.findViewById(R.id.chk15);
        chk16 = (Button) v.findViewById(R.id.chk16);
        chk17 = (Button) v.findViewById(R.id.chk17);
        chk18 = (Button) v.findViewById(R.id.chk18);
        chk19 = (Button) v.findViewById(R.id.chk19);
        chk20 = (Button) v.findViewById(R.id.chk20);
        chk21 = (Button) v.findViewById(R.id.chk21);
        chk22 = (Button) v.findViewById(R.id.chk22);
        chk23 = (Button) v.findViewById(R.id.chk23);
        chk24 = (Button) v.findViewById(R.id.chk24);
    }
}
