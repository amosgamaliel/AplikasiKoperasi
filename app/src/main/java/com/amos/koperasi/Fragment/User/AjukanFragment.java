package com.amos.koperasi.Fragment.User;


import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amos.koperasi.Activity.AdminActivity;
import com.amos.koperasi.Activity.LoginActivity;
import com.amos.koperasi.Adapter.DetailCicilanAdapter;
import com.amos.koperasi.Model.DetailCicilanModel;
import com.amos.koperasi.Model.DetailCicilanUserModel;
import com.amos.koperasi.R;
import com.amos.koperasi.Utility.SharedPreferenceConfig;
import com.amos.koperasi.Utility.Singleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class AjukanFragment extends Fragment {
    Context mCtx;
    Button btnAjukan,btnDetail;
    Spinner spinner;
    double sum;
    EditText jumlah,tenor;
    TextView total,terbilang;
    AlertDialog.Builder builder ;
    View dialogView;
    int bln;
    private final String CHANNEL_ID = "personal_notification";
    private final int NOTIFICATION_ID = 001;

    double hasil;
    SharedPreferenceConfig sharedPreferenceConfig;
    ArrayList<DetailCicilanUserModel> arrayList = new ArrayList<>();
    String url,bulan;
    DetailCicilanAdapter adapter;
    String jabatan;
    SharedPreferences mSettings;

    public AjukanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_ajukan, container, false);
        btnAjukan = view.findViewById(R.id.ajukan);

        spinner = view.findViewById(R.id.spinner);
        terbilang = view.findViewById(R.id.terbilang);
        jumlah = view.findViewById(R.id.jumlah);
        total = view.findViewById(R.id.total);

        final DecimalFormat kursIndonesia = (DecimalFormat)DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        sharedPreferenceConfig =  new SharedPreferenceConfig(getContext());
        url = sharedPreferenceConfig.getUrl()+"peminjaman.php";
        mSettings = PreferenceManager.getDefaultSharedPreferences(getContext());
        jabatan = mSettings.getString("jabatan","user");
        adapter = new DetailCicilanAdapter(getActivity(),arrayList);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        final RecyclerView recyclerView = view.findViewById(R.id.rvdetail);
        recyclerView.setNestedScrollingEnabled(false);
        bulan = spinner.getSelectedItem().toString();

        jumlah.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.clear();
                total.setVisibility(View.VISIBLE);
                terbilang.setVisibility(View.VISIBLE);
                int pos= spinner.getSelectedItemPosition();
                String[] value = getResources().getStringArray(R.array.bulan);
                final int bulann = Integer.valueOf(value[pos]);
                bln = bulann;
                String jumlahs = jumlah.getText().toString();
                int iJumlah ;
                if (jumlahs.equals("")){
                    iJumlah = 0;
                    adapter.clear();
                    recyclerView.setVisibility(View.GONE);
                }else{
                    recyclerView.setVisibility(View.VISIBLE);
                    iJumlah =Integer.parseInt(jumlahs);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            adapter.clear();
                            sum = 0;
                            int pos= spinner.getSelectedItemPosition();
                            String[] value = getResources().getStringArray(R.array.bulan);
                            final int bulann = Integer.valueOf(value[pos]);
                            bln = bulann;
                            String jumlahs = jumlah.getText().toString();
                            int iJumlah ;
                            if (jumlahs.equals("")){
                                iJumlah = 0;
                            }else{
                                iJumlah =Integer.parseInt(jumlahs);
                            }
                            final int perBulan = iJumlah/bln;
                            ArrayList<Integer> integers = new ArrayList<>();
                            terbilang.setText(kekata(iJumlah));
                            for (int i = 0; i< bln ; i++){
                                int sisa = iJumlah - perBulan*i;
                                int cicilan = (int) (perBulan+(sisa*0.02));

                                arrayList.add(new DetailCicilanUserModel(
                                        cicilan,
                                        getJatuhTempo(i),
                                        "kosong"
                                ));
                                integers.add(cicilan);
                                hasil =sum += cicilan;

                            }

                            String f = kursIndonesia.format(sum);
                            total.setText(f);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                            Log.d("ba", "cekJabatan"+jabatan);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            recyclerView.setVisibility(View.GONE);
                        }
                    });
                }
                final int perBulan = iJumlah/bln;
                ArrayList<Integer> integers = new ArrayList<>();
                terbilang.setText(kekata(iJumlah));
                for (int i = 0; i< bln ; i++){
                    int sisa = iJumlah - perBulan*i;
                    int cicilan = (int) (perBulan+(sisa*0.02));

                    arrayList.add(new DetailCicilanUserModel(
                            cicilan,
                            getJatuhTempo(i),
                            "kosong"
                    ));
                    integers.add(cicilan);
                    hasil =sum += cicilan;
                }
                String f = kursIndonesia.format(sum);
                total.setText(f);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        });



        btnAjukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= spinner.getSelectedItemPosition();
                String[] value = getResources().getStringArray(R.array.bulan);
                if (jumlah.getText().toString().equals("")||
                    total.getText().toString().equals("")){
                    jumlah.setError("Field harus diisi");
                }else {
                    showDialog();
                }
            }
        });
    return view;
}

    String kekata (int x) {
        x = Math.abs(x);
        long m = 1000000000000L;
        String [] angka = new String[]{
            "", "satu", "dua", "tiga", "empat", "lima",
                    "enam", "tujuh", "delapan", "sembilan", "sepuluh", "sebelas"
        };

        String $temp = "";
        if (x <12) {
            $temp = " "+ angka[x];
        } else if (x <20) {
            $temp = kekata(x - 10)+ " belas";
        } else if (x <100) {
            $temp = kekata(x/10)+" puluh"+ kekata(x % 10);
        } else if (x <200) {
            $temp = " seratus"+ kekata(x - 100);
        } else if (x <1000) {
            $temp = kekata(x/100) + " ratus" +kekata(x % 100);
        } else if (x <2000) {
            $temp = " seribu" + kekata(x - 1000);
        } else if (x <1000000) {
            $temp = kekata(x/1000) +" ribu" + kekata(x % 1000);
        } else if (x <1000000000) {
            $temp = kekata(x/1000000) + " juta" + kekata(x % 1000000);
        }
        return $temp;
    }

    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyyMMdd", Locale.getDefault());
