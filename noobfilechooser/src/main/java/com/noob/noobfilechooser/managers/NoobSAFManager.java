package com.noob.noobfilechooser.managers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.provider.DocumentFile;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.noob.noobfilechooser.models.NoobFile;
import com.noob.noobfilechooser.models.NoobStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhi on 27/09/16.
 */

public class NoobSAFManager {
    private static final int ADD_STORAGE_REQUEST_CODE = 4010;
    private static final String TAG = "NoobSAFManager";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void takeCardUriPermission(final Activity activityParam) {
        new AlertDialog.Builder(activityParam)
                .setTitle("Need Storage Permission")
                .setMessage("Select root (outermost) folder of storage you want to add from next screen")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterfaceParam, int iParam) {
                        Intent _intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                        activityParam.startActivityForResult(_intent, ADD_STORAGE_REQUEST_CODE);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static boolean checkIfSDCardRoot(Uri uri) {
        return isExternalStorageDocument(uri) && isRootUri(uri) && !isInternalStorage(uri);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static boolean isRootUri(Uri uri) {
        String docId = DocumentsContract.getTreeDocumentId(uri);
        return docId.endsWith(":");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean onActivityResult(Activity activity, int requestCode, Intent data) {
        if (requestCode == ADD_STORAGE_REQUEST_CODE) {
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                return addUriToStorage(uri, activity);
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean addUriToStorage(Uri uri, Activity activity) {
        if (uri != null && checkIfSDCardRoot(uri)) {
            String title = getNameFromUri(uri, activity);
            //NoobPrefsManager.getInstance().setSDCardUri(data.getData());
            NoobStorage _storage = new NoobStorage(uri, title);
            NoobPrefsManager.getInstance().addStorage(_storage);
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static String getNameFromUri(Uri uri, Context context) {
        String title = null;
        if (isRootUri(uri)) {
            DocumentFile documentFile = DocumentFile.fromTreeUri(context, uri);
            if (isInternalStorage(uri))
                if (NoobManager.getInstance().getConfig().isShouldShowStorageName()) {
                    title = "Internal Storage ( " + documentFile.getName() + " )";
                } else {
                    title = "Internal Storage";
                }
            else if (NoobManager.getInstance().getConfig().isShouldShowStorageName()) {
                title = "SD Card ( " + documentFile.getName() + " )";
            } else {
                title = "SD Card";
            }
        } else {
            DocumentFile documentFile = DocumentFile.fromSingleUri(context, uri);
            return documentFile.getName();
        }
        return title;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean isInternalStorage(Uri uri) {
        return isExternalStorageDocument(uri) && DocumentsContract.getTreeDocumentId(uri).contains("primary");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static NoobFile buildTreeFile(Activity activity, Uri uri) throws SecurityException {
        if (uri == null)
            return null;
        ContentResolver contentResolver = activity.getContentResolver();
        Uri docUri = DocumentsContract.buildDocumentUriUsingTree(uri,
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

    public static String getValidName(DocumentFile file, boolean isRoot) {
        if (isRoot)
            if (NoobManager.getInstance().getConfig().isShouldShowStorageName()) {
                return "SD Card ( " + file.getName() + " )";
            } else {
                return "SD Card";
            }
        else
            return file.getName();
    }
}
