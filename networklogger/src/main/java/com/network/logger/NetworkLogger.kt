package com.network.logger;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.network.logger.database.AppDatabase;
import com.network.logger.database.NetworkLoggerModel;
import com.network.logger.list.NetworkLoggerListActivity;

public class NetworkLogger {

    private final Context context = NetworkLoggerApp.get();
    private final AppDatabase database = AppDatabase.getAppDatabase();

    public void add(final NetworkLoggerModel model) {
        add(model, "");
    }

    public void add(final NetworkLoggerModel model, String environment) {
        // using plain thread memory consumption less than 32 MB
        // using executor always increase until out of memory
        // both use 20% CPU consumption for inserting 100 data
        new Thread(() -> {
            database.networkLoggerDao().insert(model);
            showNotification(environment);
        }).start();
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private void showNotification(String environment) {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(context, NetworkLoggerListActivity.class);
        PendingIntent pendingIntent;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "network_logger")
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                .setContentTitle(context.getString(R.string.app_name) )
                .setContentText("Network Logger " + environment)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "network_logger";
            String description = "network_logger";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("network_logger", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, builder.build());
    }
}
