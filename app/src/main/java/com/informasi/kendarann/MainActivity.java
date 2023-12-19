package com.informasi.kendarann;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import com.informasi.kendarann.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        procesedLogin();

        binding.btnRegister.setOnClickListener(view ->
                startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void procesedLogin() {
        binding.btnLogin.setOnClickListener(view -> {
            if (TextUtils.isEmpty(binding.txtEmail.getText().toString().trim()) || TextUtils.isEmpty(binding.txtPassword.getText().toString().trim())) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_LONG).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.txtEmail.getText().toString().trim()).matches()) {
                Toast.makeText(this, "Format email salah", Toast.LENGTH_LONG).show();
            } else {
                auth.signInWithEmailAndPassword(
                                binding.txtEmail.getText().toString(),
                                binding.txtPassword.getText().toString()
                        )
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                if (Objects.requireNonNull(auth.getCurrentUser()).isEmailVerified()) {
                                    startActivity(new Intent(this, HomeActivity.class));
                                    finishAffinity();
                                } else {
                                    Toast.makeText(this, "Email belum diverifikasi", Toast.LENGTH_LONG).show();
                                    auth.signOut();
                                }
                            } else {
                                Toast.makeText(this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }
}