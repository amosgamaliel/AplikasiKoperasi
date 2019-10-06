package com.amos.koperasi;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotifikasiAdminFragment extends Fragment {


    public NotifikasiAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifikasi_admin, container, false);
        NotifikasiAdminAdapter notifikasiAdminAdapter = new NotifikasiAdminAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.rvcontainer);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(notifikasiAdminAdapter);

        return view;
    }

}
