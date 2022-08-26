package com.yh.parkingpartner.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yh.parkingpartner.R;
import com.yh.parkingpartner.config.Config;

public class MainActivity extends AppCompatActivity {

    String accessToken;
    String name;
    String email;
    String img_profile;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();

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
    }

    public void clickHandler(View view)
    {
        transaction = fragmentManager.beginTransaction();

        switch(view.getId())
        {
            case R.id.FirstFragment:
                transaction.replace(R.id.frameLayout, firstFragment).commitAllowingStateLoss();
                break;
            case R.id.SecondFragment:
                transaction.replace(R.id.frameLayout, secondFragment).commitAllowingStateLoss();
                break;
        }
    }
}