package com.amos.koperasi.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.amos.koperasi.Fragment.Admin.DalamCicilan;
import com.amos.koperasi.Fragment.Admin.DashboardAdminFragment;
import com.amos.koperasi.Fragment.Admin.NotifikasiAdminFragment;
import com.amos.koperasi.Fragment.User.AjukanFragment;
import com.amos.koperasi.Fragment.User.CicilanFragment;
import com.amos.koperasi.Fragment.User.DahboardFragment;
import com.amos.koperasi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        bottomNav = findViewById(R.id.bottom_navigationa);
        frameLayout = findViewById(R.id.fragment_containera);

        bottomNav.setOnNavigationItemSelectedListener(navListener);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_containera, new DashboardAdminFragment()).commit();
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
                    selectedFragment = new AjukanFragment();
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
}
