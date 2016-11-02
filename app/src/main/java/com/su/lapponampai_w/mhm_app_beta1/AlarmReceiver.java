package com.su.lapponampai_w.mhm_app_beta1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by apple on 5/10/16.
 */
public class AlarmReceiver extends BroadcastReceiver {

    //Explicit
    String string_sumId;


    @Override
    public void onReceive(Context context, Intent intent) {

        MyManage myManage = new MyManage(context);
        //String[] strUser = myManage.filter_userTABLE(1);
        String[] strNotification = myManage.filter_userTABLE(6);

        receiveIntent(intent);
        //Toast.makeText(context,string_sumId,Toast.LENGTH_LONG).show();


        if (strNotification[0].equals("Default")) {
            createNotification(context, "MHM Application", "ถึงเวลาแล้วครับ", "MHM Application",string_sumId);
        } else {
            createNotification(context,"MHM Application", strNotification[0], "MHM Application",string_sumId);
        }


        //createNotification(context, "Times Up", "Success", "MHM Application");
        //createNotification(context, "second Time", "10 Seconds Has Passed", "Alert");

    }

    private void receiveIntent(Intent intent) {

        string_sumId = intent.getStringExtra("DailyUpdateIntent");
        Log.d("25/10/2559", "4 : DailyUpdateIntent : " + string_sumId);
    }

    private void createNotification(Context context, String s, String s1, String alert,String sIntent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo_carabao48)
                .setContentTitle(s)
                .setTicker(alert)
                .setContentText(s1);


        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("PopUpMaster", "AlarmReceiver");
        intent.putExtra("SumId_AlarmReceiver", sIntent);

        for(int i = 0;i<=1;i++) {
            PendingIntent notificIntent = PendingIntent
                    .getActivity(context, 1, intent, 0); //ให้เปิด MainActivity
            notificIntent.cancel();
        }



        /*
        PendingIntent notificIntent = PendingIntent
                .getActivity(context, 0, new Intent(context, MainActivity.class), 0); //ให้เปิด MainActivity
        */

        PendingIntent notificIntent = PendingIntent
                .getActivity(context, 1, intent, 0); //ให้เปิด MainActivity


        builder.setContentIntent(notificIntent);
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
        builder.setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;

        notificationManager.notify(m,builder.build());



    }
    /*
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.logo_carabao48);
        builder.setTicker("ถึงเวลาทานยาแล้ว");
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle("มาสเตอร์ เตือนทานยาแล้ว");
        builder.setContentText("รายละเอียด");
        builder.setAutoCancel(true);

        Uri uri = RingtoneManager.getDefaultUri(Notification.DEFAULT_VIBRATE); //Defeault ของเสียง
        builder.setSound(uri);

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1000,notification);


        




    } // onReceive
    */
} //Main Class
