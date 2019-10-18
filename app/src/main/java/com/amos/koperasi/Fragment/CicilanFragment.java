package com.amos.koperasi.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amos.koperasi.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CicilanFragment extends Fragment {


    public CicilanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cicilan, container, false);
    }

}
