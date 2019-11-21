package com.amos.koperasi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amos.koperasi.Model.ActivityModel;
import com.amos.koperasi.R;

import java.util.ArrayList;

public class PengeluaranAdapter extends RecyclerView.Adapter<PengeluaranAdapter.PengeluaranViewHolder> {
    ArrayList<ActivityModel> models;
    Context mCtx;

    public PengeluaranAdapter(ArrayList<ActivityModel> models, Context mCtx) {
        this.models = models;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public PengeluaranViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_activity,parent,false);
        return new PengeluaranViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PengeluaranViewHolder holder, int position) {
        ActivityModel model = models.get(position);
            holder.imageView.setImageResource(R.drawable.ic_recession);
            holder.desc.setText("meminjam pinjaman");
            holder.nama.setText(model.getNama()+"jhaaa");
            holder.jumlah.setText(model.getJumlah()+"jhaaa");
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class PengeluaranViewHolder extends RecyclerView.ViewHolder {
        TextView jumlah,nama,desc;
        CardView cardView;
        ImageView imageView;
        public PengeluaranViewHolder(@NonNull View itemView) {
            super(itemView);
            jumlah = itemView.findViewById(R.id.jumlahactivity);
            nama = itemView.findViewById(R.id.namaactivity);
            desc = itemView.findViewById(R.id.descactivity);
            imageView = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.cvp);
        }
    }
}
