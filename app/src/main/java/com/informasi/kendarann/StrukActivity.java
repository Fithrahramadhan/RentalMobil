package com.informasi.kendarann;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StrukActivity extends AppCompatActivity {
    TextView nama_penyewa,jenis_mobil,lama_sewa,total,uang_bayar,uang_kembali, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_struk);
        nama_penyewa = findViewById(R.id.nama_penyewa);
        jenis_mobil = findViewById(R.id.jenis_mobil);
        lama_sewa = findViewById(R.id.lama_sewa);
        total = findViewById(R.id.total);
        uang_bayar = findViewById(R.id.uang_bayar);
        uang_kembali = findViewById(R.id.uang_kembali);
        status = findViewById(R.id.status);

        nama_penyewa.setText(getIntent().getStringExtra("nama"));
        jenis_mobil.setText(getIntent().getStringExtra("mobil"));
        lama_sewa.setText(String.valueOf(getIntent().getIntExtra("lama",0)));
        total.setText(String.valueOf(getIntent().getIntExtra("total",0)));
        uang_bayar.setText(String.valueOf(getIntent().getIntExtra("uang",0)));
        uang_kembali.setText(String.valueOf(getIntent().getIntExtra("kembalian",0)));
        status.setText(" " + getIntent().getStringExtra("status"));

        Button btnTF = findViewById(R.id.btnBuktiTF);
        btnTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), BuktiPembayaranActivity.class);
                i.putExtra("id", getIntent().getStringExtra("id").toString());
                startActivity(i);
                finish();
            }
        });
    }

    public void hal_utama(View view) {
        Intent intent =new Intent(StrukActivity.this, HistoryActivty.class);
        startActivity(intent);
    }
}