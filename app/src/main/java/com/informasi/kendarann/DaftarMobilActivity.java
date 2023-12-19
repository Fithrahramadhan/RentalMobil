package com.informasi.kendarann;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DaftarMobilActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<DataCar> data = new ArrayList<>();
    RecyclerView rvCar;
    CarAdapter carAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_mobil);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("carData");

        rvCar = findViewById(R.id.rvCar);
        rvCar.setLayoutManager(new LinearLayoutManager(this));
        rvCar.setHasFixedSize(true);
        rvCar.setNestedScrollingEnabled(false);
        carAdapter = new CarAdapter(this);
        rvCar.setAdapter(carAdapter);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    Log.d("DATA FIREBASE", dataSnapshot.child("name").getValue().toString());
                    data.add(new DataCar(
                            dataSnapshot.child("id").getValue().toString(),
                            dataSnapshot.child("name").getValue().toString(),
                            dataSnapshot.child("image").getValue().toString(),
                            dataSnapshot.child("price").getValue().toString(),
                            dataSnapshot.child("sit").getValue().toString(),
                            dataSnapshot.child("year").getValue().toString()
                    ));
                }
                carAdapter.Update(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("DATA FIREBASE", error.getMessage().toString());
                Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void hal_sewa(View view) {
        Intent intent = new Intent(DaftarMobilActivity.this, SewaMobilActivity.class);
        startActivity(intent);
    }
}