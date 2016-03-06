package com.zskisa.tourismkkc;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.zskisa.tourismkkc.apimodel.ApiFeed;
import com.zskisa.tourismkkc.apimodel.ApiFeedNearRequest;
import com.zskisa.tourismkkc.apimodel.ApiLogin;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {
    /*
    * สร้าง GoogleApiClient ไว้เรียกใช้งาน GoogleService
    * เช่น LocationServices เพื่อให้ได้ตำแหน่งที่อยู่ปัจจุบัน
     */
    public static GoogleApiClient googleApiClient;

    /*
    * สร้างตัวแปร Location ไว้ให้หน้าอื่นเรียกใช้งาน
     */
    public static Location location;

    /*
    * สร้างตัวเชื่อม ApiConnect เพื่อให้แต่ละหน้าเรียกใช้ได้สะดวก
     */
    public static ApiConnect api = new ApiConnect();
    public static ApiLogin login;

    /*
    * สร้าง FragmentManager ไว้ให้หน้าอื่นใช้เมื่อการจากใช้งาน
     */
    public static FragmentManager mFragmentManager;

    /*
    * สร้าง FloatingActionButton ไว้ให้หน้าอื่นใช้งาน
    * เพื่อใช้ในกรณีต้องการซ่อน หรือ เปลี่ยนการทำงาน
     */
    public static FloatingActionButton floatingActionButton;

    private SharedPreferences sp;

    private GoogleMap mGoogleMap;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.isInitialized();
        // Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.

        /*
        * เริ่มต้นด้วยการตรวจข้อมูลว่ามีการเข้าระบบหรือยัง?
        * หากไม่มีการเข้าระบบจะเรียกหน้า LoginActivity ขึ้นมาเพื่อให้ผู้ใช้เข้าระบบ
        */
        String PREF_APP = "PREF_APP";
        sp = getSharedPreferences(PREF_APP, MODE_PRIVATE);

        String fb_id = sp.getString("title_profile", "");
        String fb_email = sp.getString("title_email", "");
        String fb_name = sp.getString("title_name", "");

        boolean cLogin = sp.getBoolean("LOGIN", false);

        if (!cLogin) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePage(new AddPlaceFragment());
            }
        });

        /*
        * ดึงข้อมูลมาเตรียมไว้แสดงผล
        * */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View view = navigationView.inflateHeaderView(R.layout.nav_header_main);

        CircleImageView nav_profile = (CircleImageView) view.findViewById(R.id.nav_profile);
        TextView nav_name = (TextView) view.findViewById(R.id.nav_name);
        TextView nav_email = (TextView) view.findViewById(R.id.nav_email);

        /*
        * สร้าง link ที่ดึง id facebook มาทำเป็นที่อยู่ภาพ
        * */
        String sUID = "https://graph.facebook.com/" + fb_id + "/picture?type=large";

        /*
        * ดึงรูปจาก facebook ของคนที่ login†
        * */
        Picasso.with(getApplicationContext())
                .load(sUID)
                .placeholder(R.drawable.com_facebook_profile_picture_blank_portrait)
                .error(R.drawable.ic_menu_gallery)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(nav_profile);

        /*
        * ดึงชื่อและอีเมล์จากหน้า login มาแสดงผลในเมนูทางซ้าย
        * */
        nav_name.setText(fb_name);
        nav_email.setText(fb_email);

        navigationView.setNavigationItemSelectedListener(this);

        /*
        * สร้างเมนู slide ทางซ้าย
        * */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //เรียกใช้งาน GoogleApiClient
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();

        //สร้าง ApiLogin ไว้ให้หน้าอื่นเรียกใช้
        String userEmail = sp.getString("userEmail", "");
        String userPassword = sp.getString("userPassword", "");
        String userFname = sp.getString("userFname", "");
        String userLname = sp.getString("userLname", "");
        String fbID = sp.getString("fbID", "");
        if (!userEmail.isEmpty() && !userPassword.isEmpty()) {
            login = new ApiLogin(userEmail, userPassword);
        } else if (!userEmail.isEmpty() && !fbID.isEmpty()) {
            login = new ApiLogin(userEmail);
            login.setFbID(fbID);
        }
        if (login != null) {
            login.setUserFname(userFname);
            login.setUserLname(userLname);

            mFragmentManager = getFragmentManager();
            changePage(new FeedFragment());
        } else {
            Toast.makeText(getApplication(), "ผิดพลาด กรุณาเข้าระบบใหม่", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    /*
    * ตรวจสอบว่าปุ่มกลับของ android ถูกกดตอนไหน
    * หากมีการเรียกใช้งานเมนูทางซ้ายจะกดปิดไปก่อน
    * หากไม่มีการเรียกใช้เมนูทางซ้ายจะออกจากตัวแอปทันที
    */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            /*
            * popup เตือนว่าต้องการออกจากแอปจริงหรือไม่
            * */
            new AlertDialog.Builder(this)
                    .setTitle(R.string.ad_title)
                    .setMessage(R.string.ad_message)
                    .setNegativeButton(R.string.ad_negativeButton, null)
                    .setPositiveButton(R.string.ad_positiveButton, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.super.onBackPressed();
                        }
                    }).create().show();
        }
    }

    @SuppressLint("CommitPrefEdits")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*
        * รายชื่อเมนูทางด้านซ้าย
        * */
        if (id == R.id.menu_home) {
            changePage(new FeedFragment());
        } else if (id == R.id.menu_search) {
            changePage(new SearchFragment());
        } else if (id == R.id.menu_map) {
            //ซ่อน FloatingButton
            if (floatingActionButton.isShown()) {
                floatingActionButton.hide();
            }

            /*
            * ไม่ต้องสร้างหน้าใหม่เพื่อรองรับหน้า maps
            * */
            MapFragment mapFragment = MapFragment.newInstance();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_content, mapFragment);
            transaction.commit();
            mapFragment.getMapAsync(this);
        } else if (id == R.id.menu_edit) {
            changePage(new EditFragment());
        } else if (id == R.id.menu_about) {
            changePage(new AboutFragment());
        } else if (id == R.id.menu_logout) {

            /*
            * ออกจากระบบแล้วไปยังหน้า login
            * */
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.commit();
            LoginManager.getInstance().logOut();

            /*
            * refresh activity เพื่อเคลียร์ข้อมูลก่อน login ใหม่
            * */
            finish();
            startActivity(getIntent());

            /*
            * เสร็จขบวนการทั้งหมดแล้วเปลี่ยนไปยังหน้า login
            * */
            startActivity(new Intent(this, LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
    * method เปลี่ยนหน้าของ slide ทางซ้าย
    * */
    private void changePage(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_content, fragment);
        transaction.commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        /*
        * ตรวจสอบว่ามีการเปิดใช้งาน gps หรือยัง
        * */
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        ApiFeedNearRequest apiFeedNearRequest = new ApiFeedNearRequest();
        apiFeedNearRequest.setApiLogin(MainActivity.login);
        if (MainActivity.location == null) {
            apiFeedNearRequest.setLocationLat(String.valueOf(16.4739857));
            apiFeedNearRequest.setLocationLng(String.valueOf(102.8215003));
        } else {
            apiFeedNearRequest.setLocationLat(String.valueOf(MainActivity.location.getLatitude()));
            apiFeedNearRequest.setLocationLng(String.valueOf(MainActivity.location.getLongitude()));
        }

        MainActivity.Connect connect = new Connect();
        connect.execute(apiFeedNearRequest);

        mGoogleMap = googleMap;

        googleMap.setMyLocationEnabled(true);
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);
        mGoogleMap.animateCamera(cameraUpdate);

        //วาดเส้นรัศมี
        Circle circle = mGoogleMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(Double.parseDouble(apiFeedNearRequest.getDistance()))
                .strokeColor(Color.BLUE)
                .fillColor(Color.rgb(200,230,255)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null && !googleApiClient.isConnected()) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(googleApiClient);
        if (locationAvailability.isLocationAvailable()) {
            /*
            *  สร้าง request เพื่อร้องขอตำแหน่ง GPS โดยมีค่าที่กำหนดไว้ดังนี้
            *   - อัพเดทตำแหน่งทุกๆ 5 วินาที ที่เปิดแอพอยู่
            *   - ในกรณีที่อ่านค่าเร็วได้กำหนดไว้ที่ 2 วินาที(กรณีมีตำแหน่งเก่าอยู่แล้วและเป็นตำแหน่งเดียวกัน)
            *   - อัพเดทแค่ 5 ครั้ง เงือนจากจะเปลืองแบต
             */
            LocationRequest request = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setNumUpdates(5)
                    .setInterval(5000)
                    .setFastestInterval(2000);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, request, this);
        } else {
            //ตรวจสอบการเปิด GPS สำหรับอีกแบบทีไม่ใช่ดึงจาก Google Api
            LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!enabled) {
                //กรณีอุปกรณืไม่ได้เปิด GPS ไว้จะบังคับให้ไปหน้าตั้งค่า เพื่อเปิดการใช้งาน GPS ก่อน
                Toast.makeText(getApplicationContext(), "กรุณาเปิดการทำงาน ระบบระบุตำแหน่ง", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
//        Toast.makeText(getApplicationContext(), "Latitude : " + location.getLatitude() + "\n"
//                + "Longitude : " + location.getLongitude(), Toast.LENGTH_SHORT).show();
    }

    class Connect extends AsyncTask<ApiFeedNearRequest, Void, ApiFeed> {

        @Override
        protected ApiFeed doInBackground(ApiFeedNearRequest... params) {
            return MainActivity.api.feedNear(params[0]);
        }

        @Override
        protected void onPostExecute(ApiFeed apiFeed) {
            super.onPostExecute(apiFeed);

            if (apiFeed.getStatus().equalsIgnoreCase("success")) {
                Toast.makeText(getApplication(), "โหลดสำเร็จ", Toast.LENGTH_LONG).show();

                if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Toast.makeText(getApplication(), apiFeed.getData().getReason(), Toast.LENGTH_LONG).show();
                for (int i = 0; i < apiFeed.getData().getResult().size(); i++) {
                    mGoogleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(apiFeed.getData().getResult().get(i).getLocation_lat()), Double.parseDouble(apiFeed.getData().getResult().get(i).getLocation_lng())))
                            .title(apiFeed.getData().getResult().get(i).getPlaces_name())
                            .snippet(apiFeed.getData().getResult().get(i).getPlaces_desc())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                }
            } else {
                Toast.makeText(getApplication(), "ผิดพลาด", Toast.LENGTH_LONG).show();
            }
        }
    }
}