//        Date date = new Date();
        Date date = new Date();
        return dateFormat.format(date);
    }
    public String getDateAkhir() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyyMMdd", Locale.getDefault());
//        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,bln);
        Date date = calendar.getTime();

        return dateFormat.format(date);
    }
    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name = "Personal Notification";
            String description = "Include all the personal notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
    public String getJatuhTempo(int ok) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd MMMM yyyy", Locale.getDefault());
//        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,ok);
        Date date = calendar.getTime();

        return dateFormat.format(date);
    }
    private void showDialog(){
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Konfirmasi");
        builder.setMessage("Apakah anda yakin ingin mengajukan?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yakin", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getData();
                adapter.clear();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    void getData(){
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Log.d("cek", "onResponse: "+response);
                                String status = jsonObject.getString("code");
                                if (status.equals("200")){
                                    builder = new AlertDialog.Builder(getActivity());
                                    builder.setTitle("Berhasil");
                                    builder.setMessage("Pengajuan peminjaman anda sudah terkirim ke admin");
                                    builder.setCancelable(false);

                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            jumlah.setText("");
                                            dialog.dismiss();

                                        }
                                    });
                                    builder.show();
                                }else if (status.equals("501")){
                                    builder = new AlertDialog.Builder(getActivity());
                                    builder.setTitle("Tidak bisa mengajukan");
                                    builder.setMessage("Anda hanya bisa mengajukan 1 pengajuan dalam 1 waktu");
                                    builder.setCancelable(false);

                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            jumlah.setText("");
                                            dialog.dismiss();

                                        }
                                    });
                                    builder.show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("gagal tapi bae", "wleeee"+mSettings.getString("userid","1"));
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("jumlah_pinjaman",jumlah.getText().toString());
                    params.put("tenor",String.valueOf(bln));
                    params.put("total_pinjaman",String.valueOf(sum));
                    params.put("id_user",mSettings.getString("userid","1"));
                    params.put("tanggal",getDateTime());
                    params.put("tanggal_selesai",getDateAkhir());
                    return params;
                }
            };
            Singleton.getInstance(getContext()).addToRequestQue(stringRequest);
        }
}
