package com.yh.parkingpartner.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yh.parkingpartner.R;

public class MypageActivity extends AppCompatActivity {

    Button btnTotal;
    Button btnWrite;
    Button btnWritten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        btnTotal = (Button) findViewById(R.id.btnTotal);
        btnWrite = (Button) findViewById(R.id.btnWrite);
        btnWritten = (Button) findViewById(R.id.btnWritten);

        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTotal.setEnabled(false);
                btnWrite.setEnabled(true);
                btnWritten.setEnabled(true);
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTotal.setEnabled(true);
                btnWrite.setEnabled(false);
                btnWritten.setEnabled(true);
            }
        });

        btnWritten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTotal.setEnabled(true);
                btnWrite.setEnabled(true);
                btnWritten.setEnabled(false);
            }
        });
    }
}