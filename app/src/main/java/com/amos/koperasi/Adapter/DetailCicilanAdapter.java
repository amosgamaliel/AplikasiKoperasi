package com.amos.koperasi.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull DetailCicilanViewHolder holder, int position) {
        DetailCicilanUserModel model =modelList.get(position);

        if (model.getStatus().equals("kosong")){
            holder.status.setVisibility(View.GONE);
        }else if (model.getStatus().equals("lunas")){
            holder.cv.setCardBackgroundColor(R.color.gradient_end_color);
        }
        holder.tv.setText(String.valueOf(model.getJmlCicilan(position)));
        holder.header.setText("Cicilan ke "+(position+1));
        holder.status.setText(model.getStatus());
        holder.tanggal.setText(model.getKe());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class DetailCicilanViewHolder extends RecyclerView.ViewHolder {
        TextView tv,header,status,tanggal;
        CardView cv;
        public DetailCicilanViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.jumlahbayar);
            header = itemView.findViewById(R.id.headerdetail);
            tanggal = itemView.findViewById(R.id.jatuhTempo);
            status = itemView.findViewById(R.id.statusBayar);
            cv = itemView.findViewById(R.id.cardview);

        }
    }
    public void delete(int position) { //removes the row
        modelList.remove(position);
        notifyItemRemoved(position);
    }
}
