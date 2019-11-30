package com.amos.koperasi.Fragment.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.amos.koperasi.Adapter.CekIuranWajibAdapter;
import com.amos.koperasi.Model.IuranWajibModel;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CekWajibActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SharedPreferenceConfig sharedPreferenceConfig;
    String url;
    CekIuranWajibAdapter adapter;
    ArrayList<IuranWajibModel> list = new ArrayList<>();
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_wajib);

        recyclerView = findViewById(R.id.rvcekwajib);
        sharedPreferenceConfig = new SharedPreferenceConfig(this);
        url = sharedPreferenceConfig.getUrl()+"cekwajib.php";
        layoutManager = new LinearLayoutManager(this);

        getData();
        CekIuranWajibAdapter adapter = new CekIuranWajibAdapter(this,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
    void getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i<jsonArray.length();i++){
                                JSONObject data = jsonArray.getJSONObject(i);
                                list.add(new IuranWajibModel(
                                        data.getString("id_user"),
                                        data.getString("nama"),
                                        data.getString("nominal"),
                                        data.getString("tanggal")
                                ));
                            }
                            CekIuranWajibAdapter adapter = new CekIuranWajibAdapter(CekWajibActivity.this,list);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(layoutManager);
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
                params.put("bulan",getDateTime());
                return params;
            }

        };
        Singleton.getInstance(CekWajibActivity.this).addToRequestQue(stringRequest);
    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-01");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
