package com.amos.koperasi.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.amos.koperasi.R;

public class SharedPreferenceConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public static final String url ="http://192.168.1.26/koperasi_API/";

    public String getUrl() {
        return url;
    }



    public SharedPreferenceConfig(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.login_preferences),Context.MODE_PRIVATE);
    }

    public void writeLoginAdminStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.login_status_admin_preferences),status);
        editor.commit();
    }
    public void writeLoginUserStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.login_status_preferences),status);
        editor.commit();
    }
    public boolean readLoginUserStatus(){
        boolean status = false;
        status= sharedPreferences.getBoolean(context.getResources().getString(R.string.login_status_preferences),false);
        return status;
    }
    public boolean readLoginAdminStatus(){
        boolean status = false;
        status= sharedPreferences.getBoolean(context.getResources().getString(R.string.login_status_admin_preferences),false);
        return status;
    }
    public void getUsername(){

    }
}
