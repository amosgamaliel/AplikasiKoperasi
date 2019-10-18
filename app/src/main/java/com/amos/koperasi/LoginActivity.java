package com.amos.koperasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amos.koperasi.Utility.SharedPreferenceConfig;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText id,pass;
    Button btn;
    String idInput, passInput;
    SharedPreferenceConfig preferenceConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        id = findViewById(R.id.edtID);
        pass = findViewById(R.id.edtPass);
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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, UserActivity.class);
                startActivity(i);
            }
        });
        }

    public void userLogin() {
        String url= "http://192.168.42.205/koperasi_API/login.php";
        StringRequest request = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("1")){
                            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                            preferenceConfig.writeLoginStatus(true);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"Email atau password salah",Toast.LENGTH_SHORT).show();
                            id.setText("");
                            pass.setText("");
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
