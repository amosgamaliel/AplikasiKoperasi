package com.amos.koperasi;


import android.app.DownloadManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


/**
 * A simple {@link Fragment} subclass.
 */
public class DahboardFragment extends Fragment {


    public DahboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dahboard, container, false);
        TextView total,sukarela,wajib,tahara;
        total = view.findViewById(R.id.total);
        sukarela = view.findViewById(R.id.sukarela);
        wajib = view.findViewById(R.id.wajib);
        tahara = view.findViewById(R.id.tahara);

//        tampilRekap();
        return view;

    }

    private void tampilRekap() {
        String url = "http://102.168.1.8/tampilrekap.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

}
