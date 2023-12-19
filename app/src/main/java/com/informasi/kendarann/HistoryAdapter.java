package com.informasi.kendarann;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {
    List<DocumentSnapshot> data = new ArrayList<>();
    Context c;
    public void setData(List<DocumentSnapshot> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        c = parent.getContext();
        return new HistoryHolder(LayoutInflater.from(c).inflate(R.layout.item_history, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        holder.name.setText(data.get(position).get("nama").toString());
        holder.kendaraan.setText(data.get(position).get("mobil").toString());
        holder.lama.setText(data.get(position).get("lama").toString());
        holder.total.setText("Rp. " + data.get(position).get("total").toString());
        holder.status.setText(data.get(position).get("status").toString().toUpperCase());
        if(data.get(position).get("status").toString().equals("pending")){
            holder.btnUpload.setVisibility(View.VISIBLE);
            holder.status.setBackgroundColor(c.getResources().getColor(R.color.yellow));
            holder.status.setTextColor(c.getResources().getColor(R.color.black));
        }else{
            holder.status.setBackgroundColor(c.getResources().getColor(R.color.green));
            holder.status.setTextColor(c.getResources().getColor(R.color.white));
            holder.btnUpload.setVisibility(View.GONE);
        }
        holder.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c, BuktiPembayaranActivity.class);
                i.putExtra("id", data.get(position).getId().toString());
                c.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {
        TextView name, kendaraan, lama, total, status;
        Button btnUpload;
        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nama_penyewa);
            kendaraan = itemView.findViewById(R.id.jenis_mobil);
            lama = itemView.findViewById(R.id.lama_sewa);
            total = itemView.findViewById(R.id.total);
            status = itemView.findViewById(R.id.status);
            btnUpload = itemView.findViewById(R.id.toUpload);
        }
    }
}
