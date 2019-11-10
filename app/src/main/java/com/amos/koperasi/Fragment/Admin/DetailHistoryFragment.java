package com.amos.koperasi.Fragment.Admin;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amos.koperasi.Adapter.DetailHistoryAdapter;
import com.amos.koperasi.Adapter.HistoryAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailHistoryFragment extends Fragment {


    public DetailHistoryFragment() {
        // Required empty public constructor
    }
    String nama,tanggalm,tanggals,idUser,idPinjaman;
    TextView tvnama,tvtanggal;
    List<HistoryModel> list = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    DetailHistoryAdapter historyAdapter;
    SharedPreferenceConfig sharedPreferenceConfig;
    String url;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_history, container, false);
        recyclerView = view.findViewById(R.id.rvdh);
        historyAdapter = new DetailHistoryAdapter(list,getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();
        sharedPreferenceConfig = new SharedPreferenceConfig(getActivity());
        url = sharedPreferenceConfig.getUrl()+ "detailhistory.php";
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            nama = bundle.getString("NAMA","kosong");
            tanggalm = bundle.getString("TANGGAL_MULAI","kosong");
            tanggals = bundle.getString("TANGGAL_SELESAI","kosng");
            idUser = bundle.getString("ID_USER","user");
            idPinjaman = bundle.getString("ID_PINJAMAN","pinjaman");
        }
        getData();
        return view;
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
                                recyclerView.setAdapter(historyAdapter);
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
                params.put("idpin",idPinjaman);
                params.put("iduser",idUser);
                return params;
            }
        };
        Singleton.getInstance(getActivity()).addToRequestQue(stringRequest);
    }


}
