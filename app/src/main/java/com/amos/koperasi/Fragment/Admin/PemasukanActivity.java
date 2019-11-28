package com.amos.koperasi.Fragment.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.amos.koperasi.Adapter.PemasukanAdapter;
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

public class PemasukanActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    PemasukanAdapter adapter;
    ArrayList<ActivityModel> list = new ArrayList<>();
    RecyclerView rv;
    LinearLayoutManager layoutManager;
    String url;
    SharedPreferenceConfig sharedPreferenceConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pemasukan);
        rv = findViewById(R.id.rvpemasukan);
        PemasukanActivity.this.setTitle("Pemasukan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        layoutManager = new LinearLayoutManager(this);
        sharedPreferenceConfig = new SharedPreferenceConfig(this);
        url = sharedPreferenceConfig.getUrl()+"activity.php";
        getData();
        adapter = new PemasukanAdapter(list,this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
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
                                if(tipe.equals("bayar")||tipe.equals("simpan")) {
                                    list.add(new ActivityModel(
                                            activity.getString("id_pinjaman"),
                                            activity.getString("id_user"),
                                            activity.getString("nama"),
                                            activity.getString("jumlah"),
                                            activity.getString("tanggal"),
                                            activity.getString("tipe")
                                    ));
                                }
                                adapter = new PemasukanAdapter(list,PemasukanActivity.this);
                                rv.setLayoutManager(layoutManager);
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
        Singleton.getInstance(PemasukanActivity.this).addToRequestQue(stringRequest);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.toolbarsearch);

        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
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
