package com.amos.koperasi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.amos.koperasi.Fragment.Admin.DetailHistoryFragment;
import com.amos.koperasi.Fragment.Admin.DetailTransaksi;
import com.amos.koperasi.Fragment.Admin.DetailTransaksiActivity;
import com.amos.koperasi.Model.ActivityModel;
import com.amos.koperasi.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class PemasukanAdapter extends RecyclerView.Adapter<PemasukanAdapter.PemasukanViewHolder> {
    ArrayList<ActivityModel> activityModels;
    Context mCtx;
    String kurs;

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
        final ActivityModel model = activityModels.get(position);
        final DecimalFormat kursIndonesia = (DecimalFormat)DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        String jumlah = String.valueOf(model.getJumlah());
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        kurs = kursIndonesia.format(Double.parseDouble(jumlah));
            holder.jumlah.setText(kurs);
            holder.imageView.setImageResource(R.drawable.ic_profit);
            holder.desc.setText("membayar cicilan");
            holder.nama.setText(model.getNama());
            String tipe = model.getTipe();
            if (tipe.equals("bayar")) {
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mCtx, DetailTransaksiActivity.class);
                        intent.putExtra("ID_USER",model.getIduser());
                        intent.putExtra("ID_PINJAMAN",model.getIdpinjaman());
                        mCtx.startActivity(intent);
                    }
                });
            }

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
    public void clear() {
        int size = activityModels.size();
        activityModels.clear();
        notifyItemRangeRemoved(0, size);
    }
    public void updateData(List<ActivityModel> newList){
        activityModels = new ArrayList<>();
        activityModels.addAll(newList);
        notifyDataSetChanged();
    }
}
