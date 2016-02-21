package com.zskisa.tourismkkc;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zskisa.tourismkkc.apimodel.ApiFeed;
import com.zskisa.tourismkkc.apimodel.ApiLogin;
import com.zskisa.tourismkkc.apimodel.ApiRegister;
import com.zskisa.tourismkkc.apimodel.ApiRegisterPlaces;
import com.zskisa.tourismkkc.apimodel.ApiStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiConnect {

    private String URL = "http://www.tourism-kkc.com/api/";
    private String TAG = "DEBUG";
    private OkHttpClient okHttpClient = new OkHttpClient();

    public ApiConnect() {

    }

    public ApiStatus register(ApiRegister dataRegister) {
        RequestBody formBody = new FormBody.Builder()
                .add("user_email", dataRegister.getUserEmail())
                .add("user_password", dataRegister.getUserPassword())
                .add("user_fb_id", dataRegister.getUser_fb_id())
                .add("user_fname", dataRegister.getUserFirstName())
                .add("user_lname", dataRegister.getUserLastName())
                .build();

        Request.Builder builder = new Request.Builder();
        Request request = builder
                .url(URL + "register")
                .post(formBody)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return checkAPIStatus(response.body().string());
            } else {
                Log.d(TAG, "Not Success - code in register : " + response.code());
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "ERROR in register : " + e.getMessage());
            return null;
        }
    }

    public ApiStatus login(ApiLogin dataLogin) {
        RequestBody formBody = new FormBody.Builder()
                .add("user_email", dataLogin.getUserEmail())
                .add("user_password", dataLogin.getUserPassword())
                .add("user_fb_id", dataLogin.getFbID())
                .add("user_fname", dataLogin.getUserFname())
                .add("user_lname", dataLogin.getUserLname())
                .build();

        Request.Builder builder = new Request.Builder();
        Request request = builder
                .url(URL + "login")
                .post(formBody)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return checkAPIStatus(response.body().string());
            } else {
                Log.d(TAG, "Not Success - code in login : " + response.code());
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "ERROR in login : " + e.getMessage());
            return null;
        }
    }

    public ApiStatus registerPlaces(ApiRegisterPlaces apiRegisterPlaces) {
        String path = apiRegisterPlaces.getFiles();
        String filename = path.substring(path.lastIndexOf("/"));
        String mime = apiRegisterPlaces.getMime();

        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user_email", apiRegisterPlaces.getApiLogin().getUserEmail())
                .addFormDataPart("user_password", apiRegisterPlaces.getApiLogin().getUserPassword())
                .addFormDataPart("user_fb_id", apiRegisterPlaces.getApiLogin().getFbID())
                .addFormDataPart("user_fname", apiRegisterPlaces.getApiLogin().getUserFname())
                .addFormDataPart("user_lname", apiRegisterPlaces.getApiLogin().getUserLname())
                .addFormDataPart("location_lat", apiRegisterPlaces.getLocation_lat())
                .addFormDataPart("location_lng", apiRegisterPlaces.getLocation_lng())
                .addFormDataPart("places_name", apiRegisterPlaces.getPlaces_name())
                .addFormDataPart("places_desc", apiRegisterPlaces.getPlaces_desc())
                .addFormDataPart("type_detail_id", apiRegisterPlaces.getType_detail_id())
                .addFormDataPart("files", filename, RequestBody.create(MediaType.parse(mime), new File(path)))
                .build();

        Request.Builder builder = new Request.Builder();
        Request request = builder
                .url(URL + "register_places")
                .post(formBody)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return checkAPIStatus(response.body().string());
            } else {
                Log.d(TAG, "Not Success - code in login : " + response.code());
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "ERROR in login : " + e.getMessage());
            return null;
        }
    }

    public ApiFeed feed(ApiLogin apiLogin) {
        RequestBody formBody = new FormBody.Builder()
                .add("user_email", apiLogin.getUserEmail())
                .add("user_password", apiLogin.getUserPassword())
                .add("user_fb_id", apiLogin.getFbID())
                .add("user_fname", apiLogin.getUserFname())
                .add("user_lname", apiLogin.getUserLname())
                .build();

        Request.Builder builder = new Request.Builder();
        Request request = builder
                .url(URL + "feed")
                .post(formBody)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                Gson gson = new GsonBuilder().create();
                return gson.fromJson(response.body().string(), ApiFeed.class);
            } else {
                Log.d(TAG, "Not Success - code in feed : " + response.code());
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "ERROR in feed : " + e.getMessage());
            return null;
        }
    }

    private ApiStatus checkAPIStatus(String json_string) {
        ApiStatus apiStatus = new ApiStatus();
        try {
            JSONObject jStatus = new JSONObject(json_string);
            String status = jStatus.getString("status");
            apiStatus.setStatus(status);

            JSONObject objData = jStatus.getJSONObject("data");
            String action = objData.getString("action");
            apiStatus.setAction(action);

            String reason = objData.getString("reason");
            apiStatus.setReason(reason);

            Log.d(TAG, "STATUS:" + status);
            Log.d(TAG, "ACTION:" + action);
            Log.d(TAG, "REASON:" + reason);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "ERROR in checkAPIStatus : " + e.toString());
        }
        return apiStatus;
    }

}