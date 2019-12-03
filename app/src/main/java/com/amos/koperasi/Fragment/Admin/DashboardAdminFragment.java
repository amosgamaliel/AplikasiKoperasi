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
import com.amos.koperasi.Fragment.GantiPassword;
import com.amos.koperasi.Fragment.HistoryActivity;
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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardAdminFragment extends Fragment {


    public DashboardAdminFragment() {
        // Required empty public constructor
    }
    String kurs;
    LinearLayoutManager layoutManager;
    CardView komitmen,history,setsimpanan,cekwajib,gantipassword,penyerahan;
    ArrayList<ActivityModel> list;
    AllActivityAdapter activityAdapter;
    TextView totaluang;
    String hasil,url,spemasukan,spengeluaran;
    SharedPreferenceConfig sharedPreferenceConfig;
    LinearLayout pemasukan,pengeluaran;
    TextView totalPemasukan,totalPengeluaran,today,logout;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_admin, container, false);
        komitmen = view.findViewById(R.id.komitmen);
        totaluang = view.findViewById(R.id.totalUang);
        totalPemasukan = view.findViewById(R.id.totalPemasukan);
        totalPengeluaran = view.findViewById(R.id.totalPengeluaran);
        setsimpanan = view.findViewById(R.id.setsimpanan);
        cekwajib = view.findViewById(R.id.cekwajib);
        gantipassword = view.findViewById(R.id.changepass);
        penyerahan = view.findViewById(R.id.penyerahan);
        today = view.findViewById(R.id.today);
        logout = view.findViewById(R.id.logout);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView = view.findViewById(R.id.rv_daily_detail);
        history = view.findViewById(R.id.history);

        sharedPreferenceConfig = new SharedPreferenceConfig(getActivity());
        today.setText("Hari ini "+getDateTime());
        url = sharedPreferenceConfig.getUrl()+"dashboard.php";

        gantipassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_containera, new GantiPassword()).addToBackStack(null)
                        .commit();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceConfig sharedPreferenceConfig;
                sharedPreferenceConfig = new SharedPreferenceConfig(getActivity());
                sharedPreferenceConfig.writeLoginAdminStatus(false);
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
        komitmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_containera, new SetSimpananFragment()).addToBackStack(null)
                        .commit();
            }
        });
        penyerahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_containera, new MenyerahkanFragment()).addToBackStack(null)
                        .commit();
            }
        });
        cekwajib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_containera, new CekSimpananWajib()).addToBackStack(null)
                        .commit();
            }
        });

        pengeluaran = view.findViewById(R.id.pengeluaran);
        pemasukan = view.findViewById(R.id.pemasukan);
        pemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),PemasukanActivity.class);
                    startActivity(intent);
            }
        });
        pengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PengeluaranActivity.class);
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
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
        if (list.size() > 0){
            activityAdapter = new AllActivityAdapter(list,getActivity());
            recyclerView.setAdapter(activityAdapter);
            recyclerView.setLayoutManager(layoutManager);
            activityAdapter.notifyDataSetChanged();
        }
        recyclerView.setNestedScrollingEnabled(false);

        return view;
    }
    private void getDataText(){
        url = sharedPreferenceConfig.getUrl()+"dashboard.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            final DecimalFormat kursIndonesia = (DecimalFormat)DecimalFormat.getCurrencyInstance();
                            DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

                            formatRp.setCurrencySymbol("Rp. ");
                            formatRp.setMonetaryDecimalSeparator(',');
                            formatRp.setGroupingSeparator('.');
                            kursIndonesia.setDecimalFormatSymbols(formatRp);


                            JSONObject jsonObject = new JSONObject(response);
                            hasil = jsonObject.getString("saldo_terakhir");
                            spemasukan = jsonObject.getString("pemasukan");
                            spengeluaran = jsonObject.getString("pengeluaran");
                            if (hasil.equals("null")){
                                hasil = "0";
                            }
                            if (spemasukan.equals("null")){
                                spemasukan = "0";
                            }
                            if (spengeluaran.equals("null")){
                                spengeluaran = "0";
                            }
                            kurs = kursIndonesia.format(Double.parseDouble(hasil));
                            String kurspemasukan = kursIndonesia.format(Double.parseDouble(spemasukan));
                            String kurspengeluaran = kursIndonesia.format(Double.parseDouble(spengeluaran));
                            totaluang.setText(kurs);
                            totalPemasukan.setText(kurspemasukan);
                            totalPengeluaran.setText(kurspengeluaran);
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
        String url = url = sharedPreferenceConfig.getUrl()+"activity.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            list = new ArrayList<>();
                            for (int i = 0; i<7;i++){
                                JSONObject activity = array.getJSONObject(i);
                                list.add(new ActivityModel(
                                        activity.getString("id_pinjaman"),
                                        activity.getString("id_user"),
                                        activity.getString("nama"),
                                        activity.getString("jumlah"),
                                        activity.getString("tanggal"),
                                        activity.getString("tipe")
                                ));
                                if (list.size()>0){

                                activityAdapter = new AllActivityAdapter(list,getActivity());
                                recyclerView.setAdapter(activityAdapter);
                                recyclerView.setLayoutManager(layoutManager);
                                activityAdapter.notifyDataSetChanged();
                                }
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
    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd MMMM yyyy", Locale.getDefault());
//        Date date = new Date();
        Date date = new Date();
        return dateFormat.format(date);
    }

}
