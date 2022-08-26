package com.yh.parkingpartner.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.yh.parkingpartner.R;
import com.yh.parkingpartner.config.Config;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    String accessToken;
    String name;
    String email;
    String img_profile;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private Fragment thirdFragment;
    private Fragment fourthFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.bottonNavigationView);

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();
        fourthFragment = new FourthFragment();

        //SharedPreferences 를 이용해서, 앱 내의 저장소에 영구저장된 데이터를 읽어오는 방법
        SharedPreferences sp = getApplication().getSharedPreferences(Config.SP_NAME, MODE_PRIVATE);
        accessToken = sp.getString(Config.SP_KEY_ACCESS_TOKEN, "");
        Log.i("로그", "accessToken : " + accessToken);
        name = sp.getString(Config.SP_KEY_NAME, "");
        Log.i("로그", "name : " + name);
        email = sp.getString(Config.SP_KEY_EMAIL, "");
        Log.i("로그", "email : " + email);
        img_profile = sp.getString(Config.SP_KEY_IMG_PROFILE, "");
        Log.i("로그", "img_profile : " + img_profile);

        //없으면, 로그인 액티비티를 실행
        //있으면, ?
        if(accessToken.isEmpty()){
            Intent intent=new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            transaction.replace(R.id.frameLayout, firstFragment).commitAllowingStateLoss();
        }

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                Fragment fragment = null;

                if(itemId == R.id.firstFragment){
                    fragment = firstFragment;
                    getSupportActionBar().setTitle("파킹파트너");
                    getSupportActionBar().show();
                }else if (itemId == R.id.secondFragment){
                    fragment = secondFragment;
                }else if (itemId == R.id.thirdFragment){
                    fragment = thirdFragment;
                }else if (itemId == R.id.fourthFragment) {
                    fragment = fourthFragment;
                }
                return loadFragment(fragment);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)    {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
            return true;
        }
        return false;
    }

}