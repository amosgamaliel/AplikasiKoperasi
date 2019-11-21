package com.amos.koperasi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amos.koperasi.Model.ActivityModel;
import com.amos.koperasi.R;

import java.util.ArrayList;

public class AllActivityAdapter extends RecyclerView.Adapter<AllActivityAdapter.AllActivityViewHolder> {

    ArrayList<ActivityModel> models;
    Context mCtx;

    public AllActivityAdapter(ArrayList<ActivityModel> models, Context mCtx) {
        this.models = models;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public AllActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_activity,parent,false);
        return new AllActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllActivityViewHolder holder, int position) {
        ActivityModel model = models.get(position);
        String tipe = model.getTipe();
        holder.nama.setText(model.getNama());
        holder.jumlah.setText(model.getJumlah());
        if (tipe.equals("bayar")||tipe.equals("simpan")){
            holder.imageView.setImageResource(R.drawable.ic_profit);
            if (tipe.equals("bayar")){
                holder.desc.setText("Membayar cicilan");
            }else if (tipe.equals("simpan")){
                holder.desc.setText("menyimpan ke koperasi");
            }
        }else{
            holder.imageView.setImageResource(R.drawable.ic_recession);
            holder.desc.setText("meminjam pinjaman");
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class AllActivityViewHolder extends RecyclerView.ViewHolder {
        TextView jumlah,nama,desc;
        ImageView imageView;
        public AllActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            jumlah = itemView.findViewById(R.id.jumlahactivity);
            nama = itemView.findViewById(R.id.namaactivity);
            desc = itemView.findViewById(R.id.descactivity);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
