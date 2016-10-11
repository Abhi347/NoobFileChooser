package com.noob.noobfilechooser.managers;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by abhi on 22/09/16.
 */

public class NoobPermissionManager {
    private static final int PERMISSION_REQUEST_CODE = 4001;

    public static boolean takeRunTimePermissions(Activity activity) {
        return takePermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE, "Writing to External Storage is required for the App to work properly.", true);
    }

    private static boolean takePermission(final Activity activity, final String permission, String rationaleMessage, boolean shouldShowDialog) {
        if (checkPermission(activity, permission))
            return true;
        boolean shouldRequestPermission = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowDialog && activity.shouldShowRequestPermissionRationale(permission)) {
            showPermissionRationale(activity,
                    rationaleMessage,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterfaceParam, int iParam) {
                            takePermission(activity, permission, null, false);
                        }
                    },
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterfaceParam, int iParam) {
                            //TODO: Show an Error Message
                        }
                    });
            shouldRequestPermission = false;
        }

        if (shouldRequestPermission) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{permission},
                    PERMISSION_REQUEST_CODE);
        }
        return false;
    }

    private static void showPermissionRationale(Activity activity, String message, DialogInterface.OnClickListener postiveCallback, DialogInterface.OnClickListener negativeCallback) {
        new AlertDialog.Builder(activity)
                .setTitle("Permission Required")
                .setMessage(message)
                .setPositiveButton("OK", postiveCallback)
                .setNegativeButton("Cancel", negativeCallback)
                .create()
                .show();
    }

    public static boolean checkPermission(Activity activity, String permission) {
        int permissionCheck = ContextCompat.checkSelfPermission(activity,
                permission);

        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

}
