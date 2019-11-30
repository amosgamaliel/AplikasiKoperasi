package com.amos.koperasi.Fragment.Admin;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amos.koperasi.Adapter.DalamCicilanAdapter;
import com.amos.koperasi.Model.DalamCicilanModel;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class DalamCicilan extends Fragment {

    TextView nama,total,sisa;
    public DalamCicilan() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    DalamCicilanAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<DalamCicilanModel> list;
    Button cek,next,prev;
    EditText blne;
    TextView blnt;
    SharedPreferenceConfig sharedPreferenceConfig;
    static String url;
    String duar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_dalam_cicilan, container, false);
        recyclerView = view.findViewById(R.id.rvdalamcicilan);
        sharedPreferenceConfig =  new SharedPreferenceConfig(getActivity());
        blnt = view.findViewById(R.id.tvBulan);
        next = view.findViewById(R.id.btnNext);
        prev = view.findViewById(R.id.btnPrev);
        final DateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
        final Date date = new Date();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final String bulan = dateFormat.format(date);
        blnt.setText(bulan);
        url = sharedPreferenceConfig.getUrl()+"dalamcicilan.php";
        list = new ArrayList<>();
        final DalamCicilanAdapter adapter = new DalamCicilanAdapter(getActivity(),list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();





        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,1);
                Date dude = calendar.getTime();
                String bulan = dateFormat.format(dude);
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "yyyy-MM-01");
                Date date =  calendar.getTime();
                duar = dateFormat.format(date);
                blnt.setText(bulan);
                adapter.clear();
                adapter.tambahBulan(1);
                adapter.notifyDataSetChanged();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i = 0 ; i< jsonArray.length();i++) {
                                        JSONObject product = jsonArray.getJSONObject(i);
                                        list.add(new DalamCicilanModel(
                                                product.getString("jatuh_tempo"),
                                                product.getString("id_cicilan"),
                                                duar,
                                                product.getString("nama"),
                                                product.getInt("jumlah_pinjaman"),
                                                product.getInt("lunas"),
                                                product.getString("id_pinjaman"),
                                                product.getString("tenor"),
                                                product.getString("id_user"),
                                                product.getString("jumlah_cicilan")
                                        ));
                                    }
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

                                    Log.d("sukses", "onResponse: "+response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("gagal dude", "onErrorResponse: "+error.toString());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("bulan",duar);
                        return params;
                    }
                };
                Singleton.getInstance(getActivity()).addToRequestQue(stringRequest);
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,-1);
                Date dude = calendar.getTime();
                String bulan = dateFormat.format(dude);

                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "yyyy-MM-01");
                Date date =  calendar.getTime();
                duar = dateFormat.format(date);

                adapter.notifyDataSetChanged();
                blnt.setText(bulan);
                adapter.kurangBulan(1);
                adapter.clear();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i = 0 ; i< jsonArray.length();i++) {
                                        JSONObject product = jsonArray.getJSONObject(i);
                                        list.add(new DalamCicilanModel(
                                                product.getString("jatuh_tempo"),
                                                product.getString("id_cicilan"),
                                                duar,
                                                product.getString("nama"),
                                                product.getInt("jumlah_pinjaman"),
                                                product.getInt("lunas"),
                                                product.getString("id_pinjaman"),
                                                product.getString("tenor"),
                                                product.getString("id_user"),
                                                product.getString("jumlah_cicilan")));
                                    }
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("gagal dude", "onErrorResponse: "+error.toString());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("bulan",duar);
                        return params;
                    }
                };

                Singleton.getInstance(getActivity()).addToRequestQue(stringRequest);
            }
        });

        return view;
    }


}
