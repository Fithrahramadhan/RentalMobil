package com.informasi.kendarann;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarHolder> {
    Context c;
    List<DataCar> data = new ArrayList<>();

    public CarAdapter(Context c) {
        this.c = c;
    }

    public void Update(List<DataCar> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_car, parent, false);
        return new CarHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CarHolder holder, int position) {
        DataCar item = data.get(position);
        Picasso.get().load(item.getImage()).into(holder.img);
        holder.name.setText(item.getName());
        holder.price.setText("Sewa per hari Rp. " + item.getPrice());
        holder.year.setText("Tahun " + item.getYear());
        holder.sit.setText(item.getSit() + " tempat duduk");
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }


    public class CarHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name, year, sit, price;
        public CarHolder(@NonNull View v) {
            super(v);
            img = v.findViewById(R.id.imgCar);
            name = v.findViewById(R.id.nameCar);
            year = v.findViewById(R.id.yearCar);
            sit = v.findViewById(R.id.sitCar);
            price = v.findViewById(R.id.priceCar);
        }
    }
}
