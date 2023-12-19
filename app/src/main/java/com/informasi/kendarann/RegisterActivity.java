package com.informasi.kendarann;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.informasi.kendarann.databinding.ActivityRegisterBinding;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkFieldRegister();

    }

    private void checkFieldRegister() {
        binding.btnRegister.setOnClickListener(view -> {
            if (fieldIsEmpty(binding.txtPassword.getText().toString().trim()) || fieldIsEmpty(binding.txtConfirmPassword.getText().toString().trim())) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_LONG).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.txtEmail.getText().toString().trim()).matches()) {
                Toast.makeText(this, "Format email salah", Toast.LENGTH_LONG).show();
            } else if (!binding.txtPassword.getText().toString().trim().equals(binding.txtConfirmPassword.getText().toString().trim())) {
                Toast.makeText(this, "Password dan confirm password tidak sama", Toast.LENGTH_LONG).show();
            }
            else {
                auth.createUserWithEmailAndPassword(binding.txtEmail.getText().toString(), binding.txtConfirmPassword.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                sendEmailVerificationEmail();
                                Toast.makeText(this, "Register Berhasil Cek Emailmu dan Verifikasi", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(this, MainActivity.class));
                                auth.signOut();
                                finish();
                            }
                        })
                        .addOnFailureListener(e -> Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show());
            }
        });
    }

    private void sendEmailVerificationEmail() {
        if (auth.getCurrentUser() != null) {
            auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task ->
                    auth.signOut());
        }
    }

    private boolean fieldIsEmpty(String value) {
        return TextUtils.isEmpty(value);
    }

}