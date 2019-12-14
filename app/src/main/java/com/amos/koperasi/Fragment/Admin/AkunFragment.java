package com.amos.koperasi.Fragment.Admin;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amos.koperasi.Activity.LoginActivity;
import com.amos.koperasi.Fragment.GantiPassword;
import com.amos.koperasi.Fragment.User.CicilanFragment;
import com.amos.koperasi.R;
import com.amos.koperasi.Utility.SharedPreferenceConfig;

/**
 * A simple {@link Fragment} subclass.
 */
public class AkunFragment extends Fragment {


    public AkunFragment() {
        // Required empty public constructor
    }
    Button logout,gantiPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_akun, container, false);
        logout = view.findViewById(R.id.logouts);
        gantiPass = view.findViewById(R.id.gantiPass);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceConfig sharedPreferenceConfig;
                sharedPreferenceConfig = new SharedPreferenceConfig(getActivity());
                sharedPreferenceConfig.writeLoginUserStatus(false);
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
        gantiPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GantiPassword gantiPassword = new GantiPassword();
                ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, gantiPassword).addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

}
