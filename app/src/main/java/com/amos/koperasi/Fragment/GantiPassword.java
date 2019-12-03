package com.amos.koperasi.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class GantiPassword extends Fragment {

    AlertDialog.Builder builder;
    String url,isipassword,isipasswordbaru,isikonpassword;
    SharedPreferenceConfig sharedPreferenceConfig;
    EditText password,passwordbaru,konfirmasipassword;
    Button ganti;
    SharedPreferences mSettings;
    public GantiPassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ganti_password, container, false);

        password = view.findViewById(R.id.passwordlama);
        konfirmasipassword = view.findViewById(R.id.konfirmasipassword);
        passwordbaru = view.findViewById(R.id.passwordbaru);
        ganti = view.findViewById(R.id.senddata);

        sharedPreferenceConfig = new SharedPreferenceConfig(getActivity());
        url = sharedPreferenceConfig.getUrl()+"gantipassword.php";
        ganti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isikonpassword.equals(isipassword)){

                    showDialog();
                }else{
                    password.setError("gagal");
                    konfirmasipassword.setError("gagal");
                    password.setError("error");
                }
            }
        });
        mSettings = PreferenceManager.getDefaultSharedPreferences(getContext());
        isipassword = password.getText().toString();
        isipasswordbaru = passwordbaru.getText().toString();
        isikonpassword = konfirmasipassword.getText().toString();


        return view;

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
                                builder.setMessage("Berhasil mengganti password");
                                builder.setCancelable(false);

                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        password.setText("");
                                        konfirmasipassword.setText("");
                                        passwordbaru.setText("");
                                        dialog.dismiss();

                                    }
                                });
                                builder.show();
                            }else if (status.equals("404")){
                                builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Gagal");
                                builder.setMessage("Password lama anda salah");
                                builder.setCancelable(false);

                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        password.setText("");
                                        konfirmasipassword.setText("");
                                        passwordbaru.setText("");
                                        dialog.dismiss();
                                    }
                                });
                                builder.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("tes", "onResponse: "+response);
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
                params.put("id_user",mSettings.getString("userid","1"));
                params.put("password",isipassword);
                params.put("password_baru",isipasswordbaru);
                return params;
            }
        };
        Singleton.getInstance(getContext()).addToRequestQue(stringRequest);
    }

    private void showDialog(){
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Konfirmasi");
        builder.setMessage("Apakah anda yakin ingin mengganti password?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yakin", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isipassword.equals(isikonpassword)){
                    getData();
                    dialog.dismiss();
                }else{
                    password.setError("Tidak sama");
                    konfirmasipassword.setError("Tidak sama");
                    password.setText("");
                    konfirmasipassword.setText("");
                    dialog.dismiss();
                }
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
}
