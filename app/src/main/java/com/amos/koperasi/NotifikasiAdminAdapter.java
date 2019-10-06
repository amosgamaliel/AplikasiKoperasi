package com.amos.koperasi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotifikasiAdminAdapter extends RecyclerView.Adapter<NotifikasiAdminAdapter.InfoPengajuanViewHolder> {
    List<InfoPengajuan> pengajuanList;
    Context mCtx;

    public NotifikasiAdminAdapter(List<InfoPengajuan> pengajuanList, Context mCtx) {
        this.pengajuanList = pengajuanList;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public InfoPengajuanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pengajuan,parent,false);
        return new InfoPengajuanViewHolder(view);

    }

    @Override
    public void onBindViewHolder(InfoPengajuanViewHolder holder, int position) {
        InfoPengajuan infoPengajuan = pengajuanList.get(position);

        holder.namapeminjam.setText(infoPengajuan.getNama());
        holder.jumlah.setText(String.valueOf(infoPengajuan.getJumlah()));
        holder.tenor.setText(String.valueOf(infoPengajuan.getTenor()));
        holder.total.setText(String.valueOf(infoPengajuan.getJatuh()));
    }


    @Override
    public int getItemCount() {
        return pengajuanList.size();
    }

    class InfoPengajuanViewHolder extends RecyclerView.ViewHolder{
        TextView namapeminjam,jumlah,tenor,total;

        public InfoPengajuanViewHolder(@NonNull View itemView) {
            super(itemView);
            namapeminjam = itemView.findViewById(R.id.namapeminjam);
            jumlah = itemView.findViewById(R.id.jumlahpinjaman);
            tenor = itemView.findViewById(R.id.tenorp);
            total = itemView.findViewById(R.id.jatuh);
        }
    }

}
