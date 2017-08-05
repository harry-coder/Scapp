package com.example.harpreet.scrapper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Harpreet on 31/07/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Vibrator vibrator= (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(10000);
        Intent notificationIntent=new Intent(context,MainActivity.class);
        TaskStackBuilder taskStackBuilder=TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent=taskStackBuilder.getPendingIntent(100,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);

        Notification notification=builder.setContentTitle("New Rates from OKHLEE ")
                .setTicker("Wake up boss").setAutoCancel(true).setSmallIcon(R.drawable.alarm).setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                .setContentIntent(pendingIntent).build();

        NotificationManager manager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,notification);
    }
}
