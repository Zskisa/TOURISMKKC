package com.zskisa.tourismkkc;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.zskisa.tourismkkc.apimodel.ApiLogin;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    /*
    * สร้างตัวเชื่อม ApiConnect เพื่อให้แต่ละหน้าเรียกใช้ได้สะดวก
     */
    public static ApiConnect api = new ApiConnect();
    public static ApiLogin login;

    private SharedPreferences sp;

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

        FloatingActionButton actionButton = (FloatingActionButton) findViewById(R.id.fab);
        actionButton.setOnClickListener(new View.OnClickListener() {
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

        LatLng thailand = new LatLng(12.8819714, 92.4392124);

        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(thailand));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.477551, 102.8209163))
                .title("Complex KKU")
                .snippet("ดินแดนสวยงาม สว่างมากจ้า")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    }
}
