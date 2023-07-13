package com.example.airtime;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Notifications {
    private static final String CHANNEL_ID = "My Notification";
    private static final int NOTIFICATION_ID_1 = 1;
    private static final int NOTIFICATION_ID_2 = 2;

    public static void scheduleNotification(Context context, String title, String content, String notificationTime, int notificationId) {
        long timeInMillis = getTimeInMillis(notificationTime);

        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_ONE_SHOT);

        Intent fullScreenIntent = new Intent(context, NotificationsActivity.class);
        fullScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.avatar)
                .setAutoCancel(true)

                .setContentIntent(pendingIntent)
                .setFullScreenIntent(fullScreenPendingIntent, true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(notificationId, builder.build());
    }

    private static long getTimeInMillis(String dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy H:mm", Locale.getDefault());
        try {
            Date date = sdf.parse(dateTime);
            if (date != null) {
                return date.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
