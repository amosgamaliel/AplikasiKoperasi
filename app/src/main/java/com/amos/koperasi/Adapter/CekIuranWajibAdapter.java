package com.amos.koperasi.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amos.koperasi.Model.IuranWajibModel;
import com.amos.koperasi.R;

import java.util.ArrayList;

public class CekIuranWajibAdapter extends RecyclerView.Adapter<CekIuranWajibAdapter.CekIuranWajibViewHolder> {

    Context mCtx;
    ArrayList<IuranWajibModel> list;

    public CekIuranWajibAdapter(Context mCtx, ArrayList<IuranWajibModel> list) {
        this.mCtx = mCtx;
        this.list = list;
    }

    @NonNull
    @Override
    public CekIuranWajibAdapter.CekIuranWajibViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cek_wajib,parent,false);
        return new CekIuranWajibViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CekIuranWajibAdapter.CekIuranWajibViewHolder holder, int position) {
        IuranWajibModel model = list.get(position);
        String nominal = model.getNominal();

        if (nominal.equals("null")){
            holder.imageView.setImageResource(R.drawable.ic_belum);
            holder.tanggal.setText("Belum Membayar");
        }else{
            holder.imageView.setImageResource(R.drawable.ic_selesai);
            holder.tanggal.setText(model.getTanggal());
        }
        holder.nama.setText(model.getNama());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CekIuranWajibViewHolder extends RecyclerView.ViewHolder {
        TextView nama,nominal,tanggal;
        LinearLayout linearLayout;
        CardView cardView;
        ImageView imageView;
        public CekIuranWajibViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.namawajib);
            tanggal = itemView.findViewById(R.id.tanggalwajib);
            cardView = itemView.findViewById(R.id.cvw);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
