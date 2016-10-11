package com.noob.noobfilechooser.models;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.provider.DocumentFile;
import android.util.Log;
import android.widget.ImageView;

import com.noob.noobfilechooser.managers.NoobFileManager;
import com.noob.noobfilechooser.managers.NoobManager;

import java.io.File;

/**
 * Created by abhi on 27/09/16.
 */

public class NoobFile {
    private String mName;
    private String mMimeType;
    private String mDocId;
    private Uri mUri;
    private boolean isTreeDoc;
    private boolean isDirectory;
    private DocumentFile mDocumentFile;
    private File mFile;
    private Bitmap mThumbnail;
    private boolean mIsSelected = false;
    private boolean mIsInvalid = false;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NoobFile(Context contextParam, Cursor cursorParam, Uri treeUri, boolean isTreeDocParam) {
        isTreeDoc = isTreeDocParam;

        mName = cursorParam.getString(0);
        mMimeType = cursorParam.getString(1);
        mDocId = cursorParam.getString(2);
        isDirectory = mMimeType.equalsIgnoreCase(DocumentsContract.Document.MIME_TYPE_DIR);

        if (isTreeDoc) {
            mUri = treeUri;
            mDocumentFile = DocumentFile.fromTreeUri(contextParam, treeUri);
        } else {
            mUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, mDocId);
            if (isDirectory) {
                mDocumentFile = DocumentFile.fromTreeUri(contextParam, mUri);
            } else {
                mDocumentFile = DocumentFile.fromSingleUri(contextParam, mUri);
            }
        }

        if (mDocumentFile.exists()) {
            Log.d("DocumentENtry", "Exists true " + mName);
        } else {
            Log.d("DocumentENtry", "Exists false " + mName);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public NoobFile(DocumentFile docFileParam) {
        loadDocFile(docFileParam);
    }

    public NoobFile(File fileParam) {
        loadFile(fileParam);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void loadDocFile(DocumentFile docFileParam) {
        isTreeDoc = false;
        mDocumentFile = docFileParam;
        isDirectory = mDocumentFile.isDirectory();
        mUri = docFileParam.getUri();
        mName = docFileParam.getName();
        mMimeType = docFileParam.getType();
        mDocId = DocumentsContract.getDocumentId(mUri);
    }

    private void loadFile(File fileParam) {
        String internalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        isTreeDoc = fileParam.getAbsolutePath().equalsIgnoreCase(internalStoragePath);
        mFile = fileParam;
        isDirectory = mFile.isDirectory();
        mName = mFile.getName();
        mMimeType = NoobFileManager.getMimeType(mFile.getPath());
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private Bitmap loadThumbnailSAF(Context contextParam) {
        mThumbnail = NoobFileManager.getThumbnail(mDocumentFile, contextParam, 60, 60);
        return mThumbnail;
    }

    private Bitmap loadThumbnail() {
        mThumbnail = NoobFileManager.getThumbnail(mFile, 60, 60);
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
            if (mDocumentFile != null)
                loadThumbnailSAF(imageView.getContext());
            else
                loadThumbnail();
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
        if (getType() == null)
            return false;
        return getType().startsWith("image/");
    }

    public boolean isAudioFile() {
        if (getType() == null)
            return false;
        return getType().startsWith("audio/");
    }

    public boolean isVideoFile() {
        if (getType() == null)
            return false;
        return getType().startsWith("video/");
    }

    public DocumentFile getParentDoc() {
        if (!isTreeDoc()) {
            return getDocumentFile().getParentFile();
        }
        return null;
    }

    public File getParent() {
        if (!isTreeDoc()) {
            return getFile().getParentFile();
        }
        return null;
    }

    public NoobFile getParentNoobFile() {
        if (isTreeDoc())
            return null;
        if (getDocumentFile() != null) {
            return new NoobFile(getParentDoc());
        }
        if (getFile() != null) {
            return new NoobFile(getParent());
        }
        return null;
    }


    public boolean delete() {
        boolean success = false;
        if (getDocumentFile() != null) {
            success = getDocumentFile().delete();
        } else {
            success = getFile().delete();
        }
        if (success)
            mIsInvalid = true;
        return success;
    }

    public boolean renameTo(String fileName) {
        if (getDocumentFile() != null) {
            boolean success = getDocumentFile().renameTo(fileName);
            if (success)
                mIsInvalid = true;
            return success;
        } else if (getFile() != null) {
            File _file = new File(getFile().getParent() + "/" + fileName);
            boolean success = getFile().renameTo(_file);
            if (success && _file.exists()) {
                loadFile(_file);
            }
            return success;
        }
        return false;
    }

    //region Accessors

    public String getName() {
        return mName;
    }

    public void setName(String nameParam) {
        mName = nameParam;
    }

    public String getType() {
        return mMimeType;
    }

    public Uri getUri() {
        return mUri;
    }

    public DocumentFile getDocumentFile() {
        return mDocumentFile;
    }

    public File getFile() {
        return mFile;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public boolean isTreeDoc() {
        return isTreeDoc;
    }

    public String getDocId() {
        return mDocId;
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

    public boolean isInvalid() {
        return mIsInvalid;
    }
    //endregion
}
