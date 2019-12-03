package com.amos.koperasi.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.amos.koperasi.Fragment.Admin.CekSimpananWajib;
import com.amos.koperasi.Fragment.Admin.DalamCicilan;
import com.amos.koperasi.Fragment.Admin.DashboardAdminFragment;
import com.amos.koperasi.Fragment.Admin.HistoryFragment;
import com.amos.koperasi.Fragment.Admin.MenyerahkanFragment;
import com.amos.koperasi.Fragment.Admin.NotifikasiAdminFragment;
import com.amos.koperasi.Fragment.Admin.SimpananFragment;
import com.amos.koperasi.Fragment.User.AjukanFragment;
import com.amos.koperasi.Fragment.User.CicilanFragment;
import com.amos.koperasi.Fragment.User.DahboardFragment;
import com.amos.koperasi.R;
import com.amos.koperasi.Utility.SharedPreferenceConfig;
import com.amos.koperasi.Utility.Singleton;
import com.amos.koperasi.Utility.User;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    FrameLayout frameLayout;
    FirebaseAuth mAuth;
    public static final String NODE_USER = "users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin);

        mAuth = FirebaseAuth.getInstance();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            String token = task.getResult().getToken();
                            saveToken(token);
                            sendToken(token);

                        }else {
                        }
                    }
                });




        bottomNav = findViewById(R.id.bottom_navigationa);
        frameLayout = findViewById(R.id.fragment_containera);

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        String menuFragment = getIntent().getStringExtra("menuFragment");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_containera, new DashboardAdminFragment()).commit();
            if (menuFragment != null) {
                if (menuFragment.equals("notifikasiAdmin")) {
                    NotifikasiAdminFragment notifikasiAdminFragment = new NotifikasiAdminFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containera, notifikasiAdminFragment).commit();
                    bottomNav.setOnNavigationItemSelectedListener(navListener);
                    bottomNav.setSelectedItemId(R.id.nav_item_notif);
                }
            }
        }

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new DashboardAdminFragment();
                    break;
                case R.id.nav_ajuan:
                    selectedFragment = new SimpananFragment();
                    break;

                case R.id.nav_item_notif:
                    selectedFragment = new NotifikasiAdminFragment();
                    break;
                case R.id.nav_history:
                    selectedFragment = new DalamCicilan();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containera, selectedFragment).addToBackStack(null).commit();
            return true;
        }
    };
    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null){
            Intent intent = new Intent(AdminActivity.this,LoginActivity.class);
                SharedPreferenceConfig sharedPreferenceConfig;
                    sharedPreferenceConfig = new SharedPreferenceConfig(this);
                    sharedPreferenceConfig.writeLoginAdminStatus(false);
            startActivity(intent);
            finish();
        }
    }

    private void saveToken(String token) {
        String email = mAuth.getCurrentUser().getEmail();

        User user = new User(email,token);

        DatabaseReference dbUser = FirebaseDatabase.getInstance().getReference(NODE_USER);
        dbUser.child(mAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(AdminActivity.this,"Token Disimpan",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void sendToken(final String token){
        final SharedPreferences mSettings;
        mSettings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferenceConfig sharedPreferenceConfig;
        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        String url = sharedPreferenceConfig.getUrl()+"updatefcmtoken.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("cek", "onResponseToken: "+response);
                            String status = jsonObject.getString("code");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("ya", "onResponseToken: "+e.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("gagal tapi bae", "onResponseToken"+mSettings.getString("userid","1"));
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                params.put("id_user",mSettings.getString("userid","1"));
                params.put("fcm_token",token);
                return params;
            }
        };
        Singleton.getInstance(getApplicationContext()).addToRequestQue(stringRequest);
    }
}
