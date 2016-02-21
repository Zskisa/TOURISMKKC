package com.zskisa.tourismkkc;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.zskisa.tourismkkc.apimodel.ApiLogin;
import com.zskisa.tourismkkc.apimodel.ApiRegisterPlaces;
import com.zskisa.tourismkkc.apimodel.ApiStatus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddPlaceFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    protected static final int GALLERY_PICTURE = 1;
    EditText editTextName, editTextDetail;
    ImageView imgView;
    Button btnAdd;
    private Spinner spinner;
    private View view;
    private String path = "";
    private String mime = "";
    private String gpsMapLat = "16.42866500";
    private String gpsMapLong = "102.82711730";
    List<String> categories;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_place, container, false);

        // EditText element
        editTextName = (EditText) view.findViewById(R.id.edt_place_name);
        editTextDetail = (EditText) view.findViewById(R.id.edt_place_detail);
        imgView = (ImageView) view.findViewById(R.id.fap_img);
        btnAdd = (Button) view.findViewById(R.id.fap_btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sName = editTextName.getText().toString();
                String sDetail = editTextDetail.getText().toString();
                if (!sName.isEmpty() && !path.isEmpty()) {
                    try {
                        Toast.makeText(getActivity(), "Name: " + sName + "\nDetail: " + sDetail, Toast.LENGTH_LONG).show();

                        ApiLogin login = new ApiLogin("test@test.com", "1234");
                        ApiRegisterPlaces registerPlaces = new ApiRegisterPlaces();
                        registerPlaces.setApiLogin(login);
                        registerPlaces.setPlaces_name(sName);
                        registerPlaces.setPlaces_desc(sDetail);
                        registerPlaces.setType_detail_id("06");
                        registerPlaces.setLocation_lat(gpsMapLat);
                        registerPlaces.setLocation_lng(gpsMapLong);
                        registerPlaces.setFiles(path);
                        registerPlaces.setMime(mime);

                        AddPlaceFragment.Connect connect = new Connect();
                        connect.execute(registerPlaces);

                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "กรอกข้อมูลให้ครบ", Toast.LENGTH_LONG).show();
                }
            }
        });

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
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), GALLERY_PICTURE);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getActivity(), "In", Toast.LENGTH_LONG).show();
        if (requestCode == GALLERY_PICTURE && resultCode == -1 && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                path = getRealPathFromURI_API19(getActivity(), uri);
                imgView.setImageURI(Uri.parse(new File(path).toString()));
                mime = getActivity().getContentResolver().getType(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getRealPathFromURI_API19(Context context, Uri uri) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Images.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{id}, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
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

    class Connect extends AsyncTask<ApiRegisterPlaces, Void, ApiStatus> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*
            * สร้าง dialog popup ขึ้นมาแสดงตอนกำลัง login เข้าระบบเป็นเวลา 3 วินาที
            */
            progressDialog = new ProgressDialog(getActivity(),
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Uploading...");
            progressDialog.show();
        }

        @Override
        protected ApiStatus doInBackground(ApiRegisterPlaces... params) {
            return MainActivity.api.registerPlaces(params[0]);
        }

        @Override
        protected void onPostExecute(ApiStatus apiStatus) {
            super.onPostExecute(apiStatus);
            progressDialog.dismiss();
            if (apiStatus.getStatus().equalsIgnoreCase("success")) {
                Toast.makeText(getActivity(), "เพิ่มสำเร็จ", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "ผิดพลาด", Toast.LENGTH_LONG).show();
            }
        }
    }
}
