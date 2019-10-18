package com.amos.koperasi;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amos.koperasi.Adapter.DalamCicilanAdapter;
import com.amos.koperasi.Adapter.NotifikasiAdminAdapter;
import com.amos.koperasi.Model.DalamCicilanModel;
import com.amos.koperasi.Utility.Singleton;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DalamCicilan extends Fragment {

    TextView nama,total,sisa;
    public DalamCicilan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final RecyclerView recyclerView;
        DalamCicilanAdapter adapter;
        RecyclerView.LayoutManager layoutManager;
        final List<DalamCicilanModel> list;

        final View view = inflater.inflate(R.layout.fragment_dalam_cicilan, container, false);
        recyclerView = view.findViewById(R.id.rvdalamcicilan);
        list = new ArrayList<>();
        adapter = new DalamCicilanAdapter(getActivity(),list);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        String url= "http://192.168.42.205/koperasi_API/dalamcicilan.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                            for (int i = 0 ; i< jsonArray.length();i++) {
                                JSONObject product = jsonArray.getJSONObject(i);
                                list.add(new DalamCicilanModel(
                                        product.getString("nama"),
                                        product.getString("total"),
                                        product.getString("sisa_cicilan"),
                                        product.getString("id_pinjaman"),
                                        product.getString("cicilan ke")

                                ));
                            }
                            DalamCicilanAdapter notifikasiAdminAdapter = new DalamCicilanAdapter(getActivity(),list);
                            recyclerView.setAdapter(notifikasiAdminAdapter);
                            notifikasiAdminAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("gagal dude", "onErrorResponse: "+error.toString());
            }
        });
        Singleton.getInstance(getActivity()).addToRequestQue(stringRequest);
        return view;
    }

}
