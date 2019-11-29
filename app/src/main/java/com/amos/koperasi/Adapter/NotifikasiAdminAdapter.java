package com.amos.koperasi.Adapter;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.amos.koperasi.Activity.AdminActivity;
import com.amos.koperasi.Fragment.Admin.DetailTransaksi;
import com.amos.koperasi.Model.InfoPengajuan;
import com.amos.koperasi.R;
import com.amos.koperasi.Utility.SharedPreferenceConfig;
import com.amos.koperasi.Utility.Singleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotifikasiAdminAdapter extends RecyclerView.Adapter<NotifikasiAdminAdapter.InfoPengajuanViewHolder> {
    List<InfoPengajuan> pengajuanList;
    Context mCtx;
    SharedPreferenceConfig sharedPreferenceConfig;
    String url,jabatan,kurs;
    AlertDialog.Builder builder ;

    private final String CHANNEL_ID = "personal_notification";
    private final int NOTIFICATION_ID = 001;

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
        final DecimalFormat kursIndonesia = (DecimalFormat)DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        String jumlah = String.valueOf(infoPengajuan.getJumlah());
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        kurs = kursIndonesia.format(Double.parseDouble(jumlah));
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailTransaksi detailTransaksi = new DetailTransaksi();
                ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_containera, detailTransaksi).addToBackStack(null)
                        .commit();
                Bundle bundle = new Bundle();
                bundle.putString("ID_USER", infoPengajuan.getIdUser());
                bundle.putString("ID_PINJAMAN", String.valueOf(infoPengajuan.getId()));
                detailTransaksi.setArguments(bundle);
            }
        });
        holder.jumlah.setText(kurs);
        holder.tenor.setText(String.valueOf(infoPengajuan.getTenor()));
        holder.tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Apakah anda yakin ingin menolak pinjaman ini?");
                builder.setCancelable(true);

                builder.setPositiveButton("Yakin", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String id = String.valueOf(infoPengajuan.getId());
                        final String iduser = infoPengajuan.getIdUser();
                        final String total = String.valueOf(infoPengajuan.getJumlah());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            String code = jsonObject.getString("code");
                                            if (code.equals("200")){
                                                builder = new AlertDialog.Builder(mCtx);
                                                builder.setTitle("Berhasil");
                                                builder.setMessage("Pengajuan peminjaman berhasil ditolak");
                                                builder.setCancelable(false);

                                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();

                                                    }
                                                });
                                                builder.show();
                                            }else if (code.equals("404")){
                                                builder = new AlertDialog.Builder(mCtx);
                                                builder.setTitle("Gagal");
                                                builder.setMessage("Peminjaman gagal ditolak...");
                                                builder.setCancelable(false);

                                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();

                                                    }
                                                });
                                                builder.show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
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
                                params.put("id_user",iduser);
                                params.put("id_pinjaman",id);
                                params.put("jumlah",total);
                                params.put("tenor",String.valueOf(infoPengajuan.getTenor()));
                                params.put("tanggal",getDateTime());
                                params.put("action","tolak");
                                return params;
                            }
                        };
                        Singleton.getInstance(mCtx).addToRequestQue(stringRequest);
                        deleteItem(position);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(mCtx,R.anim.fade_kiri_ke_kanan));
        holder.setuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Apakah anda yakin ingin menyetujui pinjaman ini?");
                builder.setCancelable(true);

                builder.setPositiveButton("Yakin", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String id = String.valueOf(infoPengajuan.getId());
                        final String iduser = infoPengajuan.getIdUser();
                        final String total = String.valueOf(infoPengajuan.getJumlah());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            String code = jsonObject.getString("code");
                                            if (code.equals("200")){
                                                builder = new AlertDialog.Builder(mCtx);
                                                builder.setTitle("Berhasil");
                                                builder.setMessage("Pengajuan peminjaman berhasil disetujui");
                                                builder.setCancelable(false);

                                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();

                                                    }
                                                });
                                                builder.show();
                                            }else if (code.equals("404")){
                                                builder = new AlertDialog.Builder(mCtx);
                                                builder.setTitle("Gagal");
                                                builder.setMessage("Peminjaman gagal disetujui...");
                                                builder.setCancelable(false);

                                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                                builder.show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
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
                                params.put("id_user",iduser);
                                params.put("id_pinjaman",id);
                                params.put("jumlah",total);
                                params.put("tenor",String.valueOf(infoPengajuan.getTenor()));
                                params.put("tanggal",getDateTime());
                                params.put("action","setuju");
                                return params;
                            }
                        };
                        Singleton.getInstance(mCtx).addToRequestQue(stringRequest);
                        deleteItem(position);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });
    }

    private void createNotificationChannel() {
    }

    @Override
    public int getItemCount() {
        return pengajuanList.size();
    }

    class InfoPengajuanViewHolder extends RecyclerView.ViewHolder{
        TextView namapeminjam,jumlah,tenor,total,tolak,detail;
         Button setuju;
         CardView cardView;
        SharedPreferences mSettings;

        public InfoPengajuanViewHolder(@NonNull View itemView) {
            super(itemView);
            SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(mCtx);
            namapeminjam = itemView.findViewById(R.id.namapeminjam);
            jumlah = itemView.findViewById(R.id.jumlahpinjaman);
            tenor = itemView.findViewById(R.id.tenorp);
            detail = itemView.findViewById(R.id.lihatdetail);
            setuju = itemView.findViewById(R.id.btnSetuju);
            tolak = itemView.findViewById(R.id.btnTolak);
            cardView = itemView.findViewById(R.id.cardviewp);
            sharedPreferenceConfig =  new SharedPreferenceConfig(mCtx);
            url = sharedPreferenceConfig.getUrl()+"menyetujui.php";
            jabatan = mSettings.getString("jabatan","admin");
        }
    }

    private void deleteItem(int position){
        pengajuanList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,pengajuanList.size());
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }



}
