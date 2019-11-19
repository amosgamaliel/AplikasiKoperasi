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

public class PemasukanAdapter extends RecyclerView.Adapter<PemasukanAdapter.PemasukanViewHolder> {
    ArrayList<ActivityModel> activityModels;
    Context mCtx;

    public PemasukanAdapter(ArrayList<ActivityModel> activityModels, Context mCtx) {
        this.activityModels = activityModels;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public PemasukanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_activity,parent,false);
        return new PemasukanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PemasukanViewHolder holder, int position) {
        ActivityModel model = activityModels.get(position);
            holder.imageView.setImageResource(R.drawable.ic_done_all_black_24dp);
            holder.desc.setText("meminjam pinjaman");
            holder.nama.setText(model.getNama());
            holder.jumlah.setText(model.getJumlah());

    }

    @Override
    public int getItemCount() {
        return activityModels.size();
    }

    public class PemasukanViewHolder extends RecyclerView.ViewHolder {
        TextView jumlah,nama,desc;
        ImageView imageView;
        CardView cardView;
        public PemasukanViewHolder(@NonNull View itemView) {
            super(itemView);
            jumlah = itemView.findViewById(R.id.jumlahactivity);
            nama = itemView.findViewById(R.id.namaactivity);
            desc = itemView.findViewById(R.id.descactivity);
            imageView = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.cvp);
        }
    }
}
