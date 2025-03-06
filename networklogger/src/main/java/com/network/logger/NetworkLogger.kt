package com.network.logger

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.network.logger.database.AppDatabase
import com.network.logger.database.NetworkLoggerModel
import com.network.logger.list.NetworkLoggerListActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NetworkLogger {
    private val context: Context = NetworkLoggerApp.get()
    private var database : AppDatabase? = AppDatabase.getAppDatabase()
    private val scope = CoroutineScope(Dispatchers.Main.immediate)

    fun add(model: NetworkLoggerModel?, environment: String = "") {
        scope.launch {
            withContext(Dispatchers.IO) {
                if (model != null) database?.networkLoggerDao()?.insert(model)
            }
            showNotification(environment)
        }
    }

    fun cleanup() {
        // Cancel the scope when the NetworkLogger is no longer needed
        scope.cancel()
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showNotification(environment: String) {
        // Create an explicit intent for an Activity in your app
        val intent = Intent(context, NetworkLoggerListActivity::class.java)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val builder = NotificationCompat.Builder(context, "network_logger")
            .setSmallIcon(android.R.drawable.ic_menu_info_details)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText("Network Logger $environment")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "network_logger"
            val description = "network_logger"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("network_logger", name, importance)
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = context.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationManager = NotificationManagerCompat.from(context)

        // notificationId is a unique int for each notification that you must define
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(0, builder.build())
    }
}
