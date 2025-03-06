package com.networklogger.example;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionHelper {

    // Choose a unique request code
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 101;

    public void requestPermissionNotification(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13 (API 33) and above
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_REQUEST_CODE);
            }
        }
        // No permission needed for older versions.
    }

    public boolean checkNotificationPermission(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
        }else {
            return true; //Permissions not needed on older versions.
        }
    }

    public int getNotificationPermissionRequestCode(){
        return NOTIFICATION_PERMISSION_REQUEST_CODE;
    }
}