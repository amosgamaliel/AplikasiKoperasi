package com.amos.koperasi.Fragment.User;


import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.amos.koperasi.Activity.LoginActivity;
import com.amos.koperasi.Adapter.DalamCicilanAdapter;
import com.amos.koperasi.Adapter.DetailCicilanAdapter;
import com.amos.koperasi.Fragment.Admin.DalamCicilan;
import com.amos.koperasi.Model.DalamCicilanModel;
import com.amos.koperasi.Model.DetailCicilanModel;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AjukanFragment extends Fragment {
    Button btnAjukan,btnDetail;
    Spinner spinner;
    EditText jumlah,tenor;
    SharedPreferenceConfig sharedPreferenceConfig;
    TextView total,terbilang;
    AlertDialog.Builder builder ;
    String url = "http://192.168.1.6/koperasi_API/peminjaman.php";
    ArrayList<DetailCicilanModel> arrayList = new ArrayList<>();

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
        final SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(getContext());
        final DetailCicilanAdapter adapter = new DetailCicilanAdapter(getActivity(),arrayList);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        final RecyclerView recyclerView = view.findViewById(R.id.rvdetail);

        final String bulan = spinner.getSelectedItem().toString();

        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos= spinner.getSelectedItemPosition();
                String[] value = getResources().getStringArray(R.array.bulan);
                final int bulann = Integer.valueOf(value[pos]);
                final int bln = bulann;
                int iJumlah = Integer.parseInt(jumlah.getText().toString());
                final int perBulan = iJumlah/bln;
                ArrayList<Integer> integers = new ArrayList<>();
                terbilang.setText(kekata(iJumlah));
                for (int i = 0; i< bln ; i++){
                    DetailCicilanModel model = new DetailCicilanModel();
                    int sisa = iJumlah - perBulan*i;
                    int cicilan = (int) (perBulan+(sisa*0.02));
                    integers.add(cicilan);
                    model.setJmlCicilan(integers.get(i));
                    arrayList.add(model);
                }

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                Log.d("isi array", "onClick: "+arrayList);




            }
        });


        btnAjukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= spinner.getSelectedItemPosition();
                String[] value = getResources().getStringArray(R.array.bulan);
                final int bulann = Integer.valueOf(value[pos]);
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
                                    Log.d("berhasil tapi bagus", "wleeee"+getDateTime()+mSettings.getString("userid","1"));
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
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
