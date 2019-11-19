package com.amos.koperasi.Fragment.Admin;


import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amos.koperasi.Activity.LoginActivity;
import com.amos.koperasi.Adapter.AllActivityAdapter;
import com.amos.koperasi.Adapter.PemasukanAdapter;
import com.amos.koperasi.Adapter.PengeluaranAdapter;
import com.amos.koperasi.Model.ActivityModel;
import com.amos.koperasi.Model.NamaPenyimpanModel;
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
public class DashboardAdminFragment extends Fragment {


    public DashboardAdminFragment() {
        // Required empty public constructor
    }
    LinearLayoutManager layoutManager;
    CardView logout,history,setsimpanan;
    ArrayList<ActivityModel> list;
    AllActivityAdapter activityAdapter;
    TextView totaluang;
    String hasil,url,spemasukan,spengeluaran;
    SharedPreferenceConfig sharedPreferenceConfig;
    LinearLayout pemasukan,pengeluaran;
    TextView totalPemasukan,totalPengeluaran;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_admin, container, false);
        logout = view.findViewById(R.id.logoutadmin);
        totaluang = view.findViewById(R.id.totalUang);
        totalPemasukan = view.findViewById(R.id.totalPemasukan);
        totalPengeluaran = view.findViewById(R.id.totalPengeluaran);
        setsimpanan = view.findViewById(R.id.setsimpanan);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView = view.findViewById(R.id.rv_daily_detail);
        history = view.findViewById(R.id.history);
        sharedPreferenceConfig = new SharedPreferenceConfig(getActivity());
        url = sharedPreferenceConfig.getUrl()+"dashboard.php";
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceConfig sharedPreferenceConfig;
                sharedPreferenceConfig = new SharedPreferenceConfig(getActivity());
                sharedPreferenceConfig.writeLoginAdminStatus(false);
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        pengeluaran = view.findViewById(R.id.pengeluaran);
        pemasukan = view.findViewById(R.id.pemasukan);
        pemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_containera, new PemasukanFragment()).addToBackStack(null)
                        .commit();
            }
        });
        pengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_containera, new PengeluaranFragment()).addToBackStack(null)
                        .commit();
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_containera, new HistoryFragment()).addToBackStack(null)
                        .commit();
            }
        });
        setsimpanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_containera, new SetSimpananFragment()).addToBackStack(null)
                        .commit();
            }
        });
        getDataText();
        getData();
        totaluang.setText("Rp."+hasil);
        list = new ArrayList<>();
        activityAdapter = new AllActivityAdapter(list,getActivity());
        recyclerView.setAdapter(activityAdapter);
        recyclerView.setLayoutManager(layoutManager);
        activityAdapter.notifyDataSetChanged();

        return view;
    }
    private void getDataText(){
        url = sharedPreferenceConfig.getUrl()+"dashboard.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            hasil = jsonObject.getString("saldo_terakhir");
                            spemasukan = jsonObject.getString("pemasukan");
                            spengeluaran = jsonObject.getString("pengeluaran");
                            totaluang.setText("Rp."+hasil);
                            totalPemasukan.setText(spemasukan);
                            totalPengeluaran.setText(spengeluaran);
                            Log.d("der", "onResponse: "+response);
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
    private void getData(){
        String url = url = sharedPreferenceConfig.getUrl()+"detailpemasukan.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            list = new ArrayList<>();
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
                                activityAdapter = new AllActivityAdapter(list,getActivity());
                                recyclerView.setAdapter(activityAdapter);
                                recyclerView.setLayoutManager(layoutManager);
                                activityAdapter.notifyDataSetChanged();
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
