package com.amos.koperasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.amos.koperasi.Fragment.AjukanFragment;
import com.amos.koperasi.Fragment.DahboardFragment;
import com.amos.koperasi.Fragment.NotifikasiAdminFragment;
import com.amos.koperasi.Fragment.CicilanFragment;
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
                    selectedFragment = new DalamCicilan();
                    break;
                case R.id.nav_ajuan:
                    selectedFragment = new AjukanFragment();
                    break;

                case R.id.nav_item_notif:
                    selectedFragment = new NotifikasiAdminFragment();
                    break;
                case R.id.nav_history:
                    selectedFragment = new CicilanFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
            return true;
        }
    };
}
