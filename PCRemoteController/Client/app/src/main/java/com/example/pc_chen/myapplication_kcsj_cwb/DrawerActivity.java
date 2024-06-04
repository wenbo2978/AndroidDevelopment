package com.example.pc_chen.myapplication_kcsj_cwb;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction transaction;
    ArrayList<Fragment> fragmentList;
    Fragment fragment;
    boolean f1,f2,f3 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initData();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /***********************************************************/
        Resources resource=getBaseContext().getResources();
        ColorStateList csl= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            csl = (ColorStateList)resource.getColorStateList(R.color.colorAccent,this.getTheme());
        }else{
            csl=resource.getColorStateList(R.color.colorAccent);
        }
        navigationView.setItemTextColor(csl);

        /******************************************************************/

    }

    private void initData(){
        fragmentList = new ArrayList<>();
        fragmentList.add(new MouseFragment());
        fragmentList.add(new MainFragment());
        fragmentList.add(new HotKeyFileFragment());



    }

    private void hideAllFragments(FragmentTransaction fragmentTransaction){
        for(int i = 0;i <fragmentList.size();i++){
            Fragment fragment = fragmentList.get(i);
            fragmentTransaction.hide(fragment);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        transaction = getSupportFragmentManager().beginTransaction();
        switch (id){
            case R.id.me_hot:
                Log.d("fg","1111111");
                if(f1 == false){
                    transaction.add(R.id.frameLayout,fragmentList.get(2));
                    f1 = true;
                }
                hideAllFragments(transaction);
                transaction.show(fragmentList.get(2));
                transaction.commit();
                break;
            case R.id.me_hot_conn:
                Log.d("fg","222222");

                if(f2 == false){
                    transaction.add(R.id.frameLayout,fragmentList.get(0));
                    f2 = true;
                }

                hideAllFragments(transaction);
                transaction.show(fragmentList.get(0));
                transaction.commit();
                break;
            case R.id.me_main:
                Log.d("fg","333333");

                    //fragment = new MainFragment();
                if(f3 == false){
                    transaction.add(R.id.frameLayout,fragmentList.get(1));
                    f3 = true;
                }

                hideAllFragments(transaction);
                transaction.show(fragmentList.get(1));
                transaction.commit();
                break;
            default:
                break;
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
