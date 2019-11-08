package com.amos.koperasi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amos.koperasi.R;
import com.amos.koperasi.Utility.SharedPreferenceConfig;
import com.amos.koperasi.Utility.Singleton;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class UsernameActivity extends AppCompatActivity {
    Button btnLogSet,btnSave;
    EditText username,password,conpass;
    TextView title;
    SharedPreferenceConfig sharedPreferenceConfig;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        btnLogSet = findViewById(R.id.buttonLogin);
        username = findViewById(R.id.edtuserna);
        password = findViewById(R.id.password);
        conpass = findViewById(R.id.conPass);
        title = findViewById(R.id.intro_we);
        btnSave = findViewById(R.id.buttonSave);

        sharedPreferenceConfig =  new SharedPreferenceConfig(this);
        url = sharedPreferenceConfig.getUrl()+"peminjaman.php";

        btnLogSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setVisibility(View.GONE);
                password.setVisibility(View.VISIBLE);
                conpass.setVisibility(View.VISIBLE);

                btnLogSet.setVisibility(View.GONE);
                btnSave.setVisibility(View.VISIBLE);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UsernameActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });
    }

    private void requestUser(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("200")) {
                        Intent intent = new Intent(UsernameActivity.this, LoginActivity.class);
                        startActivity(intent);
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
        Singleton.getInstance(this).addToRequestQue(stringRequest);
    }
}
