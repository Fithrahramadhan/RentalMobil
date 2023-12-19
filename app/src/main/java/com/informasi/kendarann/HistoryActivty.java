package com.informasi.kendarann;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class HistoryActivty extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth auth;
    RecyclerView rv;
    HistoryAdapter historyAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        rv = findViewById(R.id.rvHistory);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        historyAdapter = new HistoryAdapter();
        rv.setAdapter(historyAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(historyAdapter != null){
            db.collection("sewa").document("mobil").collection(Objects.requireNonNull(auth.getCurrentUser()).getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(!queryDocumentSnapshots.isEmpty()){
//                    Log.d("DATAKU", queryDocumentSnapshots.getDocuments().get(0).get("nama").toString());
                        historyAdapter.setData(queryDocumentSnapshots.getDocuments());
                    }else{
                        Toast.makeText(HistoryActivty.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
