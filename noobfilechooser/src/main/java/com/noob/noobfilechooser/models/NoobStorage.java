package com.noob.noobfilechooser.models;

import android.net.Uri;

/**
 * Created by abhi on 11/10/16.
 */

public class NoobStorage {
    private String mTitle;
    private String mUriString;
    private transient Uri mUri;

    public NoobStorage(Uri uriParam, String titleParam) {
        load(uriParam, titleParam);
    }

    public NoobStorage(Uri uriParam) {
        load(uriParam, null);
    }

    @Override
    public String toString() {
        return getUriString();
    }

    public void load(Uri uri, String title) {
        mTitle = title;
        mUri = uri;
    }

    public void load(NoobStorage storage) {
        mTitle = storage.getTitle();
        mUri = storage.getUri();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String titleParam) {
        mTitle = titleParam;
    }

    public String getUriString() {
        if (mUriString == null && mUri != null) {
            mUriString = mUri.toString();
        }
        return mUriString;
    }

    public void setUriString(String uriStringParam) {
        mUri = Uri.parse(mUriString);
        mUriString = uriStringParam;
    }

    public Uri getUri() {
        if (mUri == null && mUriString != null) {
            mUri = Uri.parse(mUriString);
        }
        return mUri;
    }

    public void setUri(Uri uriParam) {
        mUriString = mUri.toString();
        mUri = uriParam;
    }
}
