package com.amos.koperasi.Fragment.User;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.amos.koperasi.Adapter.DisetujuiAdapter;
import com.amos.koperasi.Activity.LoginActivity;
import com.amos.koperasi.Model.NotifikasiDisetujui;
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
public class DahboardFragment extends Fragment {


    public DahboardFragment() {
        // Required empty public constructor
    }
    List<NotifikasiDisetujui> listp;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dahboard, container, false);
        TextView total,sukarela,wajib,tahara;
        Button logout,amos;
        listp = new ArrayList<>();

        amos=view.findViewById(R.id.amos);
        recyclerView = view.findViewById(R.id.disetujui_container);
        total = view.findViewById(R.id.total);
        sukarela = view.findViewById(R.id.sukarela);
        wajib = view.findViewById(R.id.wajib);
        tahara = view.findViewById(R.id.tahara);
        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogout();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        DisetujuiAdapter disetujuiAdapter = new DisetujuiAdapter(listp,getActivity());
        recyclerView.setAdapter(disetujuiAdapter);
        disetujuiAdapter.notifyDataSetChanged();
        final SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String url = "http://192.168.1.8/koperasi_API/disetujui.php" ;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray list = new JSONArray(response);
                            for (int i = 0; i<list.length();i++){
                                JSONObject info = list.getJSONObject(i);
                                listp.add(new NotifikasiDisetujui(
                                        info.getString("tanggal"),
                                        info.getInt("jumlah")
                                ));
                                DisetujuiAdapter disetujuiAdapter = new DisetujuiAdapter(listp,getActivity());
                                recyclerView.setAdapter(disetujuiAdapter);
                                disetujuiAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){@Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("id",mSettings.getString("userid","1"));
            return params;
        }};

        Singleton.getInstance(getActivity()).addToRequestQue(stringRequest);
        return view;
    }


    public void userLogout(){
        SharedPreferenceConfig sharedPreferenceConfig;
        sharedPreferenceConfig = new SharedPreferenceConfig(getActivity());
        sharedPreferenceConfig.writeLoginUserStatus(false);
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

}
