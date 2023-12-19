package com.informasi.kendarann;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SewaMobilActivity extends AppCompatActivity {
//    public static final String url = "http://192.168.1.13/rentalmobil/tbl_pemesan";
    int harga_sewa_mobil, jml_lmsw, ttl_hargasewa, jml_uang;
    String s_nama;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    Spinner ad_listmobil;
    TextView harga_mobil;
    EditText lama_sewa, uangbayar, nama_penyewa;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

//    String[] list_mobil = {"Toyota Avansa", "Daihatsu Xenia", "Mitsubisi Pajero", "Toyota Fortuner", "Honda Scoopy", "Honda Revo", "Honda Beet"};
    String[] list_mobil;
    int[] list_price;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<DataCar> dataCar = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewa_mobil);
        nama_penyewa = findViewById(R.id.nama_penyewa);
        ad_listmobil = findViewById(R.id.ad_listmobil);
        harga_mobil = findViewById(R.id.harga_mobil);
        lama_sewa = findViewById(R.id.lama_sewa);
        uangbayar = findViewById(R.id.uangbayar);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("carData");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    dataCar.add(new DataCar(
                            dataSnapshot.child("id").getValue().toString(),
                            dataSnapshot.child("name").getValue().toString(),
                            dataSnapshot.child("image").getValue().toString(),
                            dataSnapshot.child("price").getValue().toString(),
                            dataSnapshot.child("sit").getValue().toString(),
                            dataSnapshot.child("year").getValue().toString()
                    ));
                }
                list_mobil = new String[dataCar.size()];
                list_price = new int[dataCar.size()];

                for(int i=0; i<dataCar.size();i++){
                   list_mobil[i] = dataCar.get(i).getName();
                   list_price[i] = Integer.valueOf(dataCar.get(i).getPrice());
                }

                ArrayAdapter ad_mbl = new ArrayAdapter(SewaMobilActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list_mobil);
                ad_listmobil.setAdapter(ad_mbl);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void tmbl_OK(View view) {
        jml_lmsw = Integer.parseInt(lama_sewa.getText().toString());
        s_nama = nama_penyewa.getText().toString();



//        if (ad_listmobil.getSelectedItem().toString() == "Toyota Avansa") {
//            harga_sewa_mobil = 300000;
//            ttl_hargasewa = jml_lmsw * harga_sewa_mobil;
//            harga_mobil.setText(Integer.toString(ttl_hargasewa));
//        } else if (ad_listmobil.getSelectedItem().toString() == "Daihatsu Xenia") {
//            harga_sewa_mobil = 300000;
//            ttl_hargasewa = jml_lmsw * harga_sewa_mobil;
//            harga_mobil.setText(Integer.toString(ttl_hargasewa));
//        } else if (ad_listmobil.getSelectedItem().toString() == "Mitsubisi Pajero") {
//            harga_sewa_mobil = 300000;
//            ttl_hargasewa = jml_lmsw * harga_sewa_mobil;
//            harga_mobil.setText(Integer.toString(ttl_hargasewa));
//        } else if (ad_listmobil.getSelectedItem().toString() == "Toyota Fortuner") {
//            harga_sewa_mobil = 300000;
//            ttl_hargasewa = jml_lmsw * harga_sewa_mobil;
//            harga_mobil.setText(Integer.toString(ttl_hargasewa));
//        } else if (ad_listmobil.getSelectedItem().toString() == "Honda Scoopy")
//            harga_sewa_mobil = 100000;
//        ttl_hargasewa = jml_lmsw * harga_sewa_mobil;
        ttl_hargasewa = jml_lmsw * list_price[ad_listmobil.getSelectedItemPosition()];
        harga_mobil.setText(Integer.toString(ttl_hargasewa));


    }

    public void tombol_sewa2(View view) {
        jml_uang = Integer.parseInt(uangbayar.getText().toString());
        if (jml_uang < ttl_hargasewa) {
            Toast.makeText(this, "Uang Kurang", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(SewaMobilActivity.this, StrukActivity.class);

            intent.putExtra("nama", s_nama);
            intent.putExtra("mobil", ad_listmobil.getSelectedItem().toString());
            intent.putExtra("lama", jml_lmsw);
            intent.putExtra("total", ttl_hargasewa);
            intent.putExtra("uang", jml_uang);
            intent.putExtra("status", "pending");
            intent.putExtra("tf", "");
            intent.putExtra("kembalian", jml_uang - ttl_hargasewa);

            HashMap<String, String> data = new HashMap<String, String>();
            data.put("nama", s_nama);
            data.put("mobil", ad_listmobil.getSelectedItem().toString());
            data.put("lama", String.valueOf( jml_lmsw) + " Hari");
            data.put("total", String.valueOf(ttl_hargasewa));
            data.put("uang", String.valueOf(jml_uang));
            data.put("kembalian", String.valueOf(jml_uang - ttl_hargasewa));
            data.put("status", "pending");
            data.put("tf", "");


            int randomId = (int) (Math.random() * 1000);
            db.collection("sewa").document("mobil").collection(Objects.requireNonNull(auth.getCurrentUser()).getUid()).document(String.valueOf(randomId))
                            .set(
                                data
                            );
            intent.putExtra("id", String.valueOf(randomId));
            startActivity(intent);
            finish();
        }
    }

    public void hal_utama(View view) {
        Intent intent = new Intent(SewaMobilActivity.this, MainActivity.class);
        startActivity(intent);

//        StringRequest stringRequest = null;
//        StringRequest finalStringRequest = stringRequest;
//        stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                }
//            }
//        ) {
//
//            private void getprams() throws AuthFailureError {
//                //posting parameter ke post url
//                Map<String, String> prams = new HashMap<>();
//                {
//                }
//
//                assert getActivity() != null;
//                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
//                queue.add(null);
//            }
//        };
    }

    private Context getActivity() {
        return null;
    }
}