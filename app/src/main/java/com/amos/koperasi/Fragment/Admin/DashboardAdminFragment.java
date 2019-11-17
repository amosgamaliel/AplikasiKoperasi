package com.amos.koperasi.Fragment.Admin;


import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amos.koperasi.Activity.LoginActivity;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardAdminFragment extends Fragment {


    public DashboardAdminFragment() {
        // Required empty public constructor
    }

    CardView logout,history;
    TextView totaluang;
    String hasil,url;
    SharedPreferenceConfig sharedPreferenceConfig;
    LinearLayout pemasukan,pengeluaran;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_admin, container, false);
        logout = view.findViewById(R.id.logoutadmin);
        totaluang = view.findViewById(R.id.totalUang);
        history = view.findViewById(R.id.history);
        sharedPreferenceConfig = new SharedPreferenceConfig(getActivity());
        url = sharedPreferenceConfig.getUrl()+"tes.php";
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
        getData();
        totaluang.setText("Rp."+hasil);

        return view;
    }
    private void getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            hasil = jsonObject.getString("saldo_terakhir");
                            totaluang.setText("Rp."+hasil);

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
