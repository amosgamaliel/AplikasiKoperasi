package com.amos.koperasi.Fragment.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amos.koperasi.Adapter.DetailHistoryAdapter;
import com.amos.koperasi.Model.HistoryModel;
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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailHistoryActivity extends AppCompatActivity {

    String nama,tanggalm,tanggals,idUser,idPinjaman,jumlah,tenor;
    TextView tvJumlah,tvNama,tvTenor,tvTs,tvTm;
    TextView tvnama,tvtanggal,tanggaldiajukan;
    List<HistoryModel> list = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    DetailHistoryAdapter historyAdapter;
    SharedPreferenceConfig sharedPreferenceConfig;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_history);


        tvJumlah = findViewById(R.id.jumlahpew);
        tvnama = findViewById(R.id.namapw);
        tvTenor = findViewById(R.id.tenorpew);
        tvTs = findViewById(R.id.tanggals);
        tvTm = findViewById(R.id.tanggalm);
        tanggaldiajukan = findViewById(R.id.tanggaldiajukan);

        final DecimalFormat kursIndonesia = (DecimalFormat)DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        recyclerView = findViewById(R.id.rvdh);
        historyAdapter = new DetailHistoryAdapter(list,this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();
        sharedPreferenceConfig = new SharedPreferenceConfig(this);
        url = sharedPreferenceConfig.getUrl()+"disetujui2.php";

            nama = getIntent().getStringExtra("NAMA");
            tanggalm = getIntent().getStringExtra("TANGGAL_MULAI");
            String tanggalmc = getIntent().getStringExtra("TANGGAL_MULAI_CICILAN");
            tanggals = getIntent().getStringExtra("TANGGAL_SELESAI");
            idUser = getIntent().getStringExtra("ID_USER");
            idPinjaman = getIntent().getStringExtra("ID_PINJAMAN");
            jumlah = getIntent().getStringExtra("TOTAL");
            tenor = getIntent().getStringExtra("TENOR");
            String x = kursIndonesia.format(Double.parseDouble(jumlah));
            tvJumlah.setText(x);
            tanggaldiajukan.setText(tanggalm);
            tvTm.setText(tanggalmc);
            tvTs.setText(tanggals);
            tvTenor.setText(tenor);
            tvnama.setText(nama);

        getData();
    }

    private void getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0 ; i< jsonArray.length();i++) {
                                JSONObject product = jsonArray.getJSONObject(i);
                                list.add(new HistoryModel(
                                        product.getString("tanggal_bayar"),
                                        product.getString("jumlah")

                                ));
                                historyAdapter = new DetailHistoryAdapter(list,DetailHistoryActivity.this);
                                recyclerView.setAdapter(historyAdapter);
                                recyclerView.setLayoutManager(layoutManager);
                                historyAdapter.notifyDataSetChanged();
                                Log.d("res", "historyResponse: "+response);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id_pinjaman",idPinjaman);
                params.put("id_user",idUser);
                return params;
            }
        };
        Singleton.getInstance(DetailHistoryActivity.this).addToRequestQue(stringRequest);
    }
}
