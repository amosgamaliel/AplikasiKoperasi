package com.amos.koperasi.Fragment.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.amos.koperasi.Adapter.PengeluaranAdapter;
import com.amos.koperasi.Model.ActivityModel;
import com.amos.koperasi.R;
import com.amos.koperasi.Utility.SharedPreferenceConfig;
import com.amos.koperasi.Utility.Singleton;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PengeluaranActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    PengeluaranAdapter adapter;
    ArrayList<ActivityModel> list = new ArrayList<>();
    RecyclerView rv;
    LinearLayoutManager layoutManager;
    String url;
    Toolbar toolbar;
    SharedPreferenceConfig sharedPreferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pengeluaran);
        rv = findViewById(R.id.rvpengeluaran);
        PengeluaranActivity.this.setTitle("Pengeluaran");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        layoutManager = new LinearLayoutManager(this);
        sharedPreferenceConfig = new SharedPreferenceConfig(this);
        url = sharedPreferenceConfig.getUrl()+"activity.php";

        getData();
        adapter = new PengeluaranAdapter(list,this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.toolbarsearch);

        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    private void getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            adapter.clear();
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length();i++){
                                JSONObject activity = array.getJSONObject(i);
                                String tipe = activity.getString("tipe");
                                if(tipe.equals("keluar")) {
                                    list.add(new ActivityModel(
                                            activity.getString("id_pinjaman"),
                                            activity.getString("id_user"),
                                            activity.getString("nama"),
                                            activity.getString("jumlah"),
                                            activity.getString("tanggal"),
                                            activity.getString("tipe")
                                    ));
                                }
                                adapter = new PengeluaranAdapter(list,PengeluaranActivity.this);
                                rv.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Singleton.getInstance(PengeluaranActivity.this).addToRequestQue(stringRequest);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();

        ArrayList<ActivityModel> baru = new ArrayList<>();
        for (ActivityModel model : list){
            if (model.getNama().toLowerCase().contains(userInput)){
                baru.add(model);
            }
        }
        adapter.updateData(baru);
        return true;
    }
}
