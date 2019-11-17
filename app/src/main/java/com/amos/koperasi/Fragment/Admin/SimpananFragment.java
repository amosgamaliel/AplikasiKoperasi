package com.amos.koperasi.Fragment.Admin;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amos.koperasi.Adapter.NamaPenyimpanArrayAdapter;
import com.amos.koperasi.Model.NamaPenyimpanModel;
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
public class SimpananFragment extends Fragment{


    public SimpananFragment() {
        // Required empty public constructor
    }
    Context mCtx;
    EditText nama,jumlah;
    Spinner spinner;
    String url,iduser,namai,tipe;
    RecyclerView recyclerView;
    ArrayList<NamaPenyimpanModel> list;
    SharedPreferenceConfig sharedPreferenceConfig;
    NamaPenyimpanArrayAdapter namaPenyimpanAdapter;
    LinearLayoutManager layoutManager;
    AutoCompleteTextView editText;
    TextView edthasil;
    Button simpan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_simpanan, container, false);

        nama = view.findViewById(R.id.namaanggota);
        edthasil = view.findViewById(R.id.hasil);
        jumlah = view.findViewById(R.id.jumlahsimpanan);
        spinner = view.findViewById(R.id.spinnerSimpanan);
        recyclerView = view.findViewById(R.id.rv_simpanan_tahara);
        sharedPreferenceConfig = new SharedPreferenceConfig(getActivity());
        url = sharedPreferenceConfig.getUrl()+"namaanggota.php";
        layoutManager = new LinearLayoutManager(getActivity());
        tipe = spinner.getSelectedItem().toString();
        editText = view.findViewById(R.id.namaanggota);

        getAnggota();

        simpan = view.findViewById(R.id.simpan);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postSimpanan();
            }
        });
        jumlah.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (jumlah.length()!=0){
                    int jumlahs = Integer.parseInt(jumlah.getText().toString());
                    int hasil = jumlahs * 10;
                    edthasil.setText(String.valueOf(hasil));
                }
            }
        });

        return view;
    }

    private void getAnggota(){
        StringRequest string = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);
                            list = new ArrayList<>();
                            for (int i = 0 ; i< array.length();i++){
                                JSONObject product = array.getJSONObject(i);
                                list.add(new NamaPenyimpanModel(
                                        product.getString("id_user"),
                                        product.getString("nama")
                                ));
                                Log.d("hm", "onResponse: "+list.toString());
                                final NamaPenyimpanArrayAdapter arrayAdapter = new NamaPenyimpanArrayAdapter(getActivity(),list);
                                editText.setAdapter(arrayAdapter);
                                editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        NamaPenyimpanModel pojo = arrayAdapter.getItem(position);
                                        iduser = pojo.getId();
                                    }
                                });
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
        Singleton.getInstance(getActivity()).addToRequestQue(string);
    }

    private void postSimpanan(){
        url = sharedPreferenceConfig.getUrl()+"simpanan.php";
        StringRequest string = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject jsonObject = array.getJSONObject(0);
                            String code = jsonObject.getString("code");
                            if (code.equals("200")){
                                Toast.makeText(getActivity(), "Mantap", Toast.LENGTH_SHORT).show();
                            }


                        }catch (JSONException e) {
                            e.printStackTrace();
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
                params.put("id_user",iduser);
                params.put("nominal",jumlah.getText().toString());
                params.put("tipe_simpanan",tipe);
                params.put("tanggal",getDateTime());
                return params;
            }
        };
        Singleton.getInstance(getActivity()).addToRequestQue(string);
    }

    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyyMMdd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}
