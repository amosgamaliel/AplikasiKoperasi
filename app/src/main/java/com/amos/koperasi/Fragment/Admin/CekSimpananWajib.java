package com.amos.koperasi.Fragment.Admin;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class CekSimpananWajib extends Fragment {


    public CekSimpananWajib() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    SharedPreferenceConfig sharedPreferenceConfig;
    String url;
    CekIuranWajibAdapter adapter;
    ArrayList<IuranWajibModel> list = new ArrayList<>();
    LinearLayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cek_simpanan_wajib, container, false);
        recyclerView = view.findViewById(R.id.rvcekwajib);
        sharedPreferenceConfig = new SharedPreferenceConfig(getActivity());
        url = sharedPreferenceConfig.getUrl()+"cekwajib.php";
        layoutManager = new LinearLayoutManager(getActivity());

        getData();
        CekIuranWajibAdapter adapter = new CekIuranWajibAdapter(getActivity(),list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        return view;
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
                            CekIuranWajibAdapter adapter = new CekIuranWajibAdapter(getActivity(),list);
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

        };Singleton.getInstance(getActivity()).addToRequestQue(stringRequest);
    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-01");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
