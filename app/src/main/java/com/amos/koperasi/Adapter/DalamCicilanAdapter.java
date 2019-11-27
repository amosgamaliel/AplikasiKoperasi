package com.amos.koperasi.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amos.koperasi.Fragment.Admin.DalamCicilan;
import com.amos.koperasi.Model.DalamCicilanModel;
import com.amos.koperasi.Model.DetailCicilanModel;
import com.amos.koperasi.R;
import com.amos.koperasi.Utility.SharedPreferenceConfig;
import com.amos.koperasi.Utility.Singleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DalamCicilanAdapter extends RecyclerView.Adapter<DalamCicilanAdapter.DisetujuiViewHolder>{
    Context mCtx;
    List<DalamCicilanModel> list;
    SharedPreferenceConfig sharedPreferenceConfig;
    String url;
    Date awalBulan,akhirbulan,tanggalcicilan;

    int angka = 0;
    public DalamCicilanAdapter(Context context, List<DalamCicilanModel> list){
        this.list = list;
        this.mCtx = context;
    }

    @NonNull
    @Override
    public DisetujuiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_dalam_cicilan,parent,false);
        return new DisetujuiViewHolder(view);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull DisetujuiViewHolder holder, final int position) {
        final DalamCicilanModel cicilanModel = list.get(position);
        holder.lunas.setVisibility(View.GONE);
        String jumlahcicilan = cicilanModel.getJumlah();
        try {
            awalBulan = new SimpleDateFormat("yyyyMMdd").parse(getDateAwal());
            akhirbulan = new SimpleDateFormat("yyyyMMdd").parse(getDateAkhir());
            tanggalcicilan = new SimpleDateFormat("yyyyMMdd").parse(cicilanModel.getJatuhTempo());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (!tanggalcicilan.after(awalBulan)&&tanggalcicilan.before(akhirbulan)){
            holder.lunas.setVisibility(View.VISIBLE);
            final int jumlah = (cicilanModel.getJumlahpinjaman());
            int tenor = Integer.parseInt(cicilanModel.getTenor());
            int lunas = cicilanModel.getSisaCicilan();
            int perbulan = jumlah/tenor;

            int bayar = jumlah-perbulan*lunas;
            holder.lunas.setText(String.valueOf(bayar));

            holder.lunas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String iduser = String.valueOf(cicilanModel.getIduser());
                    final String id = String.valueOf(cicilanModel.getId());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                                        Log.d("tes response", "onResponse: "+response);

                                        String status = jsonObject.getString("code");

                                        Toast.makeText(mCtx,status,Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.d("ah", "onResponse: "+e.getMessage());
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("VoleyError", "Error: "+id+error.toString());
                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<>();
                            params.put("id_pinjaman",id);
                            params.put("id_cicilan",cicilanModel.getIdCicilan());
                            params.put("id_user",iduser);
                            params.put("tanggal",getDateTime());
                            params.put("jumlah",String.valueOf(jumlah));
                            params.put("method","langsung");
                            return params;
                        }
                    };
                    Singleton.getInstance(mCtx).addToRequestQue(stringRequest);
                    deleteItem(position);


                }
            });
        }
            holder.bayar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String iduser = String.valueOf(cicilanModel.getIduser());
                    final String id = String.valueOf(cicilanModel.getId());
                    final int jumlah = (cicilanModel.getJumlahpinjaman());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                                        Log.d("tes response", "onResponse: "+response);

                                        String status = jsonObject.getString("code");

                                        Toast.makeText(mCtx,status,Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("VoleyError", "Error: "+id+error.toString());
                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<>();
                            params.put("id_pinjaman",id);
                            params.put("id_cicilan",cicilanModel.getIdCicilan());
                            params.put("id_user",iduser);
                            params.put("tanggal",getDateTime());
                            params.put("jumlah",cicilanModel.getJumlah());
                            params.put("method","normal");
                            return params;
                        }
                    };
                    Singleton.getInstance(mCtx).addToRequestQue(stringRequest);
                    deleteItem(position);
                }
            });


        holder.nama.setText(cicilanModel.getNama());
        holder.jumlah.setText("Rp. "+jumlahcicilan);
        holder.tenor.setText(cicilanModel.getJatuhTempo());
        holder.idp.setText(cicilanModel.getId());


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DisetujuiViewHolder extends RecyclerView.ViewHolder{

        TextView nama,jumlah,tenor,idp;
        Button bayar,lunas;

        public DisetujuiViewHolder(@NonNull View itemView) {
            super(itemView);
            sharedPreferenceConfig =  new SharedPreferenceConfig(mCtx);
            url = sharedPreferenceConfig.getUrl()+"cicilan.php";
            nama = itemView.findViewById(R.id.npeminjamc);
            jumlah = itemView.findViewById(R.id.jumlahc);
            tenor=itemView.findViewById(R.id.jatuhc);
            bayar = itemView.findViewById(R.id.btnNormal);
            idp = itemView.findViewById(R.id.idpinjaman);
            lunas = itemView.findViewById(R.id.btnLangsung);

        }
    }
    public void clear() {
        int size = list.size();
        list.clear();
        notifyItemRangeRemoved(0, size);
    }
    public void tambahBulan(int i){
        this.angka = angka+1;
        Log.d("isi angka", "tambahBulan: "+angka);
        notifyDataSetChanged();
    }
    public void kurangBulan(int i){
        this.angka = angka-1;
        Log.d("isi angka", "tambahBulan: "+angka);
        notifyDataSetChanged();
    }
    private void deleteItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,list.size());
    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyyMMdd");
        Date date = new Date();
        return dateFormat.format(date);
    }
    private boolean dalamJangkauan(Date date){
        return date.after(awalBulan)||date.before(akhirbulan);
    }
    private String getDateAwal() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyyMM01");
        Date date = new Date();
        return dateFormat.format(date);
    }
    private String getDateAkhir() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyyMM31");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
