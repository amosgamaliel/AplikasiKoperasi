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
import android.widget.TextView;

import com.amos.koperasi.Adapter.DetailCicilanAdapter;
import com.amos.koperasi.Model.DetailCicilanUserModel;
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

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class CicilanFragment extends Fragment {


    public CicilanFragment() {
        // Required empty public constructor
    }
    SharedPreferences mSettings;
    TextView jumlah,nama,tenor,tanggals,tanggalm;
    RecyclerView recyclerView;
    ArrayList<DetailCicilanUserModel> arrayList = new ArrayList<>();
    SharedPreferenceConfig sharedPreferenceConfig;
    String url,idpinjaman;
    DetailCicilanAdapter disetujuiAdapter;
    LinearLayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cicilan, container, false);
        jumlah = view.findViewById(R.id.jumlahpew);
        nama = view.findViewById(R.id.namapw);
        tenor = view.findViewById(R.id.tenorpew);
        tanggals = view.findViewById(R.id.tanggals);
        tanggalm =view.findViewById(R.id.tanggalm);
        recyclerView = view.findViewById(R.id.rvdetailcicilanuser);
        sharedPreferenceConfig =  new SharedPreferenceConfig(getActivity());
        url = sharedPreferenceConfig.getUrl()+"disetujui.php";
        disetujuiAdapter = new DetailCicilanAdapter(getActivity(),arrayList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(disetujuiAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        disetujuiAdapter.notifyDataSetChanged();
        mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(final String response) {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String namaw = jsonObject.getString("nama");
                                    idpinjaman = jsonObject.getString("id_pinjaman");
                                    String tanggalw = jsonObject.getString("tanggal_mulai");
                                    String tanggale = jsonObject.getString("tanggal_selesai");
                                    String jumlahw = jsonObject.getString("jumlah");
                                    String tenorw = jsonObject.getString("tenor");
                                    String jatuhw = jsonObject.getString("jatuh");
                                    nama.setText(namaw);
                                    tanggalm.setText(tanggalw);
                                    tenor.setText(String.valueOf(tenorw));
                                    tanggals.setText(tanggale);
                                    jumlah.setText("Rp. "+String.valueOf(jumlahw));
                                    Log.d("tes", "isiResponse: "+response);
                                    getData2();
                                    recyclerView.setLayoutManager(layoutManager);
                                    recyclerView.setAdapter(disetujuiAdapter);
                                    disetujuiAdapter.notifyDataSetChanged();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d(TAG, "onResponsegagal: "+e.getMessage());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", "error: "+error.toString());
                    }
                }){@Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id_user",mSettings.getString("userid","1"));
                    return params;
                }};
                Singleton.getInstance(getActivity()).addToRequestQue(stringRequest);
        return view;
    }

    private void getData2(){
        url = sharedPreferenceConfig.getUrl()+"disetujui2.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        try {
                            ArrayList<DetailCicilanUserModel> list = new ArrayList<>();
                            JSONArray array = new JSONArray(response);
                            for (int i = 0 ; i< array.length();i++){
                                JSONObject product = array.getJSONObject(i);
                                list.add(new DetailCicilanUserModel(
                                        product.getInt("jumlah"),
                                        product.getString("tanggal_bayar"),
                                        product.getString("status")
                                ));
                                Log.d("hm", "onResponse: "+response);
                            }
                            disetujuiAdapter = new DetailCicilanAdapter(getActivity(),list);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(disetujuiAdapter);
                            disetujuiAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "error: "+error.toString());
            }
        }){@Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("id_user",mSettings.getString("userid","1"));
            params.put("id_pinjaman",idpinjaman);
            return params;
        }};
        Singleton.getInstance(getActivity()).addToRequestQue(stringRequest);
    }

}
