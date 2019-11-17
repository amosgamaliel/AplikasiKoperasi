package com.amos.koperasi.Adapter;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.amos.koperasi.Fragment.Admin.DalamCicilan;
import com.amos.koperasi.Fragment.Admin.DashboardAdminFragment;
import com.amos.koperasi.Fragment.Admin.DetailHistoryFragment;
import com.amos.koperasi.Fragment.Admin.NotifikasiAdminFragment;
import com.amos.koperasi.Fragment.User.CicilanFragment;
import com.amos.koperasi.Model.HistoryModel;
import com.amos.koperasi.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    List<HistoryModel> modelList;
    Context mCtx;
    String idPinjaman,idUser,idCicilan;

    public HistoryAdapter(List<HistoryModel> modelList, Context mCtx) {
        this.modelList = modelList;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_history,parent,false);
        return new HistoryAdapter.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {
        final HistoryModel model = modelList.get(position);
        holder.nama.setText(model.getNama());
        holder.tanggalm.setText(model.getTanggalMulai());
        holder.tanggals.setText(model.getTanggalSelesai());
        idPinjaman = model.getIdPinjaman();
        idCicilan = model.getIdCicilan();
        idUser= model.getIdUser();
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                DetailHistoryFragment detailHistoryFragment = new DetailHistoryFragment();
                 ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_containera, detailHistoryFragment).addToBackStack(null)
                        .commit();
                 Bundle bundle = new Bundle();
                 bundle.putString("ID_USER",idUser);
                 bundle.putString("ID_PINJAMAN",idPinjaman);
                 bundle.putString("NAMA",model.getNama());
                 bundle.putString("TANGGAL_MULAI",model.getTanggalMulai());
                 bundle.putString("TANGGAL_SELESAI",model.getTanggalSelesai());
                 bundle.putString("TENOR",model.getTenor());
                 bundle.putString("TOTAL",model.getJumlah());
                 detailHistoryFragment.setArguments(bundle);

            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView nama,tanggalm,tanggals;
        CardView cv;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.namah);
            tanggalm = itemView.findViewById(R.id.tanggalmh);
            tanggals = itemView.findViewById(R.id.tanggalsh);
            cv = itemView.findViewById(R.id.cvh);
        }
    }
    public void clear() {
        int size = modelList.size();
        modelList.clear();
        notifyItemRangeRemoved(0, size);
    }
}
