package com.amos.koperasi.Utility;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.amos.koperasi.Activity.AdminActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification()!=null){
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            NotificationHelper.displayNotification(getApplicationContext(),title,body, AdminActivity.class);
        }
    }

    @Override
    public void onNewToken(String s) {
        Log.d("Token Baru", "onNewToken: "+s);
        sendToken(s);
    }

    void sendToken(final String token){
        final SharedPreferences mSettings;
        mSettings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferenceConfig sharedPreferenceConfig;
        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        String url = sharedPreferenceConfig.getUrl()+"updatefcmtoken.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("cek", "onResponseToken: "+response);
                            String status = jsonObject.getString("code");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("ya", "onResponseToken: "+e.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("gagal tapi bae", "onResponseToken"+mSettings.getString("userid","1"));
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                params.put("id_user",mSettings.getString("userid","1"));
                params.put("fcm_token",token);
                return params;
            }
        };
        Singleton.getInstance(getApplicationContext()).addToRequestQue(stringRequest);
    }
}
