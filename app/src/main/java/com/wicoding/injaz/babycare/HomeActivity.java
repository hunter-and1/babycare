package com.wicoding.injaz.babycare;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    Toolbar toolbar;
    CircleImageView circleImageView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        circleImageView = (CircleImageView)navigationView.getHeaderView(0).findViewById(R.id.profile_image);

        navigationView.setNavigationItemSelectedListener(this);

        Bundle b = getIntent().getExtras();

        refrechData();
        if(b.getString("fragment").equals("setting")){
            GlobalApp.setLogOutSharedPreferences(getApplicationContext());
            Fragment fragmentFirst = new FragmentSetting();
            navigationView.getMenu().getItem(4).setChecked(true);
            toolbar.setVisibility(View.INVISIBLE);
            this.setTitle(R.string.menu_settings);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragmentFirst).commit();
        }
        else if(b.getString("fragment").equals("main"))
        {
            Fragment fragmentFirst = new FragmentMain();
            navigationView.getMenu().getItem(0).setChecked(true);
            this.setTitle(R.string.menu_mybaby);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragmentFirst).commit();
        }
        else
         finish();


    }

    public void navigationHide(int viw)
    {
        toolbar.setVisibility(viw);
    }
    public void refrechData()
    {
        try{
            circleImageView.setImageBitmap(GlobalApp.dataUser.get_Img());
        }
        catch (Exception e){Log.d("err",e.getMessage());}
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    Fragment fragment = null;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.meun_homepage) {
            //ZGlobalVar.setLogOutSharedPreferences(getApplicationContext());
           // Intent mainIntent = new Intent(this,LoginActivity.class);
            //startActivity(mainIntent);
            //finish();
            fragment = new FragmentMain();
            this.setTitle(R.string.menu_mybaby);
        }
        else if (id == R.id.meun_graphs) {
            fragment = new FragmentGraph();
            this.setTitle(R.string.menu_graphs);
        }
        else if (id == R.id.meun_setting) {
            fragment = new FragmentSetting();
            this.setTitle(R.string.menu_settings);
        }
        else if(id == R.id.meun_aboutas){
            fragment = new FragmentAboutAs();
            this.setTitle(R.string.menu_aboutas);
        }
        else if(id == R.id.meun_quicktips){
            fragment = new FragmentQuickTip();
            this.setTitle(R.string.menu_quicktips);
        }
        else if(id == R.id.meun_howitwork )
        {
            //fragment = new FragmentQuickTip();
            this.setTitle(R.string.menu_howitworks);
        }
        else if (id == R.id.meun_places) {
            if(GlobalApp.isNetworkAvailable(getApplicationContext())){
                fragment = new FragmentPlaces();
                this.setTitle(R.string.meun_places);
            }
            else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Internet!");
                alertDialogBuilder.setMessage("Internet connection failed.");
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }
        if(null!=fragment)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
