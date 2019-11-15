package com.amos.koperasi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.amos.koperasi.Model.NamaPenyimpanModel;
import com.amos.koperasi.R;

import java.io.FilterReader;
import java.util.ArrayList;
import java.util.List;

public class NamaPenyimpanArrayAdapter extends ArrayAdapter<NamaPenyimpanModel> {

    private List<NamaPenyimpanModel> listFull;
    public NamaPenyimpanArrayAdapter(@NonNull Context context, @NonNull List<NamaPenyimpanModel> listNama) {
        super(context, 0, listNama);
        listFull = new ArrayList<>(listNama);
    }

    @Nullable
    @Override
    public NamaPenyimpanModel getItem(int position) {
        return super.getItem(position);
    }

    //
//    @Nullable
//    @Override
//    public NamaPenyimpanModel getItem(int position) {
//        return listFull.get(position);
//    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_anggota,parent,false);
        }

        TextView nama = convertView.findViewById(R.id.namaanggota);
        NamaPenyimpanModel model = getItem(position);
        if (model != null){
            nama.setText(model.getNama());
        }
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<NamaPenyimpanModel> suggestion = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                suggestion.addAll(listFull);
            }else{
                String fillPattern = constraint.toString().toLowerCase();
                for (NamaPenyimpanModel nama : listFull){
                    if (nama.getNama().toLowerCase().contains(fillPattern)){
                        suggestion.add(nama);
                    }
                }
            }
            results.values = suggestion;
            results.count = suggestion.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List)results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((NamaPenyimpanModel)resultValue).getNama();
        }


    };


}
