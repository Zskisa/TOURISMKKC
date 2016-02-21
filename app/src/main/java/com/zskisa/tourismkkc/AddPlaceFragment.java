package com.zskisa.tourismkkc;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddPlaceFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText editTextName, editTextDetail;
    private Spinner spinner;
    private View view;
    private String gpsMapLat = "12123";
    private String gpsMapLong = "12121";
    List<String> categories;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_place, container, false);

        // EditText element
        editTextName = (EditText) view.findViewById(R.id.edt_place_name);
        editTextDetail = (EditText) view.findViewById(R.id.edt_place_detail);

        // Spinner element
        spinner = (Spinner) view.findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        categories = new ArrayList<>();
        categories.add("สถานที่ท่องเที่ยวเชิงธรรมชาติ");
        categories.add("สถานที่ท่องเที่ยวเชิงวัฒนธรรม");
        categories.add("สถานที่ท่องเที่ยวเชิงประวัติศาสตร์");
        categories.add("สถานที่ท่องเที่ยวเชิงเกษตร");
        categories.add("สถานที่ท่องเที่ยวเชิงนันทนาการ");
        categories.add("อาหารไทย");
        categories.add("อาหารภาคอีสาน");
        categories.add("อาหารภาคใต้");
        categories.add("อาหารภาคเหนือ");
        categories.add("อาหารซีฟู๊ด");
        categories.add("อาหารฟาสฟู๊ด");
        categories.add("อาหารต่างชาติ");
        categories.add("ราคาต่ำกว่า 500 บาท");
        categories.add("ราคา 501 – 1000 บาท");
        categories.add("ราคา 1001 – 2000 บาท");
        categories.add("ราคามากกว่า 2000 บาท");
        categories.add("ห้างสรรพสินค้า");
        categories.add("ซุปเปอร์เซ็นเตอร์");
        categories.add("ซุปเปอร์มาร์เก็ต");
        categories.add("ร้านสะดวกซื้อ");
        categories.add("ร้านค้าปลีก");
        categories.add("สถานบริการอาบ อบ นวด");
        categories.add("สถานบันเทิงดิสโก้เธค ผับ บาร์");
        categories.add("สถานบันเทิงร้านคาราโอเกะ");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        Button button = (Button) view.findViewById(R.id.btn_place_select);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sName = editTextName.getText().toString();
                String sDetail = editTextDetail.getText().toString();
                try {
                    Toast.makeText(getActivity(), "Name: " + sName + "\nDetail: " + sDetail, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
