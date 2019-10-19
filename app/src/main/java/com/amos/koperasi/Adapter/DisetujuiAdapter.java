package com.amos.koperasi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amos.koperasi.Fragment.User.CicilanFragment;
import com.amos.koperasi.Model.NotifikasiDisetujui;
import com.amos.koperasi.R;

import java.util.List;

public class DisetujuiAdapter extends RecyclerView.Adapter<DisetujuiAdapter.DisetujuiViewHolder> {

    Context mCtx;
    FragmentManager fragmentManager ;
    List<NotifikasiDisetujui> list;
    public DisetujuiAdapter(List<NotifikasiDisetujui> pengajuans, Context context) {
        this.mCtx= context;
        this.list= pengajuans;
    }

    @NonNull
    @Override
    public DisetujuiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_disetujui,parent,false);
        return new DisetujuiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DisetujuiViewHolder holder, final int position) {
        NotifikasiDisetujui infoPengajuan = list.get(position);
        holder.jumlah.setText(String.valueOf(infoPengajuan.getJumlah()));
        holder.tanggal.setText(infoPengajuan.getTanggal());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((AppCompatActivity)mCtx).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CicilanFragment()).addToBackStack(null).commit();
                delete(position);
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DisetujuiViewHolder extends RecyclerView.ViewHolder {
        TextView tanggal,jumlah;
        CardView cv;
        public DisetujuiViewHolder(@NonNull View itemView) {
            super(itemView);
            tanggal= itemView.findViewById(R.id.tanggalp);
            jumlah = itemView.findViewById(R.id.jumlahpe);
            cv = itemView.findViewById(R.id.cv);
        }
    }
    public void delete(int position) { //removes the row
        list.remove(position);
        notifyItemRemoved(position);
    }
}
