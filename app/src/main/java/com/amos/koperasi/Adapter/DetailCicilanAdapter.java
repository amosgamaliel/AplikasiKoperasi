package com.amos.koperasi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amos.koperasi.Model.DalamCicilanModel;
import com.amos.koperasi.Model.DetailCicilanModel;
import com.amos.koperasi.Model.DetailCicilanUserModel;
import com.amos.koperasi.R;

import java.util.ArrayList;
import java.util.List;

public class DetailCicilanAdapter extends RecyclerView.Adapter<DetailCicilanAdapter.DetailCicilanViewHolder> {

    Context mCtx;
    List<DetailCicilanUserModel> modelList;
    public DetailCicilanAdapter(Context mCtx, List<DetailCicilanUserModel> list) {
        this.mCtx = mCtx;
        this.modelList = list;
    }

    @NonNull
    @Override
    public DetailCicilanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cicilan,parent,false);
        return new DetailCicilanAdapter.DetailCicilanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailCicilanViewHolder holder, int position) {
        DetailCicilanUserModel model =modelList.get(position);
        holder.tv.setText(String.valueOf(model.getJmlCicilan(position)));
        holder.header.setText("Cicilan ke "+String.valueOf(position+1));
        holder.status.setText("Belum Dibayar");

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class DetailCicilanViewHolder extends RecyclerView.ViewHolder {
        TextView tv,header,status;
        public DetailCicilanViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.jumlahbayar);
            header = itemView.findViewById(R.id.headerdetail);
            status = itemView.findViewById(R.id.statusBayar);

        }
    }
    public void delete(int position) { //removes the row
        modelList.remove(position);
        notifyItemRemoved(position);
    }
}
