package com.amos.koperasi.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.amos.koperasi.Fragment.Admin.CekSimpananWajib;
import com.amos.koperasi.Fragment.Admin.DalamCicilan;
import com.amos.koperasi.Fragment.Admin.DashboardAdminFragment;
import com.amos.koperasi.Fragment.Admin.HistoryFragment;
import com.amos.koperasi.Fragment.Admin.NotifikasiAdminFragment;
import com.amos.koperasi.Fragment.Admin.SimpananFragment;
import com.amos.koperasi.Fragment.User.AjukanFragment;
import com.amos.koperasi.Fragment.User.CicilanFragment;
import com.amos.koperasi.Fragment.User.DahboardFragment;
import com.amos.koperasi.R;
import com.amos.koperasi.Utility.SharedPreferenceConfig;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(this);
//        if (sharedPreferenceConfig.readLoginUserStatus()){
//            startActivity(new Intent(this, UserActivity.class));
//            finish();
//        }
        setContentView(R.layout.activity_admin);


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
                    selectedFragment = new CekSimpananWajib();
                    break;
                case R.id.nav_history:
                    selectedFragment = new DalamCicilan();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containera, selectedFragment).addToBackStack(null).commit();
            return true;
        }
    };
}
