package com.noob.noobfilechooser.managers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhi on 27/09/16.
 */

public class NoobSAFManager {
    private static final int SDCARD_REQUEST_CODE = 4010;
    private static final String TAG = "NoobSAFManager";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void takeCardUriPermission(final Activity activityParam) {
        new AlertDialog.Builder(activityParam)
                .setTitle("Need SD Card Permission")
                .setMessage("Select SD Card root folder from next screen")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterfaceParam, int iParam) {
                        Intent _intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                        activityParam.startActivityForResult(_intent, SDCARD_REQUEST_CODE);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static boolean checkIfSDCardRoot(Uri uri) {
        if(!isExternalStorageDocument(uri))
            return false;
        String docId = DocumentsContract.getTreeDocumentId(uri);
        if (docId.endsWith(":")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean onActivityResult(Activity activity, int requestCode, Intent data) {
        if (requestCode == SDCARD_REQUEST_CODE) {
            if (data != null && data.getData() != null) {
                if (checkIfSDCardRoot(data.getData())) {
                    NoobPrefsManager.getInstance().setSDCardUri(data.getData());
                    return true;
                }
            }
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static NoobFile buildTreeFile(Activity activity, Uri uri) {
        ContentResolver contentResolver = activity.getContentResolver();
        Uri docUri = DocumentsContract.buildDocumentUriUsingTree(uri,
                DocumentsContract.getTreeDocumentId(uri));
        Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(uri,
                DocumentsContract.getTreeDocumentId(uri));

        Cursor docCursor = contentResolver.query(docUri, new String[]{
                DocumentsContract.Document.COLUMN_DISPLAY_NAME, DocumentsContract.Document.COLUMN_MIME_TYPE, DocumentsContract.Document.COLUMN_DOCUMENT_ID}, null, null, null);
        if (docCursor != null && docCursor.moveToNext()) {
            Log.d(TAG, "found doc =" + docCursor.getString(0) + ", mime=" + docCursor
                    .getString(1));
            NoobFile file = new NoobFile(activity, docCursor, uri, true);
            docCursor.close();
            return file;
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static List<NoobFile> buildChildFiles(Activity activity, Uri uri) {
        ContentResolver contentResolver = activity.getContentResolver();
        Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(uri,
                DocumentsContract.getTreeDocumentId(uri));

        List<NoobFile> _noobFiles = new ArrayList<>();


        Cursor childCursor = contentResolver.query(childrenUri, new String[]{
                DocumentsContract.Document.COLUMN_DISPLAY_NAME, DocumentsContract.Document.COLUMN_MIME_TYPE, DocumentsContract.Document.COLUMN_DOCUMENT_ID}, null, null, null);
        try {

            assert childCursor != null;
            while (childCursor.moveToNext()) {
                Log.d(TAG, "found child=" + childCursor.getString(0) + ", mime=" + childCursor
                        .getString(1));
                NoobFile file = new NoobFile(activity, childCursor, uri, false);
                _noobFiles.add(file);
            }
        } finally {
            if (childCursor != null)
                childCursor.close();
        }
        return _noobFiles;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
}
