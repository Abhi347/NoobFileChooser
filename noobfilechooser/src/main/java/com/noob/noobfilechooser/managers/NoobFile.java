package com.noob.noobfilechooser.managers;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.provider.DocumentsContract;
import android.support.v4.provider.DocumentFile;
import android.util.Log;

/**
 * Created by abhi on 27/09/16.
 */

public class NoobFile {
    private String name;
    private String mimeType;
    private String docId;
    private Uri uri;
    private boolean isTreeDoc;
    private boolean isDirectory;
    private DocumentFile documentFile;
    private Bitmap mBitmap;
    private boolean mIsSelected = false;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NoobFile(Context contextParam, Cursor cursorParam, Uri treeUri, boolean isTreeDocParam) {
        isTreeDoc = isTreeDocParam;

        name = cursorParam.getString(0);
        mimeType = cursorParam.getString(1);
        docId = cursorParam.getString(2);
        isDirectory = mimeType.equalsIgnoreCase(DocumentsContract.Document.MIME_TYPE_DIR);

        if (isTreeDoc) {
            uri = treeUri;
            documentFile = DocumentFile.fromTreeUri(contextParam, treeUri);
        } else {
            uri = DocumentsContract.buildDocumentUriUsingTree(treeUri, docId);
            if (isDirectory) {
                documentFile = DocumentFile.fromTreeUri(contextParam, uri);
            } else {
                documentFile = DocumentFile.fromSingleUri(contextParam, uri);
            }
        }

        if (documentFile.exists()) {
            Log.d("DocumentENtry", "Exists true " + name);
        } else {
            Log.d("DocumentENtry", "Exists false " + name);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public NoobFile(DocumentFile docFileParam) {
        isTreeDoc = false;
        documentFile = docFileParam;
        isDirectory = documentFile.isDirectory();
        uri = docFileParam.getUri();
        name = docFileParam.getName();
        mimeType = docFileParam.getType();
        docId = DocumentsContract.getDocumentId(uri);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public Bitmap loadBitmap(Context contextParam){
        mBitmap = DocumentsContract.getDocumentThumbnail(contextParam.getContentResolver(),
                documentFile.getUri(),
                new Point(60, 60),
                new CancellationSignal()
        );
        return mBitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String nameParam) {
        name = nameParam;
    }

    public String getType() {
        return mimeType;
    }

    public Uri getUri() {
        return uri;
    }

    public DocumentFile getDocumentFile() {
        return documentFile;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public boolean isTreeDoc() {
        return isTreeDoc;
    }

    public String getDocId() {
        return docId;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean selectedParam) {
        mIsSelected = selectedParam;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }
}
