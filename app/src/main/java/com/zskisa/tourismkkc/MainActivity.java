package com.zskisa.tourismkkc;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
        String p_NAME = "App_Config";
        sp = getSharedPreferences(p_NAME, MODE_PRIVATE);

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
                .placeholder(R.drawable.ic_menu_camera)
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

        changePage(new FeedFragment());
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
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton("NO", null)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.super.onBackPressed();
                        }
                    }).create().show();
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

    @SuppressLint("CommitPrefEdits")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*
        * รายชื่อเมนูทางด้านซ้าย
        * */
        if (id == R.id.nav_home) {
            changePage(new FeedFragment());
        } else if (id == R.id.nav_search) {
            changePage(new SearchFragment());
        } else if (id == R.id.nav_map) {
            changePage(new MapFragment());
        } else if (id == R.id.nav_edit) {
            changePage(new EditFragment());
        } else if (id == R.id.nav_about) {
            changePage(new AboutFragment());
        } else if (id == R.id.nav_logout) {

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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_content, fragment);
        transaction.commit();
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
}
