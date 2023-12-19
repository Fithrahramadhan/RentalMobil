package com.informasi.kendarann;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.toHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(HomeActivity.this, HistoryActivty.class);
                startActivity(intent);
            }
        });
    }
    public void Tombol_info(View view) {
        Intent intent =new Intent(HomeActivity.this, DaftarMobilActivity.class);
        startActivity(intent);
    }

    public void tombol_sewa(View view) {
        Intent intent =new Intent(HomeActivity.this, SewaMobilActivity.class);
        startActivity(intent);
    }

    public void tombol_contact(View view) {
        Intent intent =new Intent(HomeActivity.this, KontakActivity.class);
        startActivity(intent);
    }

}