package com.amos.koperasi.Fragment.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amos.koperasi.Adapter.DetailCicilanAdapter;
import com.amos.koperasi.Model.DetailCicilanUserModel;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class DetailTransaksiActivity extends AppCompatActivity {

    SharedPreferences mSettings;
    TextView jumlah,nama,tenor,tanggals,tanggalm;
    RecyclerView recyclerView;
    ArrayList<DetailCicilanUserModel> arrayList = new ArrayList<>();
    SharedPreferenceConfig sharedPreferenceConfig;
    String url,idpinjaman;
    DetailCicilanAdapter disetujuiAdapter;
    LinearLayoutManager layoutManager;
    String idUser,idPinjaman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_transaksi);
        idUser = getIntent().getStringExtra("ID_USER");
        idPinjaman = getIntent().getStringExtra("ID_PINJAMAN");

        jumlah = findViewById(R.id.jumlahpew);
        nama = findViewById(R.id.namapw);
        tenor = findViewById(R.id.tenorpew);
        tanggals = findViewById(R.id.tanggals);

        tanggalm =findViewById(R.id.tanggalm);
        recyclerView = findViewById(R.id.rvdetailcicilanuser);
        sharedPreferenceConfig =  new SharedPreferenceConfig(this);
        url = sharedPreferenceConfig.getUrl()+"detailpemasukan.php";
        disetujuiAdapter = new DetailCicilanAdapter(this,arrayList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(disetujuiAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        disetujuiAdapter.notifyDataSetChanged();
        mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        getData();
        getData2();

    }


    private void getData2(){
        url = sharedPreferenceConfig.getUrl()+"disetujui2.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        try {
                            ArrayList<DetailCicilanUserModel> list = new ArrayList<>();
                            JSONArray array = new JSONArray(response);
                            for (int i = 0 ; i< array.length();i++){
                                JSONObject product = array.getJSONObject(i);
                                list.add(new DetailCicilanUserModel(
                                        product.getInt("jumlah"),
                                        product.getString("tanggal_bayar"),
                                        product.getString("status")
                                ));
                                Log.d("hm", "onResponse: "+response);
                            }
                            disetujuiAdapter = new DetailCicilanAdapter(DetailTransaksiActivity.this,list);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(disetujuiAdapter);
                            disetujuiAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onResponseanu: "+e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "error: "+error.toString());
            }
        }){@Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("id_user",idUser);
            params.put("id_pinjaman",idPinjaman);
            return params;
        }};
        Singleton.getInstance(DetailTransaksiActivity.this).addToRequestQue(stringRequest);
    }

    private void getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String namaw = jsonObject.getString("nama");
                            idpinjaman = jsonObject.getString("id_pinjaman");
                            String tanggalw = jsonObject.getString("tanggal_mulai");
                            String tanggale = jsonObject.getString("tanggal_selesai");
                            int jumlahw = jsonObject.getInt("jumlah");
                            int tenorw = jsonObject.getInt("tenor");
                            nama.setText(namaw);
                            tanggalm.setText(tanggalw);
                            tenor.setText(String.valueOf(tenorw));
                            tanggals.setText(tanggale);
                            jumlah.setText("Rp. "+String.valueOf(jumlahw));
                            Log.d("tes", "isiResponse: "+response+"      "+idUser);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(disetujuiAdapter);
                            disetujuiAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onResponseanu: "+e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "error: "+error.toString());
            }
        }){@Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("id_user",idUser);
            return params;
        }};
        Singleton.getInstance(DetailTransaksiActivity.this).addToRequestQue(stringRequest);
    }

}
