package com.informasi.kendarann;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import javax.annotation.Nullable;

public class BuktiPembayaranActivity extends AppCompatActivity {

    private final int GALERY_TEQ_CODE = 1000;
    ImageView imgGallery;
    Button btnUpload;
    Uri fileBukti = null;

    FirebaseFirestore db;
    FirebaseAuth auth;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bukti_pembayaran);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("image_pay");

         imgGallery= findViewById(R.id.imgGalery);
        Button btnGalery = findViewById(R.id.btnCamera);
        btnUpload = findViewById(R.id.btnUpload);
        btnUpload.setVisibility(View.GONE);

        btnGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent iGalery = new Intent(Intent.ACTION_PICK);
                iGalery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGalery, GALERY_TEQ_CODE);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storageReference.child("bukti-tf-" + UUID.randomUUID().toString()).putFile(fileBukti).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                HashMap<String, Object> data = new HashMap<String, Object>();
                                data.put("status", "lunas");
                                data.put("tf", task.getResult().toString());
                                db.collection("sewa").document("mobil").collection(Objects.requireNonNull(auth.getCurrentUser()).getUid()).document(getIntent().getStringExtra("id")).update(data);
                                Toast.makeText(BuktiPembayaranActivity.this, "Pembayaran Sukses", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestcode, int resultcode, @Nullable Intent data) {
        super.onActivityResult(requestcode, resultcode, data);

        if (resultcode==RESULT_OK){

            if (requestcode==GALERY_TEQ_CODE){
                // for gallery
                fileBukti = data.getData();
                if(fileBukti != null){
                    imgGallery.setImageURI(fileBukti);
                    btnUpload.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}