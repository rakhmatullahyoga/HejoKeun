package com.rakhmatullahyoga.hejokeun.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rakhmatullahyoga.hejokeun.R;
import com.rakhmatullahyoga.hejokeun.fragments.GiveRewardFragment;
import com.rakhmatullahyoga.hejokeun.fragments.ReportFragment;

import java.util.Random;

public class PetugasActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private SupportMapFragment sMapFragment;
    private android.app.FragmentManager fragmentManager;
    private FragmentManager sFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petugas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("SISA");
        setSupportActionBar(toolbar);

        sMapFragment = SupportMapFragment.newInstance();
        sMapFragment.getMapAsync(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sFragmentManager = getSupportFragmentManager();
        if(!sMapFragment.isAdded())
            sFragmentManager.beginTransaction().add(R.id.map, sMapFragment).commit();
        else
            sFragmentManager.beginTransaction().show(sMapFragment).commit();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.petugas, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        fragmentManager = getFragmentManager();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(sMapFragment.isAdded()) {
            sFragmentManager.beginTransaction().hide(sMapFragment).commit();
        }

        if (id == R.id.nav_petasampah) {
            if(!sMapFragment.isAdded())
                sFragmentManager.beginTransaction().add(R.id.map, sMapFragment).commit();
            else
                sFragmentManager.beginTransaction().show(sMapFragment).commit();
        }
        else if (id == R.id.nav_list) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new GiveRewardFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Enable MyLocation
        googleMap.setMyLocationEnabled(true);

        // Generate 10 random spot
        for(int i=0; i<10; i++) {
            double randomLat = -6.94; // + 0.06
            double randomLng = 107.54; // + 0.1
            float color = i%2==0 ? BitmapDescriptorFactory.HUE_RED : BitmapDescriptorFactory.HUE_GREEN;
            LatLng loc = new LatLng(randomLat+(new Random().nextDouble()*0.06),randomLng+(new Random().nextDouble()*0.1));
            googleMap.addMarker(new MarkerOptions().position(loc).icon(BitmapDescriptorFactory.defaultMarker(color)));
        }

        // Move camera to Kota Bandung
        double latitude = -6.914744;
        double longitude = 107.609810;
        LatLng myPosition = new LatLng(latitude, longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 12));
    }
}
