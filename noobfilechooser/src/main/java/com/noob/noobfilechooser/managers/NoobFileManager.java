package com.noob.noobfilechooser.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.CancellationSignal;
import android.provider.DocumentsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.provider.DocumentFile;
import android.webkit.MimeTypeMap;

import java.io.File;

/**
 * Created by abhi on 11/10/16.
 */

public class NoobFileManager {
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static Bitmap getThumbnail(File fileParam, int width, int height) {
        return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(fileParam.getPath()), width, height);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static Bitmap getThumbnail(DocumentFile documentFileParam, Context contextParam, int width, int height) {
        return DocumentsContract.getDocumentThumbnail(contextParam.getContentResolver(),
                documentFileParam.getUri(),
                new Point(width, height),
                new CancellationSignal()
        );
    }

    public static String getValidName(File file, boolean isRoot) {
        if (isRoot)
            if (NoobManager.getInstance().getConfig().isShouldShowStorageName()) {
                return "Internal Storage ( " + file.getName() + " )";
            } else {
                return "Internal Storage";
            }
        else
            return file.getName();
    }
}
