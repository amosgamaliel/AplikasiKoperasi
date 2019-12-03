package com.amos.koperasi.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.amos.koperasi.Adapter.HistoryAdapter;
import com.amos.koperasi.Fragment.Admin.DashboardAdminFragment;
import com.amos.koperasi.Fragment.Admin.HistoryFragment;
import com.amos.koperasi.Fragment.Admin.NotifikasiAdminFragment;
import com.amos.koperasi.Fragment.Admin.PemasukanActivity;
import com.amos.koperasi.Model.ActivityModel;
import com.amos.koperasi.Model.HistoryModel;
import com.amos.koperasi.R;
import com.amos.koperasi.Utility.SharedPreferenceConfig;
import com.amos.koperasi.Utility.Singleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    List<HistoryModel> list = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    HistoryAdapter historyAdapter;
    SharedPreferenceConfig sharedPreferenceConfig;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_history);

        HistoryActivity.this.setTitle("History Pinjaman");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.history_rv);
        historyAdapter = new HistoryAdapter(list,this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();
        sharedPreferenceConfig = new SharedPreferenceConfig(this);
        url = sharedPreferenceConfig.getUrl()+ "history.php";

        getData();
    }
    private void getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0 ; i< jsonArray.length();i++) {
                                JSONObject product = jsonArray.getJSONObject(i);
                                list.add(new HistoryModel(
                                        product.getString("nama"),
                                        product.getString("id_user"),
                                        product.getString("id_pinjaman"),
                                        product.getString("id_user"),
                                        product.getString("tanggal_mulai"),
                                        product.getString("tanggal_selesai"),
                                        product.getString("tenor"),
                                        product.getString("total_pinjaman"),
                                        product.getString("tanggal_diserahkan")


                                ));
                                recyclerView.setAdapter(historyAdapter);
                                historyAdapter.notifyDataSetChanged();
                                Log.d("res", "historyResponse: "+response);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("hias", "onResponse: "+e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        Singleton.getInstance(HistoryActivity.this).addToRequestQue(stringRequest);
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

        ArrayList<HistoryModel> baru = new ArrayList<>();
        for (HistoryModel model : list){
            if (model.getNama().toLowerCase().contains(userInput)){
                baru.add(model);
            }
        }
        historyAdapter.updateData(baru);
        return true;
    }
}
