package com.zskisa.tourismkkc;

import android.app.Fragment;
import android.app.FragmentTransaction;
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

import com.zskisa.tourismkkc.apimodel.ApiRegisterPlaces;
import com.zskisa.tourismkkc.apimodel.ApiStatus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddPlaceFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    protected static final int GALLERY_PICTURE = 1;
    private EditText editTextName, editTextDetail;
    private ImageView imgView;
    private String TID = "";
    private List<String> typeId;
    private ApiRegisterPlaces registerPlaces;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_place, container, false);

        /*
        * ซ่อน FloatingButton
        * */
        if (MainActivity.floatingActionButton.isShown()) {
            MainActivity.floatingActionButton.hide();
        }

        /*
        * ดึงค่าตำแหน่งจากหน้า MainActivity
        * */
        registerPlaces = new ApiRegisterPlaces();
        String gpsMapLat = String.valueOf(MainActivity.location.getLatitude());
        String gpsMapLong = String.valueOf(MainActivity.location.getLongitude());
        registerPlaces.setLocation_lat(gpsMapLat);
        registerPlaces.setLocation_lng(gpsMapLong);

        editTextName = (EditText) view.findViewById(R.id.edt_place_name);
        editTextDetail = (EditText) view.findViewById(R.id.edt_place_detail);
        imgView = (ImageView) view.findViewById(R.id.fap_img);
        Button btnAdd = (Button) view.findViewById(R.id.fap_btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sName = editTextName.getText().toString();
                String sDetail = editTextDetail.getText().toString();
                if (!sName.isEmpty() && !registerPlaces.getFiles().isEmpty()) {
                    try {
                        registerPlaces.setApiLogin(MainActivity.login);
                        registerPlaces.setPlaces_name(sName);
                        registerPlaces.setPlaces_desc(sDetail);
                        registerPlaces.setType_detail_id(TID);

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
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        typeId = new ArrayList<>();
        List<String> typeDetail = new ArrayList<>();

        typeId.add("01");
        typeDetail.add("สถานที่ท่องเที่ยวเชิงธรรมชาติ");
        typeId.add("02");
        typeDetail.add("สถานที่ท่องเที่ยวเชิงวัฒนธรรม");
        typeId.add("03");
        typeDetail.add("สถานที่ท่องเที่ยวเชิงประวัติศาสตร์");
        typeId.add("04");
        typeDetail.add("สถานที่ท่องเที่ยวเชิงเกษตร");
        typeId.add("05");
        typeDetail.add("สถานที่ท่องเที่ยวเชิงนันทนาการ");
        typeId.add("06");
        typeDetail.add("อาหารไทย");
        typeId.add("07");
        typeDetail.add("อาหารภาคอีสาน");
        typeId.add("08");
        typeDetail.add("อาหารภาคใต้");
        typeId.add("09");
        typeDetail.add("อาหารภาคเหนือ");
        typeId.add("10");
        typeDetail.add("อาหารซีฟู๊ด");
        typeId.add("11");
        typeDetail.add("อาหารฟาสฟู๊ด");
        typeId.add("12");
        typeDetail.add("อาหารต่างชาติ");
        typeId.add("13");
        typeDetail.add("ราคาต่ำกว่า 500 บาท");
        typeId.add("14");
        typeDetail.add("ราคา 501 – 1000 บาท");
        typeId.add("15");
        typeDetail.add("ราคา 1001 – 2000 บาท");
        typeId.add("16");
        typeDetail.add("ราคามากกว่า 2000 บาท");
        typeId.add("17");
        typeDetail.add("ห้างสรรพสินค้า");
        typeId.add("18");
        typeDetail.add("ซุปเปอร์เซ็นเตอร์");
        typeId.add("19");
        typeDetail.add("ซุปเปอร์มาร์เก็ต");
        typeId.add("20");
        typeDetail.add("ร้านสะดวกซื้อ");
        typeId.add("21");
        typeDetail.add("ร้านค้าปลีก");
        typeId.add("22");
        typeDetail.add("สถานบริการอาบ อบ นวด");
        typeId.add("23");
        typeDetail.add("สถานบันเทิงดิสโก้เธค ผับ บาร์");
        typeId.add("24");
        typeDetail.add("สถานบันเทิงร้านคาราโอเกะ");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, typeDetail);
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
        if (requestCode == GALLERY_PICTURE && resultCode == -1 && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                String path = getRealPathFromURI_API19(getActivity(), uri);
                imgView.setImageURI(Uri.parse(new File(path).toString()));
                String mime = getActivity().getContentResolver().getType(uri);
                registerPlaces.setFiles(path);
                registerPlaces.setMime(mime);
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
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel, new String[]{id}, null);
        int columnIndex = cursor.getColumnIndex(column[0]);
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TID = typeId.get(position);
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
            progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
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
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_content, new FeedFragment());
                transaction.commit();
            } else {
                Toast.makeText(getActivity(), "ผิดพลาด", Toast.LENGTH_LONG).show();
            }
        }
    }
}
