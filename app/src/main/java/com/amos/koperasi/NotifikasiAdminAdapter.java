package com.amos.koperasi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotifikasiAdminAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pengajuan,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindData(position);
    }

    @Override
    public int getItemCount() {
        return DataDummy.nama.length;
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        TextView nama,jumlah,tenor,total;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.namapeminjam);
            jumlah = itemView.findViewById(R.id.jumlahpinjaman);
            tenor = itemView.findViewById(R.id.tenorp);
            total = itemView.findViewById(R.id.jatuh);
        }

        public void bindData(int position){
            nama.setText(DataDummy.nama[position]);
            jumlah.setText(DataDummy.jumlah[position]);
            tenor.setText(DataDummy.tenor[position]);
            total.setText(DataDummy.total[position]);
        }
    }

}
