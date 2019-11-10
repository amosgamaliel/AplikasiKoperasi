package com.amos.koperasi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amos.koperasi.Model.HistoryModel;
import com.amos.koperasi.R;

import java.util.List;

public class DetailHistoryAdapter extends RecyclerView.Adapter<DetailHistoryAdapter.DetailHistoryViewHolder> {
    List<HistoryModel> historyModels;
    Context mCtx;

    public DetailHistoryAdapter(List<HistoryModel> historyModels, Context mCtx) {
        this.historyModels = historyModels;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public DetailHistoryAdapter.DetailHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detail_history,parent,false);
        return new DetailHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailHistoryAdapter.DetailHistoryViewHolder holder, int position) {
        HistoryModel historyModel = historyModels.get(position);
        holder.tgl.setText(historyModel.getTanggalBayar());
        holder.jml.setText(historyModel.getJumlah());
    }

    @Override
    public int getItemCount() {
        return historyModels.size();
    }

    public class DetailHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tgl,jml;
        public DetailHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tgl = itemView.findViewById(R.id.tanggaldh);
            jml = itemView.findViewById(R.id.jumlahdh);
        }
    }
}
