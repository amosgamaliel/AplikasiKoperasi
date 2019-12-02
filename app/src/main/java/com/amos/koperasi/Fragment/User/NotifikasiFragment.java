package com.amos.koperasi.Fragment.User;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amos.koperasi.Adapter.ActivityUserAdapter;
import com.amos.koperasi.Adapter.AllActivityAdapter;
import com.amos.koperasi.Adapter.PemasukanAdapter;
import com.amos.koperasi.Model.ActivityModel;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class NotifikasiFragment extends Fragment {




    public NotifikasiFragment() {
        // Required empty public constructor
    }

    ActivityUserAdapter adapter;
    ArrayList<ActivityModel> list = new ArrayList<>();
    RecyclerView rv;
    LinearLayoutManager layoutManager;
    String url;
    SharedPreferenceConfig sharedPreferenceConfig;
    SharedPreferences mSettings;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifikasi, container, false);
        rv = view.findViewById(R.id.rvactivityuser);
        layoutManager = new LinearLayoutManager(getActivity());
        sharedPreferenceConfig = new SharedPreferenceConfig(getActivity());
        mSettings = PreferenceManager.getDefaultSharedPreferences(getContext());
        url = sharedPreferenceConfig.getUrl()+"activityuser.php";
        getData();
        adapter = new ActivityUserAdapter(list,getActivity());
        rv.setAdapter(adapter);
        rv.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
        return view;
    }

    private void getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            adapter.clear();
                            JSONArray array = new JSONArray(response);
                            Log.d("activity", "onResponse: "+response);
                            for (int i = 0; i<array.length();i++){
                                JSONObject activity = array.getJSONObject(i);
                                String tipe = activity.getString("tipe");
                                    list.add(new ActivityModel(
                                            activity.getString("id_pinjaman"),
                                            activity.getString("id_user"),
                                            "Anda",
                                            activity.getString("jumlah"),
                                            activity.getString("tanggal"),
                                            activity.getString("tipe")
                                    ));
                                adapter = new ActivityUserAdapter(list,getActivity());
                                rv.setLayoutManager(layoutManager);
                                rv.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("a", "onResponse: "+e.getMessage()+response);
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
                params.put("id_user",mSettings.getString("userid","1"));
                return params;
            }
        };
        Singleton.getInstance(getActivity()).addToRequestQue(stringRequest);
    }
}
