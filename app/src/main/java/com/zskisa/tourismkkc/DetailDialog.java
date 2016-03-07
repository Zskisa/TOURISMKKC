package com.zskisa.tourismkkc;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.zskisa.tourismkkc.apimodel.ApiReview;
import com.zskisa.tourismkkc.apimodel.ApiStatus;

import java.io.File;

public class DetailDialog extends DialogFragment implements View.OnClickListener {

    RatingBar ratingBar;
    EditText txtReview;
    ImageView imageView;
    Button btnOk, btnCancel, btnImage;

    public String places_id = "";
    protected static final int GALLERY_PICTURE = 1;
    private ApiReview apiReview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_detail, container, false);
        //เตรียม ApiReview
        apiReview = new ApiReview();

        /*
        * เชื่อมปุ่มต่างๆ
        * */
        ratingBar = (RatingBar) view.findViewById(R.id.ddRatingBar);
        txtReview = (EditText) view.findViewById(R.id.ddTxtReview);
        imageView = (ImageView) view.findViewById(R.id.ddImage);
        btnOk = (Button) view.findViewById(R.id.ddBtnOk);
        btnOk.setOnClickListener(this);
        btnCancel = (Button) view.findViewById(R.id.ddBtnCancel);
        btnCancel.setOnClickListener(this);
        btnImage = (Button) view.findViewById(R.id.ddBtnImage);
        btnImage.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICTURE && resultCode == -1 && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                apiReview.setFiles(getRealPathFromURI_API19(getActivity(), uri));
                imageView.setImageURI(Uri.parse(new File(apiReview.getFiles()).toString()));
                apiReview.setMime(getActivity().getContentResolver().getType(uri));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getRealPathFromURI_API19(Context context, Uri uri) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);
        /*
        * Split at colon, use second item in the array
        * */
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Images.Media.DATA};

        /*
        * where id is equal to
        * */
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
    public void onClick(View v) {
        if (v == btnOk) {
            //เตรียมข้อมูล ApiReview
            apiReview.setPlaces_id(places_id);
            apiReview.setRate_value(String.valueOf(ratingBar.getRating()));
            apiReview.setReview_detail(txtReview.getText().toString());
            apiReview.setApiLogin(MainActivity.login);
            /*
            * ส่งค่าให้ฝั่งเซิร์ฟเวอร์
            * */
            DetailDialog.Connect connect = new Connect();
            connect.execute(apiReview);
        } else if (v == btnCancel) {
            dismiss();
        } else if (v == btnImage) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), GALLERY_PICTURE);
        }
    }

    class Connect extends AsyncTask<ApiReview, Void, ApiStatus> {
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
        protected ApiStatus doInBackground(ApiReview... params) {
            return MainActivity.api.reviewPlaces(params[0]);
        }

        @Override
        protected void onPostExecute(ApiStatus apiStatus) {
            super.onPostExecute(apiStatus);
            progressDialog.dismiss();
            if (apiStatus != null && apiStatus.getStatus().equalsIgnoreCase("success")) {
                Toast.makeText(getActivity(), "ได้รับรีวิวของคุณแล้ว", Toast.LENGTH_LONG).show();
                dismiss();
            } else {
                Toast.makeText(getActivity(), "ผิดพลาด", Toast.LENGTH_LONG).show();
            }
        }
    }
}
