package com.amos.koperasi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

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

public class LoginActivity extends AppCompatActivity {
    EditText iduser,pass;
    FrameLayout user;
    Button btn,btnAdmin;
    String idInput, passInput;
    SharedPreferenceConfig preferenceConfig;
    String url;
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        iduser = findViewById(R.id.edtID);
        pass = findViewById(R.id.edtPass);
        user =findViewById(R.id.userlogin);

        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());


        if (preferenceConfig.readLoginAdminStatus()){
            startActivity(new Intent(this, AdminActivity.class));
            finish();
        }else if (preferenceConfig.readLoginUserStatus()){
            startActivity(new Intent(this, UserActivity.class));
            finish();
        }

        idInput = iduser.getText().toString();
        passInput = pass.getText().toString();
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progressDialog = new ProgressDialog(LoginActivity.this);
//                progressDialog.show();
//                progressDialog.setContentView(R.layout.progress_dialog);
                userLogin();
            }
        });
        }

    public void userLogin() {
        url = preferenceConfig.getUrl();
        url= url+"login.php";
        StringRequest request = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //get data dari server

                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");

                            if (code.equals("200")){
                                final String id = jsonObject.getString("id");
                                //menyimpan user id kedalam shared preferences untuk dipanggil kembali di setiap action transaksi
                                SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                SharedPreferences.Editor editor = mSettings.edit();
                                editor.putString("userid",id);
                                editor.apply();
                                String jabatan = jsonObject.getString("jabatan");
                                if (jabatan.equals("2")){
                                    Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                                    preferenceConfig.writeLoginUserStatus(true);
                                    editor.putString("jabatan","user");
                                    editor.apply();
                                    startActivity(intent);
//                                    progressDialog.dismiss();
                                    finish();
                                    Log.d("berhasil user", "onResponse: "+response);
                                }else if (jabatan.equals("1")){
                                    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                                    preferenceConfig.writeLoginAdminStatus(true);
                                    editor.putString("jabatan","admin");
                                    editor.apply();
                                    startActivity(intent);
//                                    progressDialog.dismiss();
                                    finish();
                                    Log.d("berhasil admin", "onResponse: "+response);
                                }
                            }else if (code.equals("404")){
                                builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setTitle("Login Gagal");
                                builder.setMessage("Username atau password salah");
                                builder.setCancelable(false);

                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        pass.setText("");
                                        iduser.setText("");
                                        dialog.dismiss();

                                    }
                                });
                                builder.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Error Response", error.toString());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",iduser.getText().toString());
                params.put("password",pass.getText().toString());
                return params;
            }
        };
        Singleton.getInstance(LoginActivity.this).addToRequestQue(request);
    }


}
