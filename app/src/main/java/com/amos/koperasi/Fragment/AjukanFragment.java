package com.amos.koperasi.Fragment;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.amos.koperasi.R;
import com.amos.koperasi.Utility.Singleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AjukanFragment extends Fragment {
    Button btnAjukan,btnDetail;
    Spinner spinner;
    public AjukanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        Calendar calendar = Calendar.getInstance();
//        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        final EditText jumlah,tenor;
        final TextView total;
        final AlertDialog.Builder builder ;
        final String url = "http://192.168.42.205/koperasi_API/peminjaman.php";
        View view = inflater.inflate(R.layout.fragment_ajukan, container, false);
        btnAjukan = view.findViewById(R.id.ajukan);
        btnDetail = view.findViewById(R.id.detail);
        spinner = view.findViewById(R.id.spinner);
        jumlah = view.findViewById(R.id.jumlah);
//        tenor = view.findViewById(R.id.tenor);
        total = view.findViewById(R.id.total);

        builder = new AlertDialog.Builder(getContext());




//
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int iJumlah = Integer.parseInt(jumlah.getText().toString());
                total.setText(kekata(iJumlah));

            }
        });
        btnAjukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int iJumlah, iTenor, iTotal;
                final String bulan = spinner.getSelectedItem().toString();
                int pos= spinner.getSelectedItemPosition();
                String[] value = getResources().getStringArray(R.array.bulan);
                final int bulann = Integer.valueOf(value[pos]);
                iJumlah = Integer.parseInt(jumlah.getText().toString());
//                iTenor = Integer.parseInt(tenor.getText().toString());
                iTotal = Integer.parseInt(total.getText().toString());

                if (jumlah.getText().toString().equals("")||
                    total.getText().toString().equals("")
                ){
                    builder.setTitle("Error");
                    builder.setMessage("Semua field harus diisi");
                }else {
                    StringRequest stringRequest = new StringRequest(
                            Request.Method.POST,
                            url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");
                                        String message = jsonObject.getString("message");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("jumlah_pinjaman",jumlah.getText().toString());
                            params.put("tenor",bulan);
                            params.put("total_pinjaman",total.getText().toString());
                            params.put("id_user","1");
                            return params;
                        }
                    };
                    Singleton.getInstance(getContext()).addToRequestQue(stringRequest);
                }
            }
        });
    return view;

}

    String kekata (int x) {
        x = Math.abs(x);
        long m = 1000000000000L;
        String [] angka = new String[]{
            "", "satu", "dua", "tiga", "empat", "lima",
                    "enam", "tujuh", "delapan", "sembilan", "sepuluh", "sebelas"
        };

        String $temp = "";
        if (x <12) {
            $temp = " "+ angka[x];
        } else if (x <20) {
            $temp = kekata(x - 10)+ " belas";
        } else if (x <100) {
            $temp = kekata(x/10)+" puluh"+ kekata(x % 10);
        } else if (x <200) {
            $temp = " seratus"+ kekata(x - 100);
        } else if (x <1000) {
            $temp = kekata(x/100) + " ratus" +kekata(x % 100);
        } else if (x <2000) {
            $temp = " seribu" + kekata(x - 1000);
        } else if (x <1000000) {
            $temp = kekata(x/1000) +" ribu" + kekata(x % 1000);
        } else if (x <1000000000) {
            $temp = kekata(x/1000000) + " juta" + kekata(x % 1000000);
        }
        return $temp;
    }
}
