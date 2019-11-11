package com.amos.koperasi.Fragment.User;


import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.amos.koperasi.Activity.AdminActivity;
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
    EditText jumlah,tenor;
    TextView total,terbilang;
    AlertDialog.Builder builder ;
    int bln;
    private final String CHANNEL_ID = "personal_notification";
    private final int NOTIFICATION_ID = 001;

    int hasil;
    SharedPreferenceConfig sharedPreferenceConfig;
    ArrayList<DetailCicilanUserModel> arrayList = new ArrayList<>();
    String url,bulan;
    String jabatan;

    public AjukanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_ajukan, container, false);
        btnAjukan = view.findViewById(R.id.ajukan);
        btnDetail = view.findViewById(R.id.detail);
        spinner = view.findViewById(R.id.spinner);
        terbilang = view.findViewById(R.id.terbilang);
        jumlah = view.findViewById(R.id.jumlah);
        total = view.findViewById(R.id.total);
//        sharedPreferenceConfig =  new SharedPreferenceConfig(getActivity());
//        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());

        sharedPreferenceConfig =  new SharedPreferenceConfig(getContext());
        url = sharedPreferenceConfig.getUrl()+"peminjaman.php";
        final SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(getContext());
        jabatan = mSettings.getString("jabatan","user");
        final DetailCicilanAdapter adapter = new DetailCicilanAdapter(getActivity(),arrayList);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        final RecyclerView recyclerView = view.findViewById(R.id.rvdetail);
        bulan = spinner.getSelectedItem().toString();

        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos= spinner.getSelectedItemPosition();
                String[] value = getResources().getStringArray(R.array.bulan);
                final int bulann = Integer.valueOf(value[pos]);
                bln = bulann;
                int iJumlah = Integer.parseInt(jumlah.getText().toString());
                final int perBulan = iJumlah/bln;
                ArrayList<Integer> integers = new ArrayList<>();
                terbilang.setText(kekata(iJumlah));
                for (int i = 0; i< bln ; i++){
                    int sisa = iJumlah - perBulan*i;
                    int cicilan = (int) (perBulan+(sisa*0.02));

                    arrayList.add(new DetailCicilanUserModel(
                            cicilan,
                            "tanggal bebas",
                            "kosong"
                    ));
                }

                int sum = 0;
                for(int i = 0; i < integers.size(); i++)
                    hasil =sum += integers.get(i);
                 total.setText(String.valueOf(hasil));   ;


                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                Log.d("ba", "cekJabatan"+jabatan);
            }
        });


        btnAjukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= spinner.getSelectedItemPosition();
                String[] value = getResources().getStringArray(R.array.bulan);
                if (jumlah.getText().toString().equals("")||
                    total.getText().toString().equals("")
                ){
                    builder.setTitle("Error");
                    builder.setMessage("Semua field harus diisi");
                }else {
                    StringRequest stringRequest = new StringRequest(
                            Request.Method.POST,
                            url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Log.d("cek", "onResponse: "+response);
                                        String nama = jsonObject.getString("nama");
                                        String status = jsonObject.getString("code");
                                        if (status.equals("200")){
                                            Log.d("cek status", "statusnya apa"+status);
                                            if (jabatan.equals("admin")){
                                                createNotificationChannel();

                                                Intent landingIntent = new Intent(getActivity(), AdminActivity.class);
                                                landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                landingIntent.putExtra("menuFragment","notifikasiAdmin");

                                                PendingIntent landingPendingIntent = PendingIntent.getActivity(getActivity(),0,landingIntent,PendingIntent.FLAG_ONE_SHOT);
                                                NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(),CHANNEL_ID);
                                                builder.setSmallIcon(R.drawable.ic_collections_bookmark_white_24dp);
                                                builder.setContentTitle("Ada Pengajuan Baru");
                                                builder.setContentText(nama+" mengajukan pinjaman");
                                                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                                builder.setContentIntent(landingPendingIntent);

                                                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());
                                                notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
                                            }
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
                            params.put("tenor",bulan);
                            params.put("total_pinjaman",total.getText().toString());
                            params.put("id_user",mSettings.getString("userid","1"));
                            params.put("tanggal",getDateTime());
                            params.put("tanggal_selesai",getDateAkhir());
                            return params;
                        }
                    };
                    Singleton.getInstance(getContext()).addToRequestQue(stringRequest);
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
}
