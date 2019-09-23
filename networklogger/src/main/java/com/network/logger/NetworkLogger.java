package com.network.logger;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.network.logger.database.AppDatabase;
import com.network.logger.database.NetworkLoggerModel;
import com.network.logger.list.NetworkLoggerListActivity;

public class NetworkLogger {

    private Context context = NetworkLoggerApp.get();
    private AppDatabase database = AppDatabase.getAppDatabase();

    public void add(NetworkLoggerModel model) {
        new InsertTask().execute(model);
    }

    private class InsertTask extends AsyncTask<NetworkLoggerModel, NetworkLoggerModel, NetworkLoggerModel> {
        @Override
        protected NetworkLoggerModel doInBackground(NetworkLoggerModel... networkLoggerModels) {
            database.networkLoggerDao().insert(networkLoggerModels[0]);
            return networkLoggerModels[0];
        }

        @Override
        protected void onPostExecute(NetworkLoggerModel model) {
            super.onPostExecute(model);
            showNotification(model);
        }
    }

    private void showNotification(NetworkLoggerModel model) {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(context, NetworkLoggerListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "network_logger")
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText("Network Logger")
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
