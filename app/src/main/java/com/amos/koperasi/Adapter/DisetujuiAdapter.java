package com.amos.koperasi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amos.koperasi.Model.DalamCicilanModel;
import com.amos.koperasi.Model.DetailCicilanModel;
import com.amos.koperasi.Model.DetailCicilanUserModel;
import com.amos.koperasi.R;

import java.util.ArrayList;
import java.util.List;

public class DisetujuiAdapter extends RecyclerView.Adapter<DisetujuiAdapter.DisetujuiViewHolder> {

    Context mCtx;
    List<DetailCicilanUserModel> list;
    public DisetujuiAdapter(Context mCtx, List<DetailCicilanUserModel> list) {
        this.mCtx = mCtx;
        this.list = list;
    }

    @NonNull
    @Override
    public DisetujuiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detail,parent,false);
        return new DisetujuiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DisetujuiViewHolder holder, int position) {
        DetailCicilanUserModel model =list.get(position);
        holder.tv.setText(String.valueOf(model.getJmlCicilan(position)));
        holder.header.setText("Cicilan ke "+String.valueOf(position+1));

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class DisetujuiViewHolder extends RecyclerView.ViewHolder {
        TextView tv,header;
        public DisetujuiViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.detailwe);
            header = itemView.findViewById(R.id.headerdetail);
        }
    }
    public void delete(int position) { //removes the row
        list.remove(position);
        notifyItemRemoved(position);
    }

}
