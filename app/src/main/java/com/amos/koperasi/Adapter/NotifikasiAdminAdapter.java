package com.amos.koperasi.Adapter;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.RecyclerView;

import com.amos.koperasi.Activity.AdminActivity;
import com.amos.koperasi.Model.InfoPengajuan;
import com.amos.koperasi.R;
import com.amos.koperasi.Utility.SharedPreferenceConfig;
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
    SharedPreferenceConfig sharedPreferenceConfig;
    String url,jabatan;

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
        holder.jumlah.setText(String.valueOf(infoPengajuan.getJumlah()));
        holder.tenor.setText(String.valueOf(infoPengajuan.getTenor()));
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(mCtx,R.anim.fade_kiri_ke_kanan));
        holder.setuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = String.valueOf(infoPengajuan.getId());
                final String iduser = infoPengajuan.getIdUser();
                final String total = String.valueOf(infoPengajuan.getJumlah());
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Respon sukses Cek id", "onResponse: "+id);
                                if (jabatan.equals("admin")){
                                    createNotificationChannel();

                                    Intent landingIntent = new Intent(mCtx, AdminActivity.class);
                                    landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    landingIntent.putExtra("menuFragment","notifikasiAdmin");

                                    PendingIntent landingPendingIntent = PendingIntent.getActivity(mCtx,0,landingIntent,PendingIntent.FLAG_ONE_SHOT);
                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(mCtx,CHANNEL_ID);
                                    builder.setSmallIcon(R.drawable.ic_collections_bookmark_white_24dp);
                                    builder.setContentTitle("Ada Pengajuan Baru");
                                    builder.setContentText("Pengajuan anda diterima oleh admin");
                                    builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                    builder.setContentIntent(landingPendingIntent);

                                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(mCtx);
                                    notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
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

    private void createNotificationChannel() {
    }

    @Override
    public int getItemCount() {
        return pengajuanList.size();
    }

    class InfoPengajuanViewHolder extends RecyclerView.ViewHolder{
        TextView namapeminjam,jumlah,tenor,total,tolak;
         Button setuju;
         CardView cardView;
        SharedPreferences mSettings;

        public InfoPengajuanViewHolder(@NonNull View itemView) {
            super(itemView);
            SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(mCtx);
            namapeminjam = itemView.findViewById(R.id.namapeminjam);
            jumlah = itemView.findViewById(R.id.jumlahpinjaman);
            tenor = itemView.findViewById(R.id.tenorp);
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

}
