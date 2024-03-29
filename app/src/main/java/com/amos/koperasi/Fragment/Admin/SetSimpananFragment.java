package com.amos.koperasi.Fragment.Admin;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetSimpananFragment extends Fragment {


    public SetSimpananFragment() {
        // Required empty public constructor
    }
    AlertDialog.Builder builder;
    String iduser,jumlahKomitmen,namauser;
    SharedPreferenceConfig sharedPreferenceConfig;
    TextView prediksi;
    AutoCompleteTextView edtNama;
    EditText jumlah;
    Button btnSimpan;
    ArrayList<NamaPenyimpanModel> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_simpanan, container, false);

        edtNama = view.findViewById(R.id.namaanggota);
        jumlah = view.findViewById(R.id.jumlahsimpanan);
        btnSimpan = view.findViewById(R.id.buttonsetsimpanan);
        prediksi = view.findViewById(R.id.prediksi);

        sharedPreferenceConfig = new SharedPreferenceConfig(getActivity());
        getAnggota();
        NamaPenyimpanArrayAdapter adapter = new NamaPenyimpanArrayAdapter(getActivity(),list);
        edtNama.setAdapter(adapter);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Konfirmasi");
                builder.setMessage("Apakah anda yakin ingin menyimpan simpanan user ini?");
                builder.setCancelable(true);

                builder.setPositiveButton("Yakin", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        postSimpanan();
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
        });
        return view;
    }
    private void getAnggota(){
        String url = sharedPreferenceConfig.getUrl()+"namaanggota.php";
        StringRequest string = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("list");
                            list = new ArrayList<>();
                            for (int i = 0 ; i< array.length();i++){
                                JSONObject product = array.getJSONObject(i);
                                list.add(new NamaPenyimpanModel(
                                        product.getString("id_user"),
                                        product.getString("nama"),
                                        product.getString("jumlah_simpanan")
                                ));
                                Log.d("hm", "onResponse: "+list.toString());
                                final NamaPenyimpanArrayAdapter arrayAdapter = new NamaPenyimpanArrayAdapter(getActivity(),list);
                                edtNama.setAdapter(arrayAdapter);
                                edtNama.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        NamaPenyimpanModel pojo = arrayAdapter.getItem(position);
                                        iduser = pojo.getId();
                                        namauser = pojo.getNama();
                                        jumlahKomitmen = pojo.getJumlahSimpanan();
                                        if(jumlahKomitmen.equals("0")||jumlahKomitmen.equals("null")){
                                            jumlah.setEnabled(true);
                                            jumlah.setText("");
                                            btnSimpan.setEnabled(true);
                                            btnSimpan.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_gradient_style));
                                            jumlah.addTextChangedListener(new TextWatcher() {
                                                @Override
                                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                }

                                                @Override
                                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                }

                                                @Override
                                                public void afterTextChanged(Editable s) {
                                                    prediksi.setVisibility(View.VISIBLE);
                                                    prediksi.setText("Pada bulan "+getDateAkhir()+" "+namauser+" akan mendapatkan tunjangan sebesar Rp."+jumlah.getText().toString()+"0");
                                                }
                                            });
                                        }else{
                                            prediksi.setVisibility(View.GONE);
                                            btnSimpan.setEnabled(false);
                                            btnSimpan.setBackgroundColor(getResources().getColor(R.color.intro_description_color));
                                            jumlah.setText("User sudah memiliki komitmen simpanan");
                                            jumlah.setEnabled(false);
                                        }
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onResponse: "+e.getMessage());
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
        String url = sharedPreferenceConfig.getUrl()+"setsimpanan.php";
        StringRequest string = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("200")){
                                builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Berhasil");
                                builder.setMessage(namauser+" berhasil membuat komitmen baru");
                                builder.setCancelable(false);

                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        edtNama.setText("");
                                        jumlah.setText("");
                                        ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.fragment_containera, new DashboardAdminFragment()).addToBackStack(null)
                                                .commit();
                                        dialog.dismiss();
                                    }
                                });
                                builder.show();
                            }
                            Log.d(TAG, "onResponse: "+response);


                        }catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "tidak terkirim: "+e.getMessage());
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
                params.put("jumlah",jumlah.getText().toString());
                params.put("tanggal",getDateTime());
                params.put("tanggal_selesai",getDateAkhirPost());
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
    public String getDateAkhir() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "MMMM yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,10);
        Date date = calendar.getTime();

        return dateFormat.format(date);
    }
    public String getDateAkhirPost() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyyMMdd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,10);
        Date date = calendar.getTime();

        return dateFormat.format(date);
    }

}
