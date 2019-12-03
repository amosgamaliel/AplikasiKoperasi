package com.amos.koperasi.Utility;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.amos.koperasi.R;

public class NotificationHelper {

    public static final String CHANNEL_ID = "coba_firebase";
    public static final String CHANNEL_NAME = "amos";
    public static final String CHANNEL_DESC = "amosgamaliel";
    public static final String gabung = "@koperasismk2.com";


    public static void displayNotification(Context context, String title, String body,Class w){


        Intent intent = new Intent(context,w);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                100,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(body).setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(1,mBuilder.build());
    }

}
