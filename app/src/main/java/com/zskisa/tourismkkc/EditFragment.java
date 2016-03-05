package com.zskisa.tourismkkc;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

public class EditFragment extends Fragment {

    private View view;
    private CheckBox chk01, chk02, chk03, chk04, chk05, chk06, chk07, chk08, chk09, chk10, chk11, chk12,
            chk13, chk14, chk15, chk16, chk17, chk18, chk19, chk20, chk21, chk22, chk23, chk24;
    private Button button;
    private ArrayList<String> id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit, container, false);

        //ซ่อน FloatingButton
        if (MainActivity.floatingActionButton.isShown()) {
            MainActivity.floatingActionButton.hide();
        }

        //เชื่อมปุ่มต่างๆ
        initial(view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = new ArrayList<String>();
                if (chk01.isChecked()) {
                    id.add("01");
                }
                if (chk02.isChecked()) {
                    id.add("02");
                }
                if (chk03.isChecked()) {
                    id.add("03");
                }
                if (chk04.isChecked()) {
                    id.add("04");
                }
                if (chk05.isChecked()) {
                    id.add("05");
                }
                if (chk06.isChecked()) {
                    id.add("06");
                }
                if (chk07.isChecked()) {
                    id.add("07");
                }
                if (chk08.isChecked()) {
                    id.add("08");
                }
                if (chk09.isChecked()) {
                    id.add("09");
                }
                if (chk10.isChecked()) {
                    id.add("10");
                }
                if (chk11.isChecked()) {
                    id.add("11");
                }
                if (chk12.isChecked()) {
                    id.add("12");
                }
                if (chk13.isChecked()) {
                    id.add("13");
                }
                if (chk14.isChecked()) {
                    id.add("14");
                }
                if (chk15.isChecked()) {
                    id.add("15");
                }
                if (chk16.isChecked()) {
                    id.add("16");
                }
                if (chk17.isChecked()) {
                    id.add("17");
                }
                if (chk18.isChecked()) {
                    id.add("18");
                }
                if (chk19.isChecked()) {
                    id.add("19");
                }
                if (chk20.isChecked()) {
                    id.add("20");
                }
                if (chk21.isChecked()) {
                    id.add("21");
                }
                if (chk22.isChecked()) {
                    id.add("22");
                }
                if (chk23.isChecked()) {
                    id.add("23");
                }
                if (chk24.isChecked()) {
                    id.add("24");
                }
                Toast.makeText(getActivity(), id.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void initial(View v) {
        chk01 = (CheckBox) v.findViewById(R.id.chk01);
        chk02 = (CheckBox) v.findViewById(R.id.chk02);
        chk03 = (CheckBox) v.findViewById(R.id.chk03);
        chk04 = (CheckBox) v.findViewById(R.id.chk04);
        chk05 = (CheckBox) v.findViewById(R.id.chk05);
        chk06 = (CheckBox) v.findViewById(R.id.chk06);
        chk07 = (CheckBox) v.findViewById(R.id.chk07);
        chk08 = (CheckBox) v.findViewById(R.id.chk08);
        chk09 = (CheckBox) v.findViewById(R.id.chk09);
        chk10 = (CheckBox) v.findViewById(R.id.chk10);
        chk11 = (CheckBox) v.findViewById(R.id.chk11);
        chk12 = (CheckBox) v.findViewById(R.id.chk12);
        chk13 = (CheckBox) v.findViewById(R.id.chk13);
        chk14 = (CheckBox) v.findViewById(R.id.chk14);
        chk15 = (CheckBox) v.findViewById(R.id.chk15);
        chk16 = (CheckBox) v.findViewById(R.id.chk16);
        chk17 = (CheckBox) v.findViewById(R.id.chk17);
        chk18 = (CheckBox) v.findViewById(R.id.chk18);
        chk19 = (CheckBox) v.findViewById(R.id.chk19);
        chk20 = (CheckBox) v.findViewById(R.id.chk20);
        chk21 = (CheckBox) v.findViewById(R.id.chk21);
        chk22 = (CheckBox) v.findViewById(R.id.chk22);
        chk23 = (CheckBox) v.findViewById(R.id.chk23);
        chk24 = (CheckBox) v.findViewById(R.id.chk24);
        button = (Button) v.findViewById(R.id.fe_btn_updates);
    }
}
