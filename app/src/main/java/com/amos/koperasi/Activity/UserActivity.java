package com.amos.koperasi.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.amos.koperasi.Fragment.Admin.AkunFragment;
import com.amos.koperasi.Fragment.Admin.DalamCicilan;
import com.amos.koperasi.Fragment.User.AjukanFragment;
import com.amos.koperasi.Fragment.User.DahboardFragment;
import com.amos.koperasi.Fragment.Admin.NotifikasiAdminFragment;
import com.amos.koperasi.Fragment.User.CicilanFragment;
import com.amos.koperasi.Fragment.User.NotifikasiFragment;
import com.amos.koperasi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bottomNav = findViewById(R.id.bottom_navigation);
        frameLayout = findViewById(R.id.fragment_container);

        bottomNav.setOnNavigationItemSelectedListener(navListener);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new DahboardFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new DahboardFragment();
                    break;
                case R.id.nav_ajuan:
                    selectedFragment = new AjukanFragment();
                    break;

                case R.id.nav_item_notif:
                    selectedFragment = new NotifikasiFragment();
                    break;
                case R.id.nav_history:
                    selectedFragment = new AkunFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
            return true;
        }
    };
}
