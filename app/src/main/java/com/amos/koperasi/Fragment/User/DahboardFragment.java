package com.amos.koperasi.Fragment.User;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amos.koperasi.Activity.LoginActivity;
import com.amos.koperasi.Fragment.Admin.HistoryFragment;
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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
    String idUser,url,total_user,total_wajib,total_sukarela,total_tahara;
    SharedPreferences mSettings;
    String idpinjaman;
    LinearLayout layout;
    RelativeLayout relativeLayout;
    SharedPreferenceConfig sharedPreferenceConfig;
    TextView nama,jumlahpinjaman,tanggalm,tanggals,today;
    Button btnDetail;
    TextView total,sukarela,wajib,tahara,wajibpb,cicilanpb,taharapb,keterangan;
    DecimalFormat kursIndonesia;
    TextView tanggaldisetujui;
    String action;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dahboard, container, false);

        CardView logout,amos;
        listp = new ArrayList<>();

        kursIndonesia = (DecimalFormat)DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);



        sharedPreferenceConfig = new SharedPreferenceConfig(getActivity());
        url = sharedPreferenceConfig.getUrl()+"infopinjamanuser.php";
        mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        idUser = mSettings.getString("userid","1");
        keterangan = view.findViewById(R.id.keterangan);
        jumlahpinjaman = view.findViewById(R.id.totalUang);
        tanggalm = view.findViewById(R.id.tanggalmulai);
        tanggals =view.findViewById(R.id.tanggal_selesai);
        layout = view.findViewById(R.id.ngutang);
        relativeLayout = view.findViewById(R.id.kosong);
        btnDetail = view.findViewById(R.id.btndetail);
        wajib = view.findViewById(R.id.wajib);
        tahara = view.findViewById(R.id.tahara);
        today = view.findViewById(R.id.today);
        wajibpb = view.findViewById(R.id.iuranwajib);
        taharapb = view.findViewById(R.id.iuran_tahara);
        total = view.findViewById(R.id.total);
        cicilanpb = view.findViewById(R.id.cicilan_pinjaman);
        today.setText("Hari ini, "+getDateTime());
        sukarela = view.findViewById(R.id.sukarela);
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CicilanFragment cicilanFragment = new CicilanFragment();
                ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, cicilanFragment).addToBackStack(null)
                        .commit();
                Bundle bundle = new Bundle();
                bundle.putString("ACTION", action);
                cicilanFragment.setArguments(bundle);
                }
            });
        getData();
        getData2();
        return view;
    }
    private void getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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
                            String status = jsonObject.getString("status");

                            if (status.equals("lunas")){
                                relativeLayout.setVisibility(View.VISIBLE);
                                layout.setVisibility(View.GONE);
                            }else if (status.equals("disetujui")||status.equals("diterima")){
                                if (status.equals("disetujui")){
                                    action = "ESTIMASI";
                                }else {
                                    action = "PASTI";
                                }
                                String x = kursIndonesia.format(Double.parseDouble(jumlahw));
                                jumlahpinjaman.setText(x);
                                tanggalm.setText(tanggalw);
                                tanggals.setText(tanggale);
                            }else if (status.equals("belum")){
                                relativeLayout.setVisibility(View.VISIBLE);
                                layout.setVisibility(View.GONE);
                                keterangan.setText("Pengajuan pinjaman anda sedang ditinjau oleh admin");
                            }

                            Log.d("ya", "onResponse: "+status+response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("a", "onResponse: "+e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user",idUser);
                return params;
            }
        };
        Singleton.getInstance(getActivity()).addToRequestQue(stringRequest);
    }

    private void getData2(){
        String url = sharedPreferenceConfig.getUrl()+"dashboarduser.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("bebas", "onResponse: Hmm"+response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String totalw = jsonObject.getString("total_wajib");
                            String totalt = jsonObject.getString("total_tahara");
                            String totals = jsonObject.getString("total_sukarela");
                            String pbcicilan = jsonObject.getString("jumlah_cicilan");
                            String wajibpbs = jsonObject.getString("jumlah_wajib");
                            String taharapbs = jsonObject.getString("jumlah_tahara");





                            if (pbcicilan.equals("null")){
                                cicilanpb.setText("Rp. 0,00");
                                pbcicilan = "0";
                            }else {
                                String x = kursIndonesia.format(Double.parseDouble(pbcicilan));
                                cicilanpb.setText(x);
                            }
                            if (wajibpbs.equals("null")){
                                wajibpb.setText("Rp. 0,00");
                            }else {
                                String x = kursIndonesia.format(Double.parseDouble(wajibpbs));
                                wajibpb.setText(x);
                            }
                            if (pbcicilan.equals("null")){
                                taharapb.setText("Rp. 0,00");
                            }else {
                                String x = kursIndonesia.format(Double.parseDouble(taharapbs));
                                taharapb.setText(x);
                            }

                            if (totalw.equals("null")){
                                wajib.setText("Rp. 0,00");
                            }else {
                                String x = kursIndonesia.format(Double.parseDouble(totalw));
                                wajib.setText(x);
                            }
                            if (totals.equals("null")){
                                sukarela.setText("Rp. 0,00");
                            }else {
                                String x = kursIndonesia.format(Double.parseDouble(totals));
                                sukarela.setText(x);
                            }
                            if (totalt.equals("null")){
                                tahara.setText("Rp. 0,00");
                            }else {
                                String x = kursIndonesia.format(Double.parseDouble(totalt));
                                tahara.setText(x);
                            }
                            int jcicilan = Integer.parseInt(pbcicilan);
                            int jwajib = Integer.parseInt(wajibpbs);
                            int jtahara = Integer.parseInt(taharapbs);
                            int jtotal = jcicilan+jwajib+jtahara;

                            String y = kursIndonesia.format(jtotal);
                            total.setText(y);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("wey", "onResponse: "+e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user",idUser);
                params.put("awalbulan",getDateAwal());
                params.put("akhirbulan",getDateAkhir());
                return params;
            }
        };
        Singleton.getInstance(getActivity()).addToRequestQue(stringRequest);
    }
    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd MMMM yyyy", Locale.getDefault());
//        Date date = new Date();
        Date date = new Date();
        return dateFormat.format(date);
    }
    private String getDateAwal() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyyMM01");
        Date date = new Date();
        return dateFormat.format(date);
    }
    private String getDateAkhir() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyyMM31");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
