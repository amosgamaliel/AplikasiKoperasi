package com.amos.koperasi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amos.koperasi.DalamCicilan;
import com.amos.koperasi.Model.DalamCicilanModel;
import com.amos.koperasi.Model.NotifikasiDisetujui;
import com.amos.koperasi.R;

import java.util.List;

public class DalamCicilanAdapter extends RecyclerView.Adapter<DalamCicilanAdapter.DisetujuiViewHolder>{
    Context mCtx;
    List<DalamCicilanModel> list;
    public DalamCicilanAdapter(Context context, List<DalamCicilanModel> list){
        this.list = list;
        this.mCtx = context;
    }

    @NonNull
    @Override
    public DisetujuiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_dalam_cicilan,parent,false);
        return new DisetujuiViewHolder(view);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull DisetujuiViewHolder holder, int position) {
        DalamCicilanModel cicilanModel = list.get(position);
        holder.nama.setText(cicilanModel.getNama());
        holder.sisa.setText(cicilanModel.getSisaCicilan());
        holder.total.setText(cicilanModel.getTotal());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DisetujuiViewHolder extends RecyclerView.ViewHolder{

        TextView nama,total,sisa;

        public DisetujuiViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.npeminjamc);
            total = itemView.findViewById(R.id.totalDC);
            sisa = itemView.findViewById(R.id.sisa);
        }
    }
}
