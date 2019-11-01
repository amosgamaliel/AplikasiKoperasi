package com.amos.koperasi.Fragment.Admin;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amos.koperasi.Adapter.NotifikasiAdminAdapter;
import com.amos.koperasi.Model.InfoPengajuan;
import com.amos.koperasi.R;
import com.amos.koperasi.Utility.SharedPreferenceConfig;
import com.amos.koperasi.Utility.Singleton;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotifikasiAdminFragment extends Fragment {

    List<InfoPengajuan> list;
    RecyclerView recyclerView;
    SharedPreferenceConfig sharedPreferenceConfig;
    String url;
    public NotifikasiAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifikasi_admin, container, false);
        list = new ArrayList<>();
        sharedPreferenceConfig =  new SharedPreferenceConfig(getActivity());
        url = sharedPreferenceConfig.getUrl()+"listpinjaman.php";
        recyclerView = view.findViewById(R.id.rvcontainer);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        NotifikasiAdminAdapter notifikasiAdminAdapter = new NotifikasiAdminAdapter(list,getActivity());

        recyclerView.setAdapter(notifikasiAdminAdapter);
        notifikasiAdminAdapter.notifyDataSetChanged();


        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0 ; i< array.length();i++){
                        JSONObject product = array.getJSONObject(i);
                        list.add(new InfoPengajuan(
                                product.getInt("id"),
                                product.getString("iduser"),
                                product.getString("nama"),
                                product.getInt("jumlah"),
                                product.getInt("tenor"),
                                product.getInt("jatuh")
                        ));

                    }NotifikasiAdminAdapter notifikasiAdminAdapter = new NotifikasiAdminAdapter(list,getActivity());
                    recyclerView.setAdapter(notifikasiAdminAdapter);
                    notifikasiAdminAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Berhasil", "onResponse: Berhasil"+response+"\n"+list);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Gagal", "onResponse: Berhasil");
            }
        });
        Singleton.getInstance(getActivity()).addToRequestQue(stringRequest);
        return view;
    }

    }


