package com.informasi.kendarann;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.informasi.kendarann.databinding.ActivityKontakBinding;

public class KontakActivity extends AppCompatActivity {

    ListView listView;
    String[] mTitle = {"Facebook", "Whatsapp", "Instagram", "Youtube"};
    String[] mDescription = {"Fithrah Ramadhan", "082268192225", "@fithrahramadhan26", "fithrah ramadhan"};
    int[] images = {R.drawable.facebook, R.drawable.whatsapp, R.drawable.instagram, R.drawable.youtube};

    private ActivityKontakBinding binding;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKontakBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listView = findViewById(R.id.listView);

        MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);
        listView.setAdapter(adapter);

        binding.btnKeluar.setOnClickListener(view -> {
            auth.signOut();
            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
        });
    }

    public void hal_utama(View view) {
        Intent intent =new Intent(KontakActivity.this,MainActivity .class);
        startActivity(intent);
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String[] rTitle;
        String[] rDescription;
        int[] rImgs;

        MyAdapter (Context c, String[] title, String[] description, int[] imgs) {
            super(c, R.layout.activity_row, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImgs = imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.activity_row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDescription = row.findViewById(R.id.textView2);

            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);

            return row;
        }
    }
}
