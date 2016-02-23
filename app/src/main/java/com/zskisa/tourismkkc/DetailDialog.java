package com.zskisa.tourismkkc;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class DetailDialog extends DialogFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_detail, container, false);

        Button button = (Button) rootView.findViewById(R.id.button_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "!!!!", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

}
