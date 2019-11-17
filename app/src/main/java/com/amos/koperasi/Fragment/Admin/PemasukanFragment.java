package com.amos.koperasi.Fragment.Admin;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amos.koperasi.Adapter.PemasukanAdapter;
import com.amos.koperasi.Model.ActivityModel;
import com.amos.koperasi.R;
import com.amos.koperasi.Utility.SharedPreferenceConfig;
import com.amos.koperasi.Utility.Singleton;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PemasukanFragment extends Fragment {


    public PemasukanFragment() {
        // Required empty public constructor
    }

    PemasukanAdapter adapter;
    ArrayList<ActivityModel> list = new ArrayList<>();
    RecyclerView rv;
    LinearLayoutManager layoutManager;
    String url;
    SharedPreferenceConfig sharedPreferenceConfig;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pemasukan, container, false);
        rv = view.findViewById(R.id.rvpemasukan);
        layoutManager = new LinearLayoutManager(getActivity());
        sharedPreferenceConfig = new SharedPreferenceConfig(getActivity());
        url = sharedPreferenceConfig.getUrl()+"detailpemasukan.php";
        getData();
        adapter = new PemasukanAdapter(list,getActivity());
        rv.setAdapter(adapter);
        rv.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();

        return view;
    }

    private void getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length();i++){
                                JSONObject activity = array.getJSONObject(i);
                                list.add(new ActivityModel(
                                        activity.getString("id_user"),
                                        activity.getString("id_pinjaman"),
                                        activity.getString("nama"),
                                        activity.getString("jumlah"),
                                        activity.getString("tanggal"),
                                        activity.getString("tipe")
                                ));
                                adapter = new PemasukanAdapter(list,getActivity());
                                rv.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Singleton.getInstance(getActivity()).addToRequestQue(stringRequest);
    }

}
