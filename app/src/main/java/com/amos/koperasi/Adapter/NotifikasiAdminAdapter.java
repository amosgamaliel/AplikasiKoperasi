package com.amos.koperasi.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amos.koperasi.Model.InfoPengajuan;
import com.amos.koperasi.R;
import com.amos.koperasi.Utility.Singleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void onBindViewHolder(InfoPengajuanViewHolder holder, final int position) {
        final InfoPengajuan infoPengajuan = pengajuanList.get(position);

        holder.namapeminjam.setText(infoPengajuan.getNama());
        holder.jumlah.setText(String.valueOf(infoPengajuan.getJumlah()));
        holder.tenor.setText(String.valueOf(infoPengajuan.getTenor()));

        holder.setuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.1.6/koperasi_API/menyetujui.php";
                final String id = String.valueOf(infoPengajuan.getId());
                final String iduser = infoPengajuan.getIdUser();
                final String total = String.valueOf(infoPengajuan.getJumlah());
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Respon sukses Cek id", "onResponse: "+id);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("VoleyError", "Error: "+id);
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("idu",iduser);
                        params.put("idpin",id);
                        params.put("total",total);
                        return params;
                    }
                };
                Singleton.getInstance(mCtx).addToRequestQue(stringRequest);
                deleteItem(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return pengajuanList.size();
    }

    class InfoPengajuanViewHolder extends RecyclerView.ViewHolder{
        TextView namapeminjam,jumlah,tenor,total;
         Button setuju,tolak;

        public InfoPengajuanViewHolder(@NonNull View itemView) {
            super(itemView);
            namapeminjam = itemView.findViewById(R.id.namapeminjam);
            jumlah = itemView.findViewById(R.id.jumlahpinjaman);
            tenor = itemView.findViewById(R.id.tenorp);
            setuju = itemView.findViewById(R.id.btnSetuju);
            tolak = itemView.findViewById(R.id.btnTolak);
        }
    }

    private void deleteItem(int position){
        pengajuanList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,pengajuanList.size());
    }

}
