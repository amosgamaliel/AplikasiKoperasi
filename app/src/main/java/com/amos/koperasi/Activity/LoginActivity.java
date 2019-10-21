package com.amos.koperasi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amos.koperasi.R;
import com.amos.koperasi.Utility.SharedPreferenceConfig;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText id,pass;
    Button btn,btnAdmin;
    String idInput, passInput;
    SharedPreferenceConfig preferenceConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        id = findViewById(R.id.edtID);
        pass = findViewById(R.id.edtPass);
        btnAdmin= findViewById(R.id.buttonadmin);
        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        if (preferenceConfig.readLoginStatus()){
            startActivity(new Intent(this, UserActivity.class));
            finish();
        }

        idInput = id.getText().toString();
        passInput = pass.getText().toString();
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(LoginActivity.this, UserActivity.class);
//                startActivity(i);
//            }
//        });
//        btnAdmin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this,AdminActivity.class);
//                startActivity(intent);
//            }
//        });
        }

    public void userLogin() {
        String url= "http://192.168.1.8/koperasi_API/login.php";
        StringRequest request = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String jabatan = jsonObject.getString("jabatan");
                            String id = jsonObject.getString("id");
//                            SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
//                            sharedPreferenceConfig.setIdUser(id);
                            SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                            SharedPreferences.Editor editor = mSettings.edit();
                            editor.putString("userid",id);
                            editor.apply();

                            if (jabatan.equals("2")){
                                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                                preferenceConfig.writeLoginStatus(true);
                                startActivity(intent);
                                finish();
                                Log.d("berhasil user", "onResponse: "+response);
                            }else if (jabatan.equals("1")){
                                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                                preferenceConfig.writeLoginStatus(true);
                                startActivity(intent);
                                finish();
                                Log.d("berhasil admin", "onResponse: "+response);
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
                params.put("username",id.getText().toString());
                params.put("password",pass.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}
