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

import com.amos.koperasi.Model.DalamCicilanModel;
import com.amos.koperasi.Model.DetailCicilanModel;
import com.amos.koperasi.R;
import com.amos.koperasi.Utility.Singleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.DateFormat;
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
    int angka = -1;
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
        holder.total.setText(cicilanModel.getTotal());
        holder.tenor.setText(cicilanModel.getTenor());
        int iJumlah = Integer.parseInt((String) holder.total.getText());
        int bln = Integer.parseInt((String) holder.tenor.getText());
        final int perBulan = iJumlah/bln;
        final ArrayList<Integer> integers = new ArrayList<>();
        ArrayList<DetailCicilanModel> arrayList = new ArrayList<>();
        for (int i = 0; i< bln ; i++){
            DetailCicilanModel model = new DetailCicilanModel();
            int sisa = iJumlah - perBulan*i;
            int cicilan = (int) (perBulan+(sisa*0.02));
            integers.add(cicilan);
            model.setJmlCicilan(integers.get(i));
            arrayList.add(model);
        }
        ArrayList<String> bulanList = new ArrayList<>();
        final DateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
        final Date date = new Date();
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,1);
        calendar.setTime(date);

        for(int i=angka+2; i <= bln+1; i++) {
            calendar.add(Calendar.MONTH,1);
            Date dude = calendar.getTime();
            String bulan = dateFormat.format(dude);
            bulanList.add(bulan);
        }
        final String arraybulan = bulanList.toString();

        holder.nama.setText(cicilanModel.getNama());
        holder.sisa.setText(integers.get(angka).toString());

        holder.lunas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("array", "isi array: "+arraybulan);
                String url = "http://192.168.1.6/koperasi_API/cicilan.php";
                final String iduser = String.valueOf(cicilanModel.getIduser());
                final String id = String.valueOf(cicilanModel.getId());
                final String jumlah = String.valueOf(integers.get(angka).toString());
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Respon sukses Cek id", "onResponse: "+id+arraybulan);
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
                        params.put("idpin",id);
                        params.put("iduser",iduser);
                        params.put("jumlah",jumlah);
                        params.put("bulan",arraybulan);
                        params.put("tanggal",getDateTime());
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
        return list.size();
    }

    public class DisetujuiViewHolder extends RecyclerView.ViewHolder{

        TextView nama,total,sisa,tenor;
        Button lunas;

        public DisetujuiViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.npeminjamc);
            total = itemView.findViewById(R.id.totalDC);
            sisa = itemView.findViewById(R.id.sisa);
            tenor=itemView.findViewById(R.id.tenor);
            lunas = itemView.findViewById(R.id.btnLunas);

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
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
