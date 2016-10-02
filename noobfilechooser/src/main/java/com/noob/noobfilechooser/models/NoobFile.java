package com.noob.noobfilechooser.models;

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
import android.widget.ImageView;

import com.noob.noobfilechooser.managers.NoobManager;

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
    private Bitmap mThumbnail;
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
    public Bitmap loadThumbnail(Context contextParam) {
        mThumbnail = DocumentsContract.getDocumentThumbnail(contextParam.getContentResolver(),
                documentFile.getUri(),
                new Point(60, 60),
                new CancellationSignal()
        );
        return mThumbnail;
    }

    public int getIconResource() {
        if (isDirectory()) {
            return NoobManager.getInstance().getConfig().getFolderDrawableResource();
        }
        if (isImageFile()) {
            return NoobManager.getInstance().getConfig().getImageFileDrawableResource();
        }
        if (isVideoFile()) {
            return NoobManager.getInstance().getConfig().getVideoFileDrawableResource();
        }
        if (isAudioFile()) {
            return NoobManager.getInstance().getConfig().getAudioFileDrawableResource();
        }
        return NoobManager.getInstance().getConfig().getFileDrawableResource();
    }

    public boolean loadImage(ImageView imageView) {
        if (mThumbnail == null && isImageFile()) {
            loadThumbnail(imageView.getContext());
        }
        if (mThumbnail != null) {
            imageView.setImageBitmap(mThumbnail);
            return true;
        }
        int iconResource = getIconResource();
        if (iconResource > 0) {
            imageView.setImageResource(iconResource);
            return true;
        }
        return false;
    }

    public boolean isImageFile() {
        if(getType()==null)
            return false;
        return getType().startsWith("image/");
    }

    public boolean isAudioFile() {
        if(getType()==null)
            return false;
        return getType().startsWith("audio/");
    }

    public boolean isVideoFile() {
        if(getType()==null)
            return false;
        return getType().startsWith("video/");
    }

    public DocumentFile getParent(){
        if (!isTreeDoc() ){
            return getDocumentFile().getParentFile();
        }
        return null;
    }

    public boolean delete() {
        return getDocumentFile() != null && getDocumentFile().delete();
    }

    public boolean renameTo(String fileName) {
        return getDocumentFile() != null && getDocumentFile().renameTo(fileName);
    }

    //region Accessors

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

    public Bitmap getThumbnail() {
        return mThumbnail;
    }
    //endregion
}
