package com.amos.koperasi.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.amos.koperasi.Utility.SharedPreferenceConfig;
import com.amos.koperasi.Utility.Singleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MenyerahkanAdapter extends RecyclerView.Adapter<MenyerahkanAdapter.MenyerahkanViewHolder> {

    ArrayList<InfoPengajuan> list;
    Context mCtx;
    SharedPreferenceConfig sharedPreferenceConfig;
    String url,kurs;
    Date awalBulan,akhirbulan,tanggalcicilan;
    AlertDialog.Builder builder;

    public MenyerahkanAdapter(ArrayList<InfoPengajuan> list, Context mCtx) {
        this.list = list;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public MenyerahkanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tunggu_serah,parent,false);
        return new MenyerahkanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenyerahkanViewHolder holder, final int position) {
        final InfoPengajuan model = list.get(position);
        holder.nama.setText(model.getNama());
        holder.tenor.setText(model.getTanggalDiserahkan());
        holder.idp.setText(String.valueOf(model.getId()));
        holder.jumlah.setText(String.valueOf(model.getJumlah()));
        holder.bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Apakah anda yakin ingin mengubah status pinjaman ini?");
                builder.setCancelable(true);

                builder.setPositiveButton("Yakin", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final String id = String.valueOf(model.getId());
                        final String iduser = model.getIdUser();
                        final String total = String.valueOf(model.getJumlah());
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
                                                builder.setMessage("Status peminjaman "+model.getNama()+" berhasil diperbarui");
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
                                params.put("tenor",String.valueOf(model.getTenor()));
                                params.put("tanggal",getDateTime());
                                return params;
                            }
                        };
                        Singleton.getInstance(mCtx).addToRequestQue(stringRequest);
                        deleteItem(position);
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MenyerahkanViewHolder extends RecyclerView.ViewHolder {
        TextView nama,jumlah,tenor,idp;
        Button bayar;
        public MenyerahkanViewHolder(@NonNull View itemView) {
            super(itemView);
            sharedPreferenceConfig =  new SharedPreferenceConfig(mCtx);
            url = sharedPreferenceConfig.getUrl()+"menyerahkan.php";
            nama = itemView.findViewById(R.id.npeminjamc);
            jumlah = itemView.findViewById(R.id.jumlahc);
            tenor=itemView.findViewById(R.id.jatuhc);
            bayar = itemView.findViewById(R.id.btnNormal);
            idp = itemView.findViewById(R.id.idpinjaman);
        }
    }
        private void deleteItem(int position){
            list.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,list.size());
        }

        private String getDateTime() {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd");
            Date date = new Date();
            return dateFormat.format(date);
        }
}
