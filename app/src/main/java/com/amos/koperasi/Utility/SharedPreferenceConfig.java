package com.amos.koperasi.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.amos.koperasi.R;

public class SharedPreferenceConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferenceConfig(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.login_preferences),Context.MODE_PRIVATE);

    }

    public void writeLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.login_status_preferences),status);
        editor.commit();
    }

    public boolean readLoginStatus(){
        boolean status = false;
        status= sharedPreferences.getBoolean(context.getResources().getString(R.string.login_status_preferences),false);
        return status;
    }

    public void getUsername(){

    }
}
