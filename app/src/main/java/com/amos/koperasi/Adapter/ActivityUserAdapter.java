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

import com.amos.koperasi.Fragment.Admin.DetailTransaksi;
import com.amos.koperasi.Fragment.Admin.DetailTransaksiActivity;
import com.amos.koperasi.Model.ActivityModel;
import com.amos.koperasi.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class ActivityUserAdapter extends RecyclerView.Adapter<ActivityUserAdapter.ActivityUserViewHolder> {

    public ActivityUserAdapter(ArrayList<ActivityModel> list, Context mCtx) {
        this.list = list;
        this.mCtx = mCtx;
    }
    String kurs;

    ArrayList<ActivityModel> list;
    Context mCtx;
    @NonNull
    @Override
    public ActivityUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_activity,parent,false);
        return new ActivityUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityUserViewHolder holder, int position) {
        final ActivityModel model = list.get(position);
        String tipe = model.getTipe();
        holder.nama.setText(model.getNama());
        final DecimalFormat kursIndonesia = (DecimalFormat)DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        String jumlah = model.getJumlah();
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        kurs = kursIndonesia.format(Double.parseDouble(jumlah));
        holder.jumlah.setText(kurs);
        if (tipe.equals("bayar")||tipe.equals("simpan")){
            holder.imageView.setImageResource(R.drawable.ic_recession);
            if (tipe.equals("bayar")){
                holder.desc.setText("Membayar cicilan");
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mCtx, DetailTransaksiActivity.class);
                        intent.putExtra("ID_USER",model.getIduser());
                        intent.putExtra("ID_PINJAMAN",model.getIdpinjaman());
                        mCtx.startActivity(intent);
                    }
                });

            }else if (tipe.equals("simpan")){
                holder.desc.setText("menyimpan ke koperasi");
            }
        }else{
            holder.imageView.setImageResource(R.drawable.ic_profit);
            holder.desc.setText("meminjam pinjaman");
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailTransaksi detailTransaksi = new DetailTransaksi();
                    ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_containera, detailTransaksi).addToBackStack(null)
                            .commit();
                    Bundle bundle = new Bundle();
                    bundle.putString("ID_USER", model.getIduser());
                    bundle.putString("ID_PINJAMAN", model.getIdpinjaman());
                    detailTransaksi.setArguments(bundle);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ActivityUserViewHolder extends RecyclerView.ViewHolder {
        TextView jumlah,nama,desc;
        CardView cardView;
        ImageView imageView;
        String kurs;
        public ActivityUserViewHolder(@NonNull View itemView) {
            super(itemView);
            jumlah = itemView.findViewById(R.id.jumlahactivity);
            nama = itemView.findViewById(R.id.namaactivity);
            desc = itemView.findViewById(R.id.descactivity);
            imageView = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.cvp);
        }
    }
    public void clear() {
        int size = list.size();
        list.clear();
        notifyItemRangeRemoved(0, size);
    }
}
