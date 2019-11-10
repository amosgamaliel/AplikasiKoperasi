package com.amos.koperasi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class UsernameActivity extends AppCompatActivity {
    Button btnLogSet,btnSave;
    EditText username,password,conpass;
    TextView title;
    SharedPreferenceConfig sharedPreferenceConfig;
    String url,iduser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        if (restorePrefData()) {

            Intent mainActivity = new Intent(getApplicationContext(),UsernameActivity.class );
            startActivity(mainActivity);
            finish();


        }

        btnLogSet = findViewById(R.id.buttonLogin);
        username = findViewById(R.id.edtuserna);
        password = findViewById(R.id.password);
        conpass = findViewById(R.id.conPass);
        title = findViewById(R.id.intro_we);
        btnSave = findViewById(R.id.buttonSave);

        sharedPreferenceConfig =  new SharedPreferenceConfig(this);
        url = sharedPreferenceConfig.getUrl()+"peminjaman.php";
        final String iPass = password.getText().toString();
        final String iConpass = conpass.getText().toString();

        btnLogSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestUser();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!iPass.equals(iConpass)){
                    password.setError("Tidak sama");
                    password.setText("");
                    conpass.setError("Tidak sama");
                    conpass.setText("");
                }else{
                    setPassword();
                }
            }
        });
    }

    private void requestUser(){
        url = sharedPreferenceConfig.getUrl()+"slider.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("code");
                    iduser = jsonObject.getString("id");
                    if (status.equals("200")) {
                        Toast.makeText(getApplicationContext(),"Berhasil",Toast.LENGTH_SHORT).show();
                        username.setVisibility(View.GONE);
                        password.setVisibility(View.VISIBLE);
                        conpass.setVisibility(View.VISIBLE);
                        btnLogSet.setVisibility(View.GONE);
                        btnSave.setVisibility(View.VISIBLE);
                    }else{
                        username.setError("Username anda tidak terdaftar");
                    }

                } catch (JSONException e) {
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
                Map<String,String> params = new HashMap<>();
                params.put("username",username.getText().toString());
                return params;
            }
        };
        Singleton.getInstance(this).addToRequestQue(stringRequest);
    }



    private void setPassword(){
        url = sharedPreferenceConfig.getUrl()+"setpassword.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("code");
                    if (status.equals("200")) {
                        Intent intent = new Intent(UsernameActivity.this, LoginActivity.class);
                        startActivity(intent);
                        savePrefsData();
                    }else{
                        Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
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
                Map<String,String> params = new HashMap<>();
                params.put("iduser",iduser);
                params.put("password",password.getText().toString());
                return params;
            }
        };
        Singleton.getInstance(this).addToRequestQue(stringRequest);
    }
    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();

    }

    private boolean restorePrefData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return  isIntroActivityOpnendBefore;

    }
}
